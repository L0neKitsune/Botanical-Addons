package ninja.shadowfox.shadowfox_botany.common.entity

import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.entity.EnumCreatureType
import net.minecraft.world.biome.BiomeGenBase
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler

public object ShadowFoxEntity {

    init {
        if (ConfigHandler.uberCreepers) {
            EntityRegistry.registerGlobalEntityID(EntityGrieferCreeper::class.java, "shadowfox_botany:grieferCreeper", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000)
        }

        EntityRegistry.registerGlobalEntityID(EntityVoidCreeper::class.java, "shadowfox_botany:voidCreeper", EntityRegistry.findGlobalUniqueEntityId(), 0xcc11d3, 0xfb9bff)

        for (i in BiomeGenBase.getBiomeGenArray()) {
            if (i != null && i != BiomeGenBase.hell && i != BiomeGenBase.sky && i != BiomeGenBase.mushroomIsland && i != BiomeGenBase.mushroomIslandShore)
                EntityRegistry.addSpawn(EntityVoidCreeper::class.java, 10, 1, 3, EnumCreatureType.monster, i)
        }

    }
}
