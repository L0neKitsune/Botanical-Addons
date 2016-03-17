package ninja.shadowfox.shadowfox_botany.client.core.proxy

import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ninja.shadowfox.shadowfox_botany.client.core.render.entity.RenderGrieferCreeper
import ninja.shadowfox.shadowfox_botany.client.core.render.MultipassRenderer
import ninja.shadowfox.shadowfox_botany.client.core.render.RenderStar
import ninja.shadowfox.shadowfox_botany.client.core.render.tile.RenderTileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.blocks.BlockFunnel
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredDoubleGrass
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileEntityStar
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileItemDisplay
import ninja.shadowfox.shadowfox_botany.common.core.proxy.CommonProxy
import ninja.shadowfox.shadowfox_botany.common.entity.EntityGrieferCreeper
import ninja.shadowfox.shadowfox_botany.common.entity.EntityVoidCreeper
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.lib.Constants
//import vazkii.botania.client.render.item.RenderLens

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
//        MinecraftForgeClient.registerItemRenderer(ShadowFoxItems.invisibleFlameLens, RenderLens())
//
//        Constants.doubleFlowerRenderID = RenderingRegistry.getNextAvailableRenderId()
//        RenderingRegistry.registerBlockHandler(BlockColoredDoubleGrass.ColoredDoublePlantRenderer())
//        Constants.multipassRenderingID = RenderingRegistry.getNextAvailableRenderId()
//        RenderingRegistry.registerBlockHandler(MultipassRenderer())
//        Constants.hopperRenderingID = RenderingRegistry.getNextAvailableRenderId()
//        RenderingRegistry.registerBlockHandler(BlockFunnel.HopperRenderer())
//
//        ClientRegistry.bindTileEntitySpecialRenderer(TileItemDisplay::class.java, RenderTileItemDisplay())
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStar::class.java, RenderStar())
//        //        EntityRegistry.registerModEntity(EntityThrowableItem::class.java, "shadowfox_botany:thrownItem", 0, ShadowfoxBotany.instance, 64, 10, true)
//
//
//        //        RenderingRegistry.registerEntityRenderingHandler(EntityThrowableItem::class.java, RenderThrownItem())
//        RenderingRegistry.registerEntityRenderingHandler(EntityGrieferCreeper::class.java, RenderGrieferCreeper())
//        RenderingRegistry.registerEntityRenderingHandler(EntityVoidCreeper::class.java, RenderGrieferCreeper())
    }
}
