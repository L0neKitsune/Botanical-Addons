package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.item.StandardItem
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.item.IManaProficiencyArmor
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.entity.EntityDoppleganger
import java.util.*

public open class LightningRod(name: String = "lightningRod") : StandardItem(name), IManaUsingItem, IAvatarWieldable {
    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarLightning.png")
    val COST: Int = 300;
    private val COST_AVATAR = 100


    init {
        setMaxStackSize(1);
    }

    override fun getItemUseAction(par1ItemStack: ItemStack?): EnumAction {
        return EnumAction.bow
    }

    override fun getMaxItemUseDuration(par1ItemStack: ItemStack?): Int {
        return 72000
    }

    override fun onPlayerStoppedUsing(stack: ItemStack?, p_77615_2_: World?, p_77615_3_: EntityPlayer?, p_77615_4_: Int) {
        super.onPlayerStoppedUsing(stack, p_77615_2_, p_77615_3_, p_77615_4_)
        ItemNBTHelper.setInt(stack, "target", -1)
    }

    override fun onUsingTick(stack: ItemStack?, player: EntityPlayer?, count: Int) {
        if (count != this.getMaxItemUseDuration(stack)  && !player!!.worldObj.isRemote && ManaItemHandler.requestManaExactForTool(stack, player, COST, false)) {

            var target = getTarget(player!!.worldObj, player, ItemNBTHelper.getInt(stack, "target", -1))
            if (target != null) {
                ItemNBTHelper.setInt(stack, "target", target.entityId)
                val thor: Boolean = (vazkii.botania.common.item.relic.ItemThorRing.getThorRing(player) != null)


                val var5 = Vector3.fromEntityCenter(target)
                val var7 = var5.copy().add(0.0, 1.0, 0.0)
                Botania.proxy.lightningFX(player.worldObj, var5, var7, 2.0f, 96708, 11198463)

                if (count % (if (IManaProficiencyArmor.Helper.hasProficiency(player)) 80 else 60) == 0) {
                    if (ConfigHandler.realLightning && thor) {
                        if (spawnLightning(player.worldObj, target.posX.toDouble(), target.posY.toDouble(), target.posZ.toDouble())) {
                            ManaItemHandler.requestManaExactForTool(stack, player, COST, true)
                            target.attackEntityFrom(DamageSource.causePlayerDamage(player), if(thor) 10f else 8f)
                        }
                    } else {
                        target.attackEntityFrom(DamageSource.causePlayerDamage(player), if(thor) 10f else 8f)
                        ManaItemHandler.requestManaExactForTool(stack, player, COST, true)
                        Botania.proxy.lightningFX(player.worldObj, Vector3.fromEntityCenter(player), Vector3.fromEntityCenter(target), 1.0f, 96708, 11198463)
                        player.worldObj.playSoundEffect(target.posX.toDouble(), target.posY.toDouble(), target.posZ.toDouble(), "ambient.weather.thunder", 100.0f, 0.8f + player.worldObj.rand.nextFloat() * 0.2f)
                    }
                    chainLightning(stack!!, target, player, thor)
                }

            }
        }
    }

