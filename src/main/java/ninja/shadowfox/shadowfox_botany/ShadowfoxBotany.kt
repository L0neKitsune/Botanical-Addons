package ninja.shadowfox.shadowfox_botany

import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.crafting.ModRecipes
import ninja.shadowfox.shadowfox_botany.lib.Constants

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.MODNAME, dependencies = Constants.DEPENDENCIES)
class ShadowfoxBotany {
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ShadowFoxBlocks
        ModRecipes
        LexiconRegistry
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {

    }
}
