package ninja.shadowfox.shadowfox_stuff.client.core.proxy

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ninja.shadowfox.shadowfox_stuff.common.core.proxy.CommonProxy


class ClientProxy : CommonProxy() {
    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)
    }

    override fun init(event: FMLInitializationEvent) {
        super.init(event)

        this.initRenderers()
    }

    override fun postInit(event: FMLPostInitializationEvent) {
        super.postInit(event)
    }

    private fun initRenderers() {

    }
}
