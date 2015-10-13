package ninja.shadowfox.botania_addon

import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import ninja.shadowfox.botania_addon.common.blocks.ModBlocks
import ninja.shadowfox.botania_addon.lib.Constants

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.MODNAME, dependencies = Constants.DEPENDENCIES)
class BotaniaAddon {
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ModBlocks
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {

    }
}