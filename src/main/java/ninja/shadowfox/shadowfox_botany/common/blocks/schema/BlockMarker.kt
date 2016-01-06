package ninja.shadowfox.shadowfox_botany.common.blocks.schema

import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks


class BlockMarker() : BlockMod(Material.wood) {

    init {
        val size = 0.1875f
        this.setBlockBounds(size, size, size, 1.0f - size, 1.0f - size, 1.0f - size)
        this.setBlockName("schemaMarker")
    }

    override fun renderAsNormalBlock(): Boolean {
        return false
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
    }

    override fun getIcon(side: Int, meta: Int): IIcon? {
        return vazkii.botania.common.block.ModBlocks.manaBeacon.getIcon(side, meta)
    }
}
