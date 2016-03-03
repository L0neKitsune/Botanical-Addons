package ninja.shadowfox.shadowfox_stuff.common.core.proxy

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ninja.shadowfox.shadowfox_stuff.common.block.ModBlocks
import ninja.shadowfox.shadowfox_stuff.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_stuff.common.crafting.ModCrafting
import ninja.shadowfox.shadowfox_stuff.common.entity.ModEntities
import ninja.shadowfox.shadowfox_stuff.common.item.ModItems


open class CommonProxy {
    open fun preInit(event: FMLPreInitializationEvent) {
        ConfigHandler.loadConfig(event.suggestedConfigurationFile)

        ModItems
        ModBlocks
        ModEntities
        ModCrafting
    }

    open fun init(event: FMLInitializationEvent) {
    }

    open fun postInit(event: FMLPostInitializationEvent) {
        ConfigHandler.loadPostInit()
    }
}
