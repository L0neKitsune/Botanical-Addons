package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.monster.EntitySlime
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.MathHelper
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.item.IPriestColorOverride
import ninja.shadowfox.shadowfox_botany.common.item.StandardItem
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.item.IManaProficiencyArmor
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.entity.EntityDoppleganger
import java.awt.Color
import java.util.*

public open class InterdictionRod(name: String = "interdictionRod") : StandardItem(name), IManaUsingItem, IAvatarWieldable {
    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarInterdiction.png")

    init {
        setMaxStackSize(1)
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

    val selector = object : IEntitySelector {
        override fun isEntityApplicable(e: Entity): Boolean {
            return e is EntityLivingBase
        }
    }

    val avatar_selector = object : IEntitySelector {
        override fun isEntityApplicable(e: Entity): Boolean {
            return e is EntityLivingBase && !(e is EntityPlayer)
        }
    }

    override fun onUsingTick(stack: ItemStack?, player: EntityPlayer?, count: Int) {
        if (!player!!.worldObj.isRemote) {
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
                val exclude: EntityLivingBase = player
                val entities = world.getEntitiesWithinAABBExcludingEntity(exclude, 
                    AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, 
                        x + range, y + range, z + range), selector)
                var flag = false

                for (entityLiving in entities) {
                    if (entityLiving is EntityLivingBase) {
                        var xDif = entityLiving.posX - x
                        var yDif = entityLiving.posY - (y + 1)
                        var zDif = entityLiving.posZ - z
                        entityLiving.motionX = velocity * xDif
                        entityLiving.motionY = velocity * yDif
                        entityLiving.motionZ = velocity * zDif
                        entityLiving.fallDistance = 0f
                        flag = true
                    }
                }
                if (flag) {
                    world.playSoundAtEntity(player, "shadowfox_botany:wind", 0.4F, 1F)
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
        return VELOCITY + if (prowess) PROWESS_VELOCTY else 0.0 + if (priest) PRIEST_VELOCITY else 0.0
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

        val cost = AVATAR_COST
        val range = RANGE
        val velocity = VELOCITY

        if (tile.currentMana >= cost) {
            val entities = world.selectEntitiesWithinAABB(EntityLivingBase::class.java, 
                AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, 
                    x + range, y + range, z + range), avatar_selector)
            var flag = false

            for (entityLiving in entities) {
                if (entityLiving is EntityLivingBase) {
                    var xDif = entityLiving.posX - x
                    var yDif = entityLiving.posY - (y + 1)
                    var zDif = entityLiving.posZ - z
                    entityLiving.motionX = velocity * xDif
                    entityLiving.motionY = velocity * yDif
                    entityLiving.motionZ = velocity * zDif
                    entityLiving.fallDistance = 0f
                    flag = true
                }
            }
            if (flag) {
                world.playSoundEffect(x.toDouble(), y.toDouble(), z.toDouble(), "shadowfox_botany:wind", 0.4F, 1F)
                tile.recieveMana(-cost)
            }
        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }
}
