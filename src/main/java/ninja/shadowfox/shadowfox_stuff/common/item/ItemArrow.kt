package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_stuff.api.ISFBolt
import ninja.shadowfox.shadowfox_stuff.common.entity.EntityBolt

/**
 * 1.8 mod
 * created on 2/20/16
 */
class ItemArrow(): ItemMod("arrow"), ISFBolt {


    override fun makeBolt(world: World, player: EntityPlayer, velocity: Float): EntityBolt? {
        return EntityBolt(world, player, velocity).apply {
            damage = 6.0
            shootingEntity = player
        }
    }
}
