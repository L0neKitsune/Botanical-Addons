package ninja.shadowfox.shadowfox_stuff

import net.minecraft.init.Blocks
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import ninja.shadowfox.shadowfox_stuff.common.core.proxy.CommonProxy
import vazkii.botania.common.lib.LibMisc


@Mod(modid = ShadowFox.MODID, version = ShadowFox.VERSION, dependencies = ShadowFox.DEPENDENCIES)
class ShadowFox {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
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

    companion object {
        const val MODID = "shadowfox_stuff"
        const val VERSION = "1.0"
        const val DEPENDENCIES = "required-after:Botania;after:Thaumcraft"

        var isDevEnv = false
        var thaumcraftLoaded = false
        lateinit var network: SimpleNetworkWrapper

        @field:Mod.Instance(MODID)
        lateinit var instance: ShadowFox

        @field:SidedProxy(serverSide = "ninja.shadowfox.shadowfox_stuff.common.core.proxy.CommonProxy",
                clientSide = "ninja.shadowfox.shadowfox_stuff.client.core.proxy.ClientProxy") lateinit var proxy: CommonProxy

    }
}
