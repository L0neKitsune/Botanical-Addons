package ninja.shadowfox.shadowfox_botany.common.lexicon

import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.ShadowfoxBotany
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.compat.thaumcraft.ThaumcraftSuffusionRecipes
import ninja.shadowfox.shadowfox_botany.common.core.handler.ConfigHandler
import ninja.shadowfox.shadowfox_botany.common.crafting.ModRecipes
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.lexicon.LexiconRecipeMappings
import vazkii.botania.common.Botania
import vazkii.botania.common.lexicon.LexiconData
import vazkii.botania.common.lexicon.page.*

public object LexiconRegistry {

    val coloredDirt: LexiconEntry
    val irisSapling: LexiconEntry
    val technicolor: LexiconEntry
    val lightningRod: LexiconEntry
    val interdictionRod: LexiconEntry
    val pastoralSeeds: LexiconEntry
    val coatOfArms: LexiconEntry
    val colorOverride: LexiconEntry
    val treeCrafting: LexiconEntry
    val dendrology: ShadowFoxLexiconCategory
    val attribution: LexiconEntry
    val sealCreepers: LexiconEntry
    val kindling: LexiconEntry
    val waveRod: LexiconEntry
    val itemDisplay: LexiconEntry
    val lightningSapling: LexiconEntry
    val livingwoodFunnel: LexiconEntry
    val netherSapling: LexiconEntry
    val toolbelt: LexiconEntry
    val lamp: LexiconEntry
    val silencer: LexiconEntry
    val amp: LexiconEntry
    val crysanthermum: LexiconEntry
    val specialAxe: LexiconEntry

    lateinit var tctrees: LexiconEntry

