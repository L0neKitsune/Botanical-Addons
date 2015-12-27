package ninja.shadowfox.shadowfox_botany.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import vazkii.botania.common.block.ModBlocks as BotaniaBlocks


class BlockMarker() : ShadowFoxBlockMod(Material.wood) {

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

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    override fun getIcon(side: Int, meta: Int): IIcon? {
        return BotaniaBlocks.manaBeacon.getIcon(side, meta)
    }
}
