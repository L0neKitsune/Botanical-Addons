package ninja.shadowfox.shadowfox_botany.common.core.proxy


import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions
import ninja.shadowfox.shadowfox_botany.common.compat.etfuturem.ShadowFoxBanners
import ninja.shadowfox.shadowfox_botany.common.compat.multipart.MultipartHandler
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.core.handler.HilarityHandler
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import ninja.shadowfox.shadowfox_botany.common.entity.ShadowFoxEntity
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.throwables.ShadowFoxThrowables
import vazkii.botania.common.item.ModItems

open class CommonProxy {
    open fun preInit(event: FMLPreInitializationEvent) {

        ShadowFoxAPI.RUNEAXE.setRepairItem(ItemStack(ModItems.manaResource, 1, 7)) // Elementium

        ConfigHandler.loadConfig(event.suggestedConfigurationFile)

        ShadowFoxBlocks
        ShadowFoxItems
        ModRecipes
        ShadowFoxPotions
        ShadowFoxEntity
        LexiconRegistry
        ShadowFoxThrowables

//        HilarityHandler.register()

//        BlockDispenser.dispenseBehaviorRegistry.putObject(ShadowFoxItems.resource, BifrostFlowerDispenserBehavior())

//        if (ShadowfoxBotany.thaumcraftLoaded) ThaumcraftAspects.initAspects()
    }

    open fun init(event: FMLInitializationEvent) {
        ShadowFoxBlocks.registerBurnables()

//        if (Loader.isModLoaded("ForgeMultipart"))
//            MultipartHandler
//        if (Loader.isModLoaded("etfuturem"))
//            ShadowFoxBanners
    }

    open fun postInit(event: FMLPostInitializationEvent) {
        ConfigHandler.loadPostInit()
//        if (ShadowfoxBotany.thaumcraftLoaded) ThaumcraftAspects.addAspects()
    }
}
