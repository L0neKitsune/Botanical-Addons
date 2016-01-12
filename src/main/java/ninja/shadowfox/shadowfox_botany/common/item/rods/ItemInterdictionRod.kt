package ninja.shadowfox.shadowfox_botany.common.item.rods

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
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
import vazkii.botania.common.entity.EntityDoppleganger
import java.awt.Color
import kotlin.ranges.step

public open class ItemInterdictionRod(name: String = "interdictionRod") : ItemMod(name), IManaUsingItem, IAvatarWieldable {
    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarInterdiction.png")

    init {
        setMaxStackSize(1)
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

    object PLAYER_SELECTOR : IEntitySelector {
        override fun isEntityApplicable(e: Entity): Boolean {
            return (e is EntityLivingBase && e !is EntityDoppleganger) || (e is IProjectile && e !is IManaBurst)
        }
    }

    object AVATAR_SELECTOR : IEntitySelector {
        override fun isEntityApplicable(e: Entity): Boolean {
            return e is EntityLivingBase && e !is EntityPlayer && e !is EntityDoppleganger
        }
    }

    fun particleRing(world: World, x: Int, y: Int, z: Int, range: Int, r: Float, g: Float, b: Float) {
        particleRing(world, x.toDouble(), y.toDouble(), z.toDouble(), range, r, g, b)
    }

    fun particleRing(world: World, x: Double, y: Double, z: Double, range: Int, r: Float, g: Float, b: Float) {
        val m = 0.15F
        val mv = 0.35F
        for (i in 0..359 step 8) {
            val rad = i.toDouble() * Math.PI / 180.0
            val dispx = x + 0.5 - Math.cos(rad) * range.toFloat()
            val dispy = y + 0.5
            val dispz = z + 0.5 - Math.sin(rad) * range.toFloat()

            Botania.proxy.wispFX(world, dispx, dispy, dispz, r, g, b, 0.2F, (Math.random() - 0.5).toFloat() * m, (Math.random() - 0.5).toFloat() * mv, (Math.random() - 0.5F).toFloat() * m)
        }
    }

    fun pushEntities(x: Int, y: Int, z: Int, range: Int, velocity: Double, entities: List<Any?>): Boolean {
        return pushEntities(x.toDouble(), y.toDouble(), z.toDouble(), range, velocity, entities)
    }

    fun pushEntities(x: Double, y: Double, z: Double, range: Int, velocity: Double, entities: List<Any?>): Boolean {
        var flag = false
        for (entityLiving in entities) {
            if (entityLiving is Entity) {
                var xDif = entityLiving.posX - x
                var yDif = entityLiving.posY - (y + 1)
                var zDif = entityLiving.posZ - z
                var dist = Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif)
                if (dist <= range) {
                    entityLiving.motionX = velocity * xDif
                    entityLiving.motionY = velocity * yDif
                    entityLiving.motionZ = velocity * zDif
                    entityLiving.fallDistance = 0f
                    flag = true
                }
            }
        }
        return flag
    }

    override fun onUsingTick(stack: ItemStack?, player: EntityPlayer?, count: Int) {
        if (player != null) {
            var world = player.worldObj
            var x = player.posX
            var y = player.posY
            var z = player.posZ

            var priest = (ItemPriestEmblem.getEmblem(2, player) != null)
            var prowess = IManaProficiencyArmor.Helper.hasProficiency(player)

            val cost = getCost(prowess, priest)
            val range = getRange(prowess, priest)
            val velocity = getVelocity(prowess, priest)

            if (ManaItemHandler.requestManaExactForTool(stack, player, cost, false)) {
                val color = Color(ColorOverrideHelper.getColor(player, 0x0000FF))
                val r = color.red.toFloat() / 255f
                val g = color.green.toFloat() / 255f
                val b = color.blue.toFloat() / 255f
                if (count % 5 == 0) particleRing(world, x, y, z, range, r, g, b)

                val exclude: EntityLivingBase = player
                val entities = world.getEntitiesWithinAABBExcludingEntity(exclude,
                        AxisAlignedBB.getBoundingBox(x - range, y - range, z - range,
                                x + range, y + range, z + range), PLAYER_SELECTOR)

                if (pushEntities(x, y, z, range, velocity, entities)) {
                    if (count % 3 == 0) world.playSoundAtEntity(player, "shadowfox_botany:wind", 0.4F, 1F)
                    ManaItemHandler.requestManaExactForTool(stack, player, cost, true)
                }
            }
        }
    }

    val COST = 5
    val PROWESS_COST = -1
    val PRIEST_COST = 2
    val AVATAR_COST = 4

    val VELOCITY = 0.05
    val PROWESS_VELOCTY = 0.02
    val PRIEST_VELOCITY = 0.07

    val RANGE = 5
    val PROWESS_RANGE = 0
    val PRIEST_RANGE = 2
    fun getCost(prowess: Boolean, priest: Boolean): Int {
        return COST + if (prowess) PROWESS_COST else 0 + if (priest) PRIEST_COST else 0
    }

    fun getVelocity(prowess: Boolean, priest: Boolean): Double {
        var vel = VELOCITY
        if (prowess) vel += PROWESS_VELOCTY
        if (priest) vel += PRIEST_VELOCITY
        return vel
    }

    fun getRange(prowess: Boolean, priest: Boolean): Int {
        return RANGE + if (prowess) PROWESS_RANGE else 0 + if (priest) PRIEST_RANGE else 0
    }

    override fun onAvatarUpdate(tile: IAvatarTile, stack: ItemStack) {
        val te = tile as TileEntity
        var world = te.worldObj
        var x = te.xCoord.toDouble()
        var y = te.yCoord.toDouble()
        var z = te.zCoord.toDouble()

        if (tile.currentMana >= AVATAR_COST) {
            if (tile.elapsedFunctionalTicks % 5 == 0) particleRing(world, x, y, z, RANGE, 0f, 0f, 1f)

            val entities = world.selectEntitiesWithinAABB(EntityLivingBase::class.java,
                    AxisAlignedBB.getBoundingBox(x - RANGE, y - RANGE, z - RANGE,
                            x + RANGE, y + RANGE, z + RANGE), AVATAR_SELECTOR)

            if (pushEntities(x, y, z, RANGE, VELOCITY, entities)) {
                if (tile.elapsedFunctionalTicks % 3 == 0) world.playSoundEffect(x.toDouble(), y.toDouble(), z.toDouble(), "shadowfox_botany:wind", 0.4F, 1F)
                tile.recieveMana(-AVATAR_COST)
            }
        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }
}