    init {

        dendrology = ShadowFoxLexiconCategory("dendrology", 1)

        coloredDirt = ShadowfoxLexiconEntry("coloredDirt", BotaniaAPI.categoryMisc, ShadowFoxBlocks.rainbowDirtBlock)
        coloredDirt.setLexiconPages(PageText("0"), PageCraftingRecipe("1", ModRecipes.recipesColoredDirt))

        technicolor = ShadowfoxLexiconEntry("technicolorRod", BotaniaAPI.categoryTools, ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, 16)).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        technicolor.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesColoredSkyDirtRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfSif))

        lightningRod = ShadowfoxLexiconEntry("lightningRod", BotaniaAPI.categoryTools, ShadowFoxItems.lightningRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        lightningRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesLightningRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfThor))

        irisSapling = ShadowfoxLexiconEntry("irisSapling", dendrology, ShadowFoxBlocks.irisSapling)
        irisSapling.setPriority().setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesWoodPanel),
                PageCraftingRecipe("2", ModRecipes.recipesSlabs),
                PageCraftingRecipe("3", ModRecipes.recipesStairsR + ModRecipes.recipesStairsL),
                PageCraftingRecipe("4", ModRecipes.recipesSlabsFull),
                PageCraftingRecipe("5", ModRecipes.recipesLeafDyes))

        pastoralSeeds = ShadowfoxLexiconEntry("irisSeeds", BotaniaAPI.categoryTools, ShadowFoxBlocks.rainbowGrass)
        pastoralSeeds.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesRedstoneRoot),
                PageManaInfusionRecipe("2", ModRecipes.recipesPastoralSeeds))

        coatOfArms = ShadowfoxLexiconEntry("coatOfArms", BotaniaAPI.categoryMisc, ItemStack(ShadowFoxItems.coatOfArms, 1, 16))
        coatOfArms.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesCoatOfArms))

        colorOverride = ShadowfoxLexiconEntry("colorOverride", BotaniaAPI.categoryMisc, ShadowFoxItems.colorOverride).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        colorOverride.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesColorOverride))

        interdictionRod = ShadowfoxLexiconEntry("interdictionRod", BotaniaAPI.categoryTools, ShadowFoxItems.interdictionRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        interdictionRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesInterdictionRod),
                PageText("2"),
                PageCraftingRecipe("3", ModRecipes.recipesPriestOfNjord))

        treeCrafting = ShadowfoxLexiconEntry("treeCrafting", dendrology, ShadowFoxBlocks.treeCrafterBlockRB)
        treeCrafting.setPriority().setLexiconPages(PageText("0"),
                PageText("1"),
                PageMultiblock("2", ShadowFoxBlocks.treeCrafter))

        attribution = ShadowfoxLexiconEntry("attribution", BotaniaAPI.categoryBaubles, ShadowFoxItems.attributionBauble)
        attribution.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesAttribution))

        sealCreepers = ShadowfoxLexiconEntry("sealCreepers", BotaniaAPI.categoryBasics, ShadowFoxItems.wiltedLotus)
        if (ConfigHandler.blackLotusDropRate > 0.0)
            sealCreepers.setLexiconPages(PageText("0"),
                    PageText("1Drop"))
        else
            sealCreepers.setLexiconPages(PageText("0"),
                    PageText("1NoDrop"))

        kindling = ShadowfoxLexiconEntry("kindling", BotaniaAPI.categoryMisc, ShadowFoxBlocks.kindling)
        kindling.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesKindling))

        waveRod = ShadowfoxLexiconEntry("waveRod", BotaniaAPI.categoryTools, ShadowFoxItems.rainbowRod).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        waveRod.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesRainbowRod))

        itemDisplay = ShadowfoxLexiconEntry("itemDisplay", BotaniaAPI.categoryMisc, ItemStack(ShadowFoxBlocks.itemDisplay, 1, 1))
        itemDisplay.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesItemDisplay))

        livingwoodFunnel = ShadowfoxLexiconEntry("livingwoodFunnel", BotaniaAPI.categoryMisc, ItemStack(ShadowFoxBlocks.livingwoodFunnel, 1))
        livingwoodFunnel.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesLivingwoodFunnel))

        lightningSapling = ShadowfoxLexiconEntry("lightningSapling", dendrology, ShadowFoxBlocks.lightningSapling)
        lightningSapling.setLexiconPages(PageText("0"),
                PageTreeCrafting("1", ModRecipes.recipesLightningTree),
                PageCraftingRecipe("2", ModRecipes.recipesThunderousPlanks),
                PageCraftingRecipe("3", ModRecipes.recipesThunderousSlabs),
                PageCraftingRecipe("4", ModRecipes.recipesThunderousStairsR),
                PageCraftingRecipe("5", ModRecipes.recipesThunderousTwig),
                PageFurnaceRecipe("6", ItemStack(ShadowFoxBlocks.lightningPlanks)))

        netherSapling = ShadowfoxLexiconEntry("infernalSapling", dendrology, ShadowFoxBlocks.netherSapling)
        netherSapling.setLexiconPages(PageText("0"),
                PageTreeCrafting("1", ModRecipes.recipesInfernalTree),
                PageCraftingRecipe("2", ModRecipes.recipesInfernalPlanks),
                PageCraftingRecipe("3", ModRecipes.recipesInfernalSlabs),
                PageCraftingRecipe("4", ModRecipes.recipesInfernalStairsR),
                PageCraftingRecipe("5", ModRecipes.recipesInfernalTwig),
                PageFurnaceRecipe("6", ItemStack(ShadowFoxBlocks.netherWood)),
                PageFurnaceRecipe("7", ItemStack(ShadowFoxBlocks.netherPlanks)))

        toolbelt = ShadowfoxLexiconEntry("toolbelt", BotaniaAPI.categoryBaubles, ShadowFoxItems.toolbelt).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        toolbelt.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesToolbelt))

        lamp = ShadowfoxLexiconEntry("lamp", BotaniaAPI.categoryMisc, ShadowFoxBlocks.irisLamp).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        lamp.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesLamp))

        silencer = ShadowfoxLexiconEntry("silencer", dendrology, ShadowFoxBlocks.sealingSapling).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        silencer.setLexiconPages(PageText("0"),
                PageTreeCrafting("1", ModRecipes.recipesSealingTree),
                PageCraftingRecipe("2", ModRecipes.recipesSealingPlanks),
                PageCraftingRecipe("3", ModRecipes.recipesSealingSlabs),
                PageCraftingRecipe("4", ModRecipes.recipesSealingStairsR))

        amp = ShadowfoxLexiconEntry("amp", BotaniaAPI.categoryMisc, ShadowFoxBlocks.amp).setKnowledgeType(BotaniaAPI.elvenKnowledge)
        amp.setLexiconPages(PageText("0"),
                PageCraftingRecipe("1", ModRecipes.recipesAmplifier))

        crysanthermum = ShadowfoxLexiconEntry("crysanthermum", BotaniaAPI.categoryGenerationFlowers, BotaniaAPI.internalHandler.getSubTileAsStack("crysanthermum"))
        crysanthermum.setLexiconPages(PageText("0"),
                PageText("1"),
                PagePetalRecipe("2", ModRecipes.recipeCrysanthermum))

        specialAxe = ShadowFoxRelicEntry("andmyaxe", BotaniaAPI.categoryAlfhomancy, ShadowFoxItems.wireAxe).setKnowledgeType(BotaniaAPI.relicKnowledge)
        specialAxe.setLexiconPages(PageText("0"),
            PageText("1"))

        if (ThaumcraftSuffusionRecipes.recipesLoaded) {
            tctrees = object : ShadowfoxLexiconEntry("tctrees", dendrology, ItemStack(ThaumcraftSuffusionRecipes.plantBlock)) {
                override fun getSubtitle(): String {
                    return "[Botanical Addons x Thaumcraft]"
                }
            }.setKnowledgeType(BotaniaAPI.elvenKnowledge)
            tctrees.setLexiconPages(PageText("0"),
                    PageTreeCrafting("1", ThaumcraftSuffusionRecipes.greatwoodRecipe),
                    PageTreeCrafting("2", ThaumcraftSuffusionRecipes.silverwoodRecipe),
                    PageText("3"),
                    PageTreeCrafting("4", ThaumcraftSuffusionRecipes.shimmerleafRecipe),
                    PageTreeCrafting("5", ThaumcraftSuffusionRecipes.cinderpearlRecipe),
                    PageTreeCrafting("6", ThaumcraftSuffusionRecipes.vishroomRecipe))

            LexiconRecipeMappings.map(ItemStack(ThaumcraftSuffusionRecipes.plantBlock, 1, 0), tctrees, 1)
            LexiconRecipeMappings.map(ItemStack(ThaumcraftSuffusionRecipes.plantBlock, 1, 1), tctrees, 2)
            LexiconRecipeMappings.map(ItemStack(ThaumcraftSuffusionRecipes.plantBlock, 1, 2), tctrees, 4)
            LexiconRecipeMappings.map(ItemStack(ThaumcraftSuffusionRecipes.plantBlock, 1, 3), tctrees, 5)
            LexiconRecipeMappings.map(ItemStack(ThaumcraftSuffusionRecipes.plantBlock, 1, 5), tctrees, 6)
        }


        var memes = LexiconData.tinyPotato
        for (entry in BotaniaAPI.getAllEntries()) {
            if (entry.getUnlocalizedName() == "botania.entry.wrap") {
                memes = entry
            }
        }

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisSapling), irisSapling, 0)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.lightningRod), lightningRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.interdictionRod), interdictionRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.rainbowRod), waveRod, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 0), lightningRod, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 1), technicolor, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.emblem, 1, 2), interdictionRod, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.colorOverride), colorOverride, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLamp), lamp, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.attributionBauble, 1, 0), attribution, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.attributionBauble, 1, 1), memes, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.wiltedLotus, 1, 0), sealCreepers, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.wiltedLotus, 1, 1), sealCreepers, 1)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.treeCrafterBlock), treeCrafting, 2)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.treeCrafterBlockRB), treeCrafting, 2)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningSapling), lightningSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningWood), lightningSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningLeaves), lightningSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningPlanks), lightningSapling, 2)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningSlabs), lightningSapling, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.lightningStairs), lightningSapling, 4)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.resource, 1, 0), lightningSapling, 5)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.resource, 1, 1), lightningSapling, 6)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherSapling), netherSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherWood), netherSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherLeaves), netherSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherPlanks), netherSapling, 2)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherSlabs), netherSapling, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.netherStairs), netherSapling, 4)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.resource, 1, 2), netherSapling, 5)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.resource, 1, 3), netherSapling, 6)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.resource, 1, 4), netherSapling, 7)

        LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.toolbelt), toolbelt, 1)

        for (i in 0..2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.itemDisplay, 1, i), itemDisplay, 1)

        for (i in 0..3) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood0, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood1, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood2, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisWood3, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altWood0, 1, i), irisSapling, 1)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altWood1, 1, 0), irisSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altWood1, 1, 1), irisSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowWood), irisSapling, 1)
        for (i in 0..15) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredPlanks, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredSlabs[i], 1), irisSapling, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredStairs[i], 1), irisSapling, 3)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, i), coloredDirt, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisGrass, 1, i), pastoralSeeds, 0)
        }
        for (i in 0..5) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altPlanks, 1, i), irisSapling, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altSlabs[i], 1), irisSapling, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.altStairs[i], 1), irisSapling, 3)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowLeaves), irisSapling, 5)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowPlanks), irisSapling, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowSlabs), irisSapling, 2)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowStairs), irisSapling, 3)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowDirtBlock), coloredDirt, 1)
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowGrass), pastoralSeeds, 0)
        for (i in 0..16) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.colorfulSkyDirtRod, 1, i), technicolor, 1)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.irisSeeds, 1, i), pastoralSeeds, 2)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxItems.coatOfArms, 1, i), coatOfArms, 1)
        }
        for (i in 0..7) {
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLeaves0, 1, i), irisSapling, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisLeaves1, 1, i), irisSapling, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass0, 1, i), pastoralSeeds, 0)
            LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.irisTallGrass1, 1, i), pastoralSeeds, 0)
        }
        LexiconRecipeMappings.map(ItemStack(ShadowFoxBlocks.rainbowTallGrass), pastoralSeeds, 0)

    }
}
