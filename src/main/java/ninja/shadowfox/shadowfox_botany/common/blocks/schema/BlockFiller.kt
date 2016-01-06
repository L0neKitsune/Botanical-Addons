package ninja.shadowfox.shadowfox_botany.common.blocks.schema

import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.common.block.ModBlocks

/**
 * Created by l0nekitsune on 1/3/16.
 */
class BlockFiller() : ShadowFoxBlockMod(Material.wood) {

    init {
//        val size = 0.1875f
//        this.setBlockBounds(size, size, size, 1.0f - size, 1.0f - size, 1.0f - size)
        this.setBlockName("fillerBlock")
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }
}
