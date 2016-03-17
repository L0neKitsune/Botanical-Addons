package ninja.shadowfox.shadowfox_botany


import net.minecraft.launchwrapper.Launch
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.common.network.PlayerItemMessage
import ninja.shadowfox.shadowfox_botany.common.network.PlayerItemMessageHandler
import ninja.shadowfox.shadowfox_botany.lib.Constants

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.MODNAME, dependencies = Constants.DEPENDENCIES) class ShadowfoxBotany {

    companion object {
        var isDevEnv = false
        var thaumcraftLoaded = false
        lateinit var network: SimpleNetworkWrapper

        @Mod.Instance(Constants.MODID)
        lateinit var instance: ShadowfoxBotany

        @field:SidedProxy(serverSide = "ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy",
                clientSide = "ninja.shadowfox.shadowfox_botany.client.core.proxy.ClientProxy") lateinit var proxy: CommonProxy
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
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
