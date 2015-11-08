package ninja.shadowfox.shadowfox_botany

import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.lib.Constants

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.MODNAME, dependencies = Constants.DEPENDENCIES)
public class ShadowfoxBotany {

    companion object {
        var thaumcraftLoaded = false

        @field:Mod.Instance("ShadowfoxBotany") lateinit var instance: ShadowfoxBotany

        @field:SidedProxy(serverSide = "ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy",
                clientSide = "ninja.shadowfox.shadowfox_botany.client.core.proxy.ClientProxy") lateinit var proxy: CommonProxy
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {

        thaumcraftLoaded = Loader.isModLoaded("Thaumcraft")

        proxy.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        proxy.init(event)

    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        proxy.postInit(event)
    }
}
