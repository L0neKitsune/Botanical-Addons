package ninja.shadowfox.shadowfox_botany

import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.relauncher.Side
import net.minecraft.launchwrapper.Launch
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.common.network.PlayerItemMessage
import ninja.shadowfox.shadowfox_botany.common.network.PlayerItemMessageHandler
import ninja.shadowfox.shadowfox_botany.lib.Constants

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.MODNAME, dependencies = Constants.DEPENDENCIES)
public class ShadowfoxBotany {

    companion object {
        var isDevEnv = false
        var thaumcraftLoaded = false
        lateinit var network: SimpleNetworkWrapper

        @field:Mod.Instance("ShadowfoxBotany") lateinit var instance: ShadowfoxBotany

        @field:SidedProxy(serverSide = "ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy",
                clientSide = "ninja.shadowfox.shadowfox_botany.client.core.proxy.ClientProxy") lateinit var proxy: CommonProxy
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {

        instance = this
        isDevEnv = Launch.blackboard["fml.deobfuscatedEnvironment"] as Boolean
        thaumcraftLoaded = Loader.isModLoaded("Thaumcraft")
        network = SimpleNetworkWrapper(Constants.MODID)

        network.registerMessage(PlayerItemMessageHandler::class.java, PlayerItemMessage::class.java, 0, Side.SERVER)
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
