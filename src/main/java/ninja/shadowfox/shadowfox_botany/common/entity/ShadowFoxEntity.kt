package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.common.registry.EntityRegistry

public object ShadowFoxEntity {

    init {
        EntityRegistry.registerGlobalEntityID(GrieferCreeper::class.java, "botania_addon:grieferCreeper", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000)
    }
}