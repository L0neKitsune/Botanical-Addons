package ninja.shadowfox.shadowfox_stuff.common.entity

import net.minecraftforge.fml.common.registry.EntityRegistry
import ninja.shadowfox.shadowfox_stuff.ShadowFox

/**
 * 1.8 mod
 * created on 2/20/16
 */
object ModEntities {
    init {

        var id = 0
        EntityRegistry.registerModEntity(EntityBolt::class.java, "sf:bolt", ++id, ShadowFox.instance, 256, 10, true)
    }
}
