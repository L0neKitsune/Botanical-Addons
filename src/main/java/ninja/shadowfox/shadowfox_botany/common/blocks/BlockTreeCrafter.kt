package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxTileContainer
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileTreeCrafter
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.wand.IWandHUD
import vazkii.botania.common.block.tile.TileEnchanter
import vazkii.botania.common.block.tile.TileRuneAltar
import java.util.*


class BlockTreeCrafter(): ShadowFoxTileContainer<TileTreeCrafter>(Material.wood), IWandHUD, ILexiconable {
    internal var random: Random
    override val registerInCreative: Boolean = false

    init {
        setHardness(3.0f)
        setResistance(5.0f)
        setLightLevel(1.0f)
        setStepSound(Block.soundTypeStone)
        setBlockName("treeCrafter")
        random = Random()
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    override fun getIcon(p_149691_1_: Int, p_149691_2_: Int): IIcon? {
        return ShadowFoxBlocks.rainbowPlanks.getIcon(p_149691_1_, p_149691_2_)
    }

    override fun createNewTileEntity(var1: World, var2: Int): TileTreeCrafter {
        return TileTreeCrafter()
    }

    override fun hasComparatorInputOverride(): Boolean {
        return true
    }

    override fun getComparatorInputOverride(par1World: World?, par2: Int, par3: Int, par4: Int, par5: Int): Int {
        val crafter = par1World!!.getTileEntity(par2, par3, par4) as TileTreeCrafter
        return crafter.signal
    }

//    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
//        return true
//    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random?, p_149650_3_: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.coloredPlanks)
    }

    override fun renderHUD(mc: Minecraft, res: ScaledResolution, world: World, x: Int, y: Int, z: Int) {
        (world.getTileEntity(x, y, z) as TileTreeCrafter).renderHUD(mc, res)
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.treeCrafting
    }
}
