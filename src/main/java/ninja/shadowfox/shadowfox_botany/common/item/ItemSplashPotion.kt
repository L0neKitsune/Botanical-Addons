package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IIcon
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.entity.EntityThrownPotion
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.brew.Brew
import vazkii.botania.api.brew.IBrewContainer
import vazkii.botania.api.brew.IBrewItem
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.client.core.helper.IconHelper
import vazkii.botania.common.core.helper.ItemNBTHelper
import java.awt.Color


public class ItemSplashPotion() : ItemMod("splashPotion"), IBrewItem, IBrewContainer {

    lateinit var itemIconFluid: IIcon

    init {

    }

    override fun getSubItems(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        super.getSubItems(item, tab, list)

        if (item != null && list != null) {
            for (brew in BotaniaAPI.brewMap.keys) {
                val brewStack = this.getItemForBrew(BotaniaAPI.brewMap[brew] as Brew, ItemStack(this))
                if (brewStack != null) {
                    list.add(brewStack)
                }
            }
        }
    }

    override fun onItemRightClick(stack: ItemStack?, world: World?, player: EntityPlayer?): ItemStack? {
        if (stack != null && world != null && player != null) {
            if (!world.isRemote) {
                val potion = EntityThrownPotion(player, getBrew(stack).getPotionEffects(stack))
                potion.motionX *= 1.6
                potion.motionY *= 1.6
                potion.motionZ *= 1.6
                world.spawnEntityInWorld(potion)

                stack.stackSize--
            }
        }

        return stack
    }

    override fun getColorFromItemStack(stack: ItemStack?, pass: Int): Int {
        if (stack != null) {
            if (pass == 0) {
                return 16777215
            } else {
                val color = Color(this.getBrew(stack).getColor(stack))
                val add = (Math.sin(ClientTickHandler.ticksInGame.toDouble() * 0.1) * 16.0).toInt()
                val r = Math.max(0, Math.min(255, color.red + add))
                val g = Math.max(0, Math.min(255, color.green + add))
                val b = Math.max(0, Math.min(255, color.blue + add))
                return r shl 16 or g shl 8 or b
            }
        }

        return 16777215
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        itemIcon = IconHelper.forName(par1IconRegister, "vial" + "0")
        itemIconFluid = IconHelper.forName(par1IconRegister, "vial" + "1_0")
    }

    override fun addInformation(stack: ItemStack?, player: EntityPlayer?, list: MutableList<Any?>?, adv: Boolean) {
        if (stack != null && list != null) {
            val brew = this.getBrew(stack)
            addStringToTooltip("${EnumChatFormatting.DARK_PURPLE}${StatCollector.translateToLocal("botaniamisc.brewOf")}${StatCollector.translateToLocal(brew.getUnlocalizedName(stack))}", list)

            for (effect in brew.getPotionEffects(stack)) {
                val potion = Potion.potionTypes[effect.potionID]
                val format = if (potion.isBadEffect) EnumChatFormatting.RED else EnumChatFormatting.GRAY
                addStringToTooltip("" + format + StatCollector.translateToLocal(effect.effectName) + (if (effect.amplifier == 0) "" else " " + StatCollector.translateToLocal("botania.roman" + (effect.amplifier + 1))) + EnumChatFormatting.GRAY + (if (potion.isInstant) "" else " (" + Potion.getDurationString(effect) + ")"), list)
            }

        }
    }

    override fun getItemForBrew(brew: Brew, stack: ItemStack): ItemStack? {
            val brewStack = ItemStack(this)
            setBrew(brewStack, brew)
            return brewStack

    }

    internal fun addStringToTooltip(s: String, tooltip: MutableList<Any?>?) {
        tooltip?.add(s.replace("&".toRegex(), "§"))
    }

    override fun requiresMultipleRenderPasses(): Boolean {
        return true
    }

    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return if (pass == 0) itemIcon else itemIconFluid
    }

    override fun getBrew(stack: ItemStack): Brew {
        val key = ItemNBTHelper.getString(stack, "brewKey", "")
        return BotaniaAPI.getBrewFromKey(key)
    }

    override fun getManaCost(p0: Brew?, p1: ItemStack?): Int {
        if (p0 != null)
            return p0.manaCost.times(1.5).toInt()

        return 400
    }

    fun setBrew(stack: ItemStack, brew: Brew?) {
        setBrew(stack, (brew ?: BotaniaAPI.fallbackBrew).key)
    }

    fun setBrew(stack: ItemStack, brew: String) {
        ItemNBTHelper.setString(stack, "brewKey", brew)
    }
}
