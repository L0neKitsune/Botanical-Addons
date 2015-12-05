package ninja.shadowfox.shadowfox_botany.client.core.proxy

import cpw.mods.fml.common.Loader
import cpw.mods.fml.client.registry.ClientRegistry
import net.minecraftforge.client.MinecraftForgeClient
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import vazkii.botania.client.render.item.RenderLens
import ninja.shadowfox.shadowfox_botany.client.core.MultipartHandler
import ninja.shadowfox.shadowfox_botany.client.render.entity.RenderGrieferCreeper
import ninja.shadowfox.shadowfox_botany.client.render.tile.RenderTileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.common.entity.EntityGrieferCreeper
import ninja.shadowfox.shadowfox_botany.common.entity.EntityVoidCreeper

public class ClientProxy : CommonProxy() {
    override fun preInit(event: FMLPreInitializationEvent) {
        super.preInit(event)
    }

    override fun init(event: FMLInitializationEvent) {
        super.init(event)

        this.initRenderers()

        if (Loader.isModLoaded("ForgeMultipart"))
            MultipartHandler
    }

    override fun postInit(event: FMLPostInitializationEvent) {
        super.postInit(event)
    }

    private fun initRenderers() {
        MinecraftForgeClient.registerItemRenderer(ShadowFoxItems.invisibleFlameLens, RenderLens())

        // ClientRegistry.bindTileEntitySpecialRenderer(TileItemDisplay::class.java, RenderTileItemDisplay())

        RenderingRegistry.registerEntityRenderingHandler(EntityGrieferCreeper::class.java, RenderGrieferCreeper())
        RenderingRegistry.registerEntityRenderingHandler(EntityVoidCreeper::class.java, RenderGrieferCreeper())
    }
}
