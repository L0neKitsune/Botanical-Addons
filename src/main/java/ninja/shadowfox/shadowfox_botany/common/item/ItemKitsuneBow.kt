package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.ArrowLooseEvent
import net.minecraftforge.event.entity.player.ArrowNockEvent
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.entity.EntityKitsuneArrow
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.client.core.helper.IconHelper
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.item.equipment.tool.ToolCommons


open class ItemKitsuneBow(name: String = "kitsuneBow") : ItemBow(), IManaUsingItem {
    internal var pullIcons: Array<IIcon?>

    val manaPerDamage = 40

    init {
        this.pullIcons = arrayOfNulls<IIcon>(3)
        this.creativeTab = ShadowFoxCreativeTab
        this.unlocalizedName = name
        this.maxDamage = 1561
        this.setFull3D()
    }

    override fun getItemEnchantability(): Int  = 50
    override fun getRarity(stack: ItemStack): EnumRarity = BotaniaAPI.rarityRelic

    fun ItemStack.damageStack(damage: Int, entity: EntityLivingBase?) {
        ToolCommons.damageItem(this, damage, entity, manaPerDamage)
    }

    fun chargeVelocityMultiplier(): Float {
        return 3.0f
    }

    override fun setUnlocalizedName(par1Str: String): Item {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item.".toRegex(), "item.botania:")
    }

    override fun onItemRightClick(p_77659_1_: ItemStack, world: World?, player: EntityPlayer?): ItemStack {
        if (world != null && player != null) {
            val event = ArrowNockEvent(player, p_77659_1_)
            MinecraftForge.EVENT_BUS.post(event)
            if (event.isCanceled) {
                return event.result
            } else {
                if (this.canFire(p_77659_1_, world, player, 0)) {
                    player.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_))
                }
            }
        }
        return p_77659_1_
    }

    override fun onPlayerStoppedUsing(itemStack: ItemStack?, world: World?, player: EntityPlayer?, p_77615_4_: Int) {
        if (itemStack != null && world != null && player != null) {
            var j = ((this.getMaxItemUseDuration(itemStack) - p_77615_4_).toFloat() * this.chargeVelocityMultiplier()).toInt()
            val event = ArrowLooseEvent(player, itemStack, j)
            MinecraftForge.EVENT_BUS.post(event)
            if (!event.isCanceled) {
                j = event.charge
                val flag = this.canFire(itemStack, world, player, p_77615_4_)
                val infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0
                if (flag) {
                    var f = j.toFloat() / 20.0f
                    f = (f * f + f * 2.0f) / 3.0f

                    if (f.toDouble() < 0.1) return
                    if (f > 1.0f) f = 1.0f

                    val entityarrow = this.makeArrow(itemStack, world, player, p_77615_4_, f)
                    if (f == 1.0f) entityarrow.isCritical = true


                    val k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack)
                    if (k > 0) entityarrow.damage = entityarrow.damage + k.toDouble() * 0.5 + 0.5


                    val l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack)
                    if (l > 0) entityarrow.knockbackStrength = l

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                        entityarrow.setFire(120)
                    } else {
                        entityarrow.setFire(60)
                    }

                    ToolCommons.damageItem(itemStack, 1, player, 40)
                    world.playSoundAtEntity(player, "random.bow", 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + f * 0.5f)
                    this.onFire(itemStack, world, player, p_77615_4_, infinity, entityarrow)
                    if (!world.isRemote) world.spawnEntityInWorld(entityarrow)
                }
            }
        }
    }

    fun canFire(p_77615_1_: ItemStack?, p_77615_2_: World?, p_77615_3_: EntityPlayer, p_77615_4_: Int): Boolean {
        val infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_)
        return ManaItemHandler.requestManaExactForTool(p_77615_1_, p_77615_3_, 200 / (infinity + 1), false)
    }

    fun onFire(stack: ItemStack?, world: World?, player: EntityPlayer, p_77615_4_: Int, infinity: Boolean, arrow: EntityKitsuneArrow) {
        arrow.canBePickedUp = 2

        for(effect in player.activePotionEffects){
//            effect
        }
        ManaItemHandler.requestManaExactForTool(stack, player, 200 / if (infinity) 2 else 1, false)
    }

    internal fun makeArrow(p_77615_1_: ItemStack, p_77615_2_: World, p_77615_3_: EntityPlayer, p_77615_4_: Int, f: Float): EntityKitsuneArrow {
        return EntityKitsuneArrow(p_77615_2_, p_77615_3_, f * 2.0f)
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this, 0)

        for (i in 0..2) {
            this.pullIcons[i] = IconHelper.forItem(par1IconRegister, this, i + 1)
        }

    }

    override fun onUpdate(stack: ItemStack?, world: World?, player: Entity?, par4: Int, par5: Boolean) {
        if (!world!!.isRemote && player is EntityPlayer && stack!!.itemDamage > 0 && ManaItemHandler.requestManaExactForTool(stack, player as EntityPlayer?, 80, true)) {
            stack.itemDamage = stack.itemDamage - 1
        }

    }

    override fun getIsRepairable(par1ItemStack: ItemStack?, par2ItemStack: ItemStack?): Boolean {
        return if (par2ItemStack!!.item === ModItems.manaResource && par2ItemStack!!.itemDamage == 3) true else super.getIsRepairable(par1ItemStack, par2ItemStack)
    }

    override fun usesMana(stack: ItemStack): Boolean = true

    override fun getIcon(stack: ItemStack, renderPass: Int, player: EntityPlayer?, usingItem: ItemStack?, useRemaining: Int): IIcon {
        if (stack != usingItem) {
            return this.itemIcon
        } else {
            val j = ((this.getMaxItemUseDuration(stack) - useRemaining).toFloat() * this.chargeVelocityMultiplier()).toInt()
            return if (j >= 18) this.pullIcons[2]!! else if (j > 13) this.pullIcons[1]!! else if (j > 0) this.pullIcons[0]!! else this.itemIcon
        }
    }

}
