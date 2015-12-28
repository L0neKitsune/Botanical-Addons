package ninja.shadowfox.shadowfox_botany.common.lexicon

import java.util.ArrayList
import java.util.Arrays

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.init.Blocks
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ResourceLocation
import net.minecraft.util.StatCollector
import net.minecraftforge.oredict.OreDictionary

import org.lwjgl.opengl.GL11

import vazkii.botania.api.internal.IGuiLexiconEntry
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.lexicon.LexiconRecipeMappings
import vazkii.botania.api.recipe.RecipeManaInfusion
import vazkii.botania.client.core.handler.HUDHandler
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.client.core.helper.RenderHelper
import vazkii.botania.client.lib.LibResources
import vazkii.botania.client.render.tile.RenderTilePool
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.block.tile.mana.TilePool
import vazkii.botania.common.lexicon.page.PageRecipe
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly

public class PageFurnaceRecipe: PageRecipe {

    private class StackPair(val input: ItemStack, val output: ItemStack) {}

    private val manaInfusionOverlay = ResourceLocation(LibResources.GUI_MANA_INFUSION_OVERLAY)
    private val furnaceFlame = ResourceLocation("shadowfox_botany:textures/gui/furnaceFlames.png")

    private var recipes: MutableList<StackPair>
    var ticksElapsed = 0
    var renderTicksElapsed = 0
    var recipeAt = 0

    constructor(unlocalizedName:String, inputs:MutableList<ItemStack>) : super(unlocalizedName) {
        recipes = ArrayList()
        for (inp in inputs) {
            val output = FurnaceRecipes.smelting().getSmeltingResult(inp)
            if (output == null) throw IllegalArgumentException("Invalid input")
            recipes.add(StackPair(inp, output))
        }
    }
    constructor(unlocalizedName:String, input:ItemStack) : super(unlocalizedName) {
        val output = FurnaceRecipes.smelting().getSmeltingResult(input)
        if (output == null) throw IllegalArgumentException("Invalid input")
        recipes = arrayListOf(StackPair(input, output))
    }

    override fun onPageAdded(entry: LexiconEntry, index: Int) {
        for(recipe in recipes)
            LexiconRecipeMappings.map(recipe.output, entry, index)
    }

    @SideOnly(Side.CLIENT)
    override fun renderRecipe(gui: IGuiLexiconEntry, mx: Int, my: Int) {
        val recipe = recipes.get(recipeAt)
        val render = Minecraft.getMinecraft().renderEngine

        renderItemAtGridPos(gui, 1, 1, recipe.input, false)
        renderItemAtGridPos(gui, 2, 1, ItemStack(Blocks.furnace), false)
        renderItemAtGridPos(gui, 3, 1, recipe.output, false)

        renderTicksElapsed++

        render.bindTexture(furnaceFlame)
        val flameCornerX = gui.getLeft() + gui.getWidth()/2 - 8
        val flameCornerY = gui.getTop() + gui.getHeight()/2 + 5
        (gui as GuiScreen).drawTexturedModalRect(flameCornerX, flameCornerY, 0, 0, 16, 16)
        var progress = renderTicksElapsed * 13 / 50
        (gui as GuiScreen).drawTexturedModalRect(flameCornerX, flameCornerY + progress, 16, progress, 16, 16 - progress)
        
        render.bindTexture(manaInfusionOverlay)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glColor4f(1F, 1F, 1F, 1F)
        gui.drawTexturedModalRect(gui.getLeft(), gui.getTop(), 0, 0, gui.getWidth(), gui.getHeight())
        GL11.glDisable(GL11.GL_BLEND)
    }

    @SideOnly(Side.CLIENT)
    override fun updateScreen() {
        if(ticksElapsed % 20 == 0) {
            recipeAt++
            renderTicksElapsed = 0

            if(recipeAt == recipes.size)
                recipeAt = 0
        }
        ++ticksElapsed
    }
}
