package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.Block
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxTileContainer
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileTreeCrafter
import java.util.*


class BlockTreeCrafter(): ShadowFoxTileContainer<TileTreeCrafter>(Material.ground) {
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
        return Blocks.lapis_block.getIcon(p_149691_1_, p_149691_2_)
    }

    override fun createNewTileEntity(var1: World, var2: Int): TileTreeCrafter {
        return TileTreeCrafter()
    }

    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
        return true
    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random?, p_149650_3_: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.coloredDirtBlock)
    }
}
