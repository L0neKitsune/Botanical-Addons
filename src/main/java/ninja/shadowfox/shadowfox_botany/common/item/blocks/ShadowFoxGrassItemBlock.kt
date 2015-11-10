package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.ItemBlockWithMetadata
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import java.awt.Color


class ShadowFoxGrassItemBlock(par2Block: Block) : ShadowFoxMetaItemBlock(par2Block) {
    override fun getColorFromItemStack(par1ItemStack : ItemStack, pass : Int) : Int {
        if(par1ItemStack.itemDamage >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[par1ItemStack.itemDamage];
        return Color(color[0], color[1], color[2]).rgb;
    }
}