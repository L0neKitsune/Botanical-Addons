package ninja.shadowfox.shadowfox_botany.common.core.proxy

import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions
import ninja.shadowfox.shadowfox_botany.common.compat.multipart.MultipartHandler
import ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft.ThaumcraftAspects
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.core.handler.HilarityHandler
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import ninja.shadowfox.shadowfox_botany.common.entity.ShadowFoxEntity
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.throwables.ShadowFoxThrowables
import vazkii.botania.common.item.ModItems

public open class CommonProxy {
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

        HilarityHandler.register()

        if (ShadowfoxBotany.thaumcraftLoaded) ThaumcraftAspects.initAspects()
    }

    open fun init(event: FMLInitializationEvent) {
        ShadowFoxBlocks.registerBurnables()

        if (Loader.isModLoaded("ForgeMultipart"))
            MultipartHandler
    }

    open fun postInit(event: FMLPostInitializationEvent) {
        ConfigHandler.loadPostInit()
        if (ShadowfoxBotany.thaumcraftLoaded) ThaumcraftAspects.addAspects()
    }
}
