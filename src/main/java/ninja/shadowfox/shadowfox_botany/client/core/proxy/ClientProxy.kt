package ninja.shadowfox.shadowfox_botany.client.core.proxy

import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.client.MinecraftForgeClient
import ninja.shadowfox.shadowfox_botany.client.core.multipart.MultipartHandler
import ninja.shadowfox.shadowfox_botany.client.render.entity.RenderGrieferCreeper
import ninja.shadowfox.shadowfox_botany.client.render.tile.MultipassRenderer
import ninja.shadowfox.shadowfox_botany.client.render.tile.RenderStar
import ninja.shadowfox.shadowfox_botany.client.render.tile.RenderTileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.blocks.BlockFunnel
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredDoubleGrass
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileEntityStar
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.common.entity.EntityGrieferCreeper
import ninja.shadowfox.shadowfox_botany.common.entity.EntityVoidCreeper
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.lib.Constants
import vazkii.botania.client.render.item.RenderLens

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

        Constants.doubleFlowerRenderID = RenderingRegistry.getNextAvailableRenderId()
        RenderingRegistry.registerBlockHandler(BlockColoredDoubleGrass.ColoredDoublePlantRenderer())
        Constants.multipassRenderingID = RenderingRegistry.getNextAvailableRenderId()
        RenderingRegistry.registerBlockHandler(MultipassRenderer())
        Constants.hopperRenderingID = RenderingRegistry.getNextAvailableRenderId()
        RenderingRegistry.registerBlockHandler(BlockFunnel.HopperRenderer())

        ClientRegistry.bindTileEntitySpecialRenderer(TileItemDisplay::class.java, RenderTileItemDisplay())
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStar::class.java, RenderStar())
        //        EntityRegistry.registerModEntity(EntityThrowableItem::class.java, "shadowfox_botany:thrownItem", 0, ShadowfoxBotany.instance, 64, 10, true)

        RenderingRegistry.registerEntityRenderingHandler(EntityGrieferCreeper::class.java, RenderGrieferCreeper())
        RenderingRegistry.registerEntityRenderingHandler(EntityVoidCreeper::class.java, RenderGrieferCreeper())
    }
}
