package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.common.registry.EntityRegistry
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler

public object ShadowFoxEntity {

    init {

        if (ConfigHandler.uberCreepers) {
            EntityRegistry.registerGlobalEntityID(EntityGrieferCreeper::class.java, "botania_addon:grieferCreeper", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000)
        }
    }
}