package ninja.shadowfox.shadowfox_botany.common.item.creator

import cpw.mods.fml.common.FMLLog
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.*
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.IIcon
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.api.item.ColorOverrideHelper
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.entity.EntityKitsunebi
import org.apache.logging.log4j.Level
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.client.core.helper.IconHelper
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.item.ItemMod
import java.awt.Color


open class ItemFoxFan(name: String = "kitsuneFan") : ItemMod(), IManaUsingItem {
    val COST = 80
    val PRIEST_COST = 10
    val PROWESS_COST = 10

    val SPEED = 30
    val PRIEST_SPEEDUP = 10
    val PROWESS_SPEEDUP = 10

    val DAMAGE = 8f
    val PRIEST_POWERUP = 3f
    val PROWESS_POWERUP = 2f

    init {
        this.creativeTab = ShadowFoxCreativeTab
        this.unlocalizedName = name
        this.maxDamage = 1561
        this.setFull3D()
    }

    override fun getItemEnchantability(): Int = 50
    override fun getRarity(stack: ItemStack): EnumRarity = BotaniaAPI.rarityRelic

    override fun onEaten(p_77654_1_: ItemStack, p_77654_2_: World?, p_77654_3_: EntityPlayer?): ItemStack = p_77654_1_
    override fun getMaxItemUseDuration(p_77626_1_: ItemStack?): Int = 72000
    override fun getItemUseAction(p_77661_1_: ItemStack?): EnumAction = EnumAction.bow


    override fun setUnlocalizedName(par1Str: String): Item {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item.".toRegex(), "item.botania:")
    }

    override fun onItemRightClick(p_77659_1_: ItemStack, world: World?, player: EntityPlayer?): ItemStack {
        if (world != null && player != null) {
            if (player.isSneaking)
                player.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_))
            else {
                val active = getActiveKitsunebi(world, player)
                if (active.size > 0) {
                    val t = active.minBy { (it as EntityKitsunebi).ticksExisted }
                    (t as EntityKitsunebi).setFlying(player)
                }
            }

        }
        return p_77659_1_
    }

    override fun onUsingTick(stack: ItemStack?, player: EntityPlayer?, count: Int) {
        if (count != this.getMaxItemUseDuration(stack) && !player!!.worldObj.isRemote) {

            var priest = false //(ItemPriestEmblem.getEmblem(0, player) != null)
            var prowess = vazkii.botania.api.item.IManaProficiencyArmor.Helper.hasProficiency(player)

            val color = ColorOverrideHelper.getColor(player, 0x0079C4)
            val innerColor = Color(color).brighter().brighter().rgb

            val speed = getSpeed(prowess, priest)
            if (count % speed == 0) {
                val active = getActiveKitsunebi(player.worldObj, player)

                if (active.size < 9) {
                    if (ManaItemHandler.requestManaExactForTool(stack, player, getCost(prowess, priest), false)) {

                        val damage = getDamage(prowess, priest)

                        if (ManaItemHandler.requestManaExactForTool(stack, player, getCost(prowess, priest), true)) {

                            index@for (i in 0..8) {
                                for (a in active) {
                                    if ((a as EntityKitsunebi).offsetIndex == i) continue@index
                                }
                                var kitsunebi = makeKistsunebi(player.worldObj, player, damage, i)
                                player.worldObj.spawnEntityInWorld(kitsunebi)
                                break@index
                            }
                        }
                    }
                }
            }
        }
    }

    fun getActiveKitsunebi(world: World, player: EntityPlayer): MutableList<Any?> {
        val r = 3
        val selector = IEntitySelector { e -> e is EntityKitsunebi && e.following_shooter && e.shootingEntity != null }

        return world.selectEntitiesWithinAABB(EntityKitsunebi::class.java,
                AxisAlignedBB.getBoundingBox(
                        player.posX - r, player.posY - r, player.posZ - r,
                        player.posX + r, player.posY + r, player.posZ + r
                ), selector)
    }

    fun getCost(prowess: Boolean, priest: Boolean): Int {
        return COST + (if (prowess) PROWESS_COST else 0) + (if (priest) PRIEST_COST else 0)
    }

    fun getSpeed(prowess: Boolean, priest: Boolean): Int {
        return SPEED - (if (prowess) PROWESS_SPEEDUP else 0) - (if (priest) PRIEST_SPEEDUP else 0)
    }

    fun getDamage(prowess: Boolean, priest: Boolean): Float {
        return DAMAGE + (if (prowess) PROWESS_POWERUP else 0f) + (if (priest) PRIEST_POWERUP else 0f)
    }

    internal fun makeKistsunebi(world: World, player: EntityPlayer, dmg: Float, index: Int): EntityKitsunebi {
        val r = 2

        ((player.rotationYaw + EntityKitsunebi.KITSUNEBI_OFFSETS[index].toDouble()) * 3.141592653589793 / 180.0).toDouble().let {
            val posX = player.posX.toDouble() + Math.cos(it) * r
            val posZ = player.posZ.toDouble() + Math.sin(it) * r
            val posY = player.posY + .5

            return EntityKitsunebi(world, posX, posY, posZ).apply {
                shootingEntity = player
                following_shooter = true
                offsetIndex = index
                damage = dmg.toDouble()
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = IconHelper.forItem(par1IconRegister, this)
    }

    override fun onUpdate(stack: ItemStack?, world: World?, player: Entity?, par4: Int, par5: Boolean) {

    }

    override fun usesMana(stack: ItemStack): Boolean = true

    override fun getIcon(stack: ItemStack, renderPass: Int, player: EntityPlayer?, usingItem: ItemStack?, useRemaining: Int): IIcon {
        return this.itemIcon
    }

}
