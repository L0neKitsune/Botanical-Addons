package ninja.shadowfox.shadowfox_botany.common.item.rods

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.api.item.ColorOverrideHelper
import ninja.shadowfox.shadowfox_botany.common.item.ItemMod
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import vazkii.botania.api.internal.IManaBurst
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.item.IManaProficiencyArmor
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.entity.EntityDoppleganger
import java.awt.Color

/**
 * @author WireSegal
 * Created at 9:32 PM on 1/27/16.
 */
public open class ItemFlameRod(name: String = "flameRod") : ItemMod(name), IManaUsingItem, IAvatarWieldable {
    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarFlame.png")

    init {
        setMaxStackSize(1)
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 1) {
            this.itemIcon = InterpolatedIconHelper.forItem(event.map, this)
        }
    }

    override fun getItemUseAction(par1ItemStack: ItemStack?): EnumAction {
        return EnumAction.bow
    }

    override fun getMaxItemUseDuration(par1ItemStack: ItemStack?): Int {
        return 72000
    }

    override fun isFull3D(): Boolean {
        return true
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true
    }

    override fun onItemRightClick(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
        par3EntityPlayer!!.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack))
        return par1ItemStack
    }

    override fun onUsingTick(stack: ItemStack?, player: EntityPlayer?, count: Int) {
        if (player != null) {
            val world = player.worldObj

            val lookVec = Vector3(player.lookVec)

            val dx = player.posX + lookVec.x * 2
            val dy = player.posY + lookVec.y * 2
            val dz = player.posZ + lookVec.z * 2

            var priest = (ItemPriestEmblem.getEmblem(3, player) != null)
            var prowess = IManaProficiencyArmor.Helper.hasProficiency(player)

            val color = Color(ColorOverrideHelper.getColor(player, 0xF94407))
            val r = color.red/255f
            val g = color.green/255f
            val b = color.blue/255f

            Botania.proxy.sparkleFX(world, dx, dy, dz, r, g, b, 1f, 5)

            if (count % 20 == 0) {
                val entities = world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB.getBoundingBox(dx - 1, dy - 1, dz - 1, dx + 1, dy + 1, dz + 1))
                for (entity in entities) {
                    if (entity is EntityLivingBase && entity != player && entity.health > 0) {
                        if (ManaItemHandler.requestManaExactForTool(stack, player, getCost(prowess, priest), true)) {
                            val damage = getDamage(prowess, priest)
                            entity.setFire(damage * 20)
                            entity.attackEntityFrom(EntityDamageSource("onFire", player), damage.toFloat())
                        }
                    }
                }
            }
        }
    }

    val COST = 10
    val PROWESS_COST = -2
    val PRIEST_COST = 3

    val AVATAR_COST = 4

    val DAMAGE = 2
    val PROWESS_DAMAGE = 1
    val PRIEST_DAMAGE = 7

    fun getCost(prowess: Boolean, priest: Boolean): Int {
        var d = COST
        if (prowess) d += PROWESS_COST
        if (priest) d += PRIEST_COST
        return d
    }

    fun getDamage(prowess: Boolean, priest: Boolean): Int {
        var d = DAMAGE
        if (prowess) d += PROWESS_DAMAGE
        if (priest) d += PRIEST_DAMAGE
        return d
    }

    override fun onAvatarUpdate(tile: IAvatarTile, stack: ItemStack) {
//        val te = tile as TileEntity
//        var world = te.worldObj
//        var x = te.xCoord.toDouble()
//        var y = te.yCoord.toDouble()
//        var z = te.zCoord.toDouble()
//
//        if (tile.currentMana >= AVATAR_COST) {
//            if (tile.elapsedFunctionalTicks % 5 == 0) particleRing(world, x, y, z, RANGE, 0f, 0f, 1f)
//
//            val entities = world.selectEntitiesWithinAABB(EntityLivingBase::class.java,
//                    AxisAlignedBB.getBoundingBox(x - RANGE, y - RANGE, z - RANGE,
//                            x + RANGE, y + RANGE, z + RANGE), AVATAR_SELECTOR)
//
//            if (pushEntities(x, y, z, RANGE, VELOCITY, entities)) {
//                if (tile.elapsedFunctionalTicks % 3 == 0) world.playSoundEffect(x.toDouble(), y.toDouble(), z.toDouble(), "shadowfox_botany:wind", 0.4F, 1F)
//                tile.recieveMana(-AVATAR_COST)
//            }
//        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }
}