    override fun onItemRightClick(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
        par3EntityPlayer!!.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack))
        return par1ItemStack
    }

    fun getTarget(world: World, player: EntityPlayer, trial_target: Int, range: Float = 12.0f): EntityCreature? {

        var potential = world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range))

        if (trial_target >= 0) {
            var target = world.getEntityByID(trial_target)

            if (target != null && target is EntityCreature)
                if (target.health > 0.0f && !target.isDead && potential.contains(target)) {
                    return target
                }
        }

        if (potential.size > 0)
            while (potential.size > 0) {
                var i = world.rand.nextInt(potential.size)

                if (potential[i] is EntityLivingBase && if(true) true else potential[i] is IMob && potential[i] !is EntityPlayer) {
                    var entity: EntityCreature = potential[i] as EntityCreature
                    if (entity is EntityCreature && !entity.isDead && (entity.entityId != player.entityId)) {
                        return potential[i] as EntityCreature
                    }
                }

                potential.remove(i)
            }

        return null
    }

    fun chainLightning(stack: ItemStack, entity: EntityLivingBase?, attacker: EntityLivingBase?, supercharge: Boolean): Boolean {
        if (entity !is EntityPlayer && entity != null) {
            val range = if (!supercharge) 8.0 else 10.0
            val alreadyTargetedEntities = ArrayList<Entity>()
            var dmg = if (!supercharge) 6 else 8
            val lightningSeed = ItemNBTHelper.getLong(stack, "lightningSeed", 0L)
            val selector = object : IEntitySelector {
                override fun isEntityApplicable(e: Entity): Boolean {
                    return e is EntityLivingBase && (if(true) e is EntityLivingBase else e is IMob) && e !is EntityPlayer && !alreadyTargetedEntities.contains(e)
                }
            }
            val rand = Random(lightningSeed)
            var lightningSource: EntityLivingBase = entity

            for (i in 0..if (!supercharge) 4 else 6) {
                val entities = entity.worldObj.getEntitiesWithinAABBExcludingEntity(lightningSource, AxisAlignedBB.getBoundingBox(lightningSource.posX - range, lightningSource.posY - range, lightningSource.posZ - range, lightningSource.posX + range, lightningSource.posY + range, lightningSource.posZ + range), selector)
                if (entities.isEmpty()) {
                    break
                }

                val target = entities[rand.nextInt(entities.size)] as EntityLivingBase
                if (attacker != null && attacker is EntityPlayer) {
                    target.attackEntityFrom(DamageSource.causePlayerDamage(attacker as EntityPlayer?), dmg.toFloat())
                } else {
                    target.attackEntityFrom(DamageSource.causeMobDamage(attacker), dmg.toFloat())
                }

                Botania.proxy.lightningFX(entity.worldObj, Vector3.fromEntityCenter(lightningSource), Vector3.fromEntityCenter(target), 1.0f, 96708, 11198463)
                alreadyTargetedEntities.add(target)
                lightningSource = target
                --dmg
            }

            if (!entity.worldObj.isRemote) {
                ItemNBTHelper.setLong(stack, "lightningSeed", entity.worldObj.rand.nextLong())
            }
        }

        return super.hitEntity(stack, entity, attacker)
    }


    fun spawnLightning(world: World, posX: Double, posY: Double, posZ: Double): Boolean {
        return world.addWeatherEffect(EntityLightningBolt(world, posX, posY, posZ))
    }

    override fun isFull3D(): Boolean {
        return true;
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true;
    }

    override fun onAvatarUpdate(tile: IAvatarTile, stack: ItemStack) {
        val te = tile as TileEntity
        val world = te.worldObj
        val range = 18

        if (tile.getCurrentMana() >= COST_AVATAR && tile.isEnabled() && tile.getElapsedFunctionalTicks() % 10 == 0) {
            val selector = object : IEntitySelector {
                override fun isEntityApplicable(e: Entity): Boolean {
                    return (if(true) e is EntityLivingBase else e is IMob) && e !is EntityPlayer && e !is EntityDoppleganger
                }
            }

            val entities = world.selectEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB.getBoundingBox((te.xCoord - range).toDouble(), (te.yCoord - range).toDouble(),
                    (te.zCoord - range).toDouble(), (te.xCoord + range).toDouble(), (te.yCoord + range).toDouble(), (te.zCoord + range).toDouble()), selector)

            if (entities.size == 0) return

            val trial_target = ItemNBTHelper.getInt(stack, "target", -1)
            var target: EntityLivingBase? = null

            if (trial_target >= 0) {
                var ttarget = world.getEntityByID(trial_target)

                if (ttarget != null && ttarget is EntityCreature)
                    if (ttarget.health > 0.0f && !ttarget.isDead && entities.contains(ttarget)) {
                        target = ttarget
                    }
            }

            if (target == null) {
                if (entities.size > 0)
                    while (entities.size > 0) {
                        var i = world.rand.nextInt(entities.size)

                        if (entities[i] is EntityLivingBase && entities[i] is IMob && entities[i] !is EntityPlayer) {
                            var entity: EntityLivingBase = entities[i] as EntityLivingBase
                            if (entity is EntityLivingBase && !entity.isDead) {
                                target = entity
                                break
                            }
                        }

                        entities.remove(i)
                    }
            }

            if (target != null) {
                ItemNBTHelper.setInt(stack, "target", target.entityId)

                val var5 = Vector3.fromEntityCenter(target)
                val var7 = var5.copy().add(0.0, 1.0, 0.0)

                Botania.proxy.lightningFX(world, var5, var7, 2.0f, 96708, 11198463)
                Botania.proxy.sparkleFX(world, te.xCoord.toDouble() + 0.5, te.yCoord.toDouble() + 2.5, te.zCoord.toDouble() + 0.5, 0.667f, 0.875f, 1f, 6.0f, 6)

                if (tile.getElapsedFunctionalTicks() % 100 == 0) {

                    target.attackEntityFrom(DamageSource.causeMobDamage(null), 10.toFloat())

                    if(!world.isRemote) tile.recieveMana(-COST_AVATAR)

                    var vect =  Vector3()
                    vect.set(te.xCoord.toDouble() + 0.5, te.yCoord.toDouble() + 2.5, te.zCoord.toDouble() + 0.5)

                    Botania.proxy.lightningFX(world, vect, Vector3.fromEntityCenter(target), 1.0f, 96708, 11198463)

                    world.playSoundEffect(target.posX.toDouble(), target.posY.toDouble(), target.posZ.toDouble(), "ambient.weather.thunder", 100.0f, 0.8f + world.rand.nextFloat() * 0.2f)

                    chainLightning(stack, target, null, false)
                }
            }

        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }
}