package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.item.ItemSlab
import net.minecraft.item.ItemStack
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxSlabs

/**
 * Created by l0nekitsune on 10/24/15.
 */

class ShadowFoxItemBlockSlab(par1: Block) : ItemSlab(par1, (par1 as ShadowFoxSlabs).singleBlock, par1.fullBlock, false) {

    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
        return field_150939_a.unlocalizedName.replace("tile.".toRegex(), "tile.botania:")
    }

}