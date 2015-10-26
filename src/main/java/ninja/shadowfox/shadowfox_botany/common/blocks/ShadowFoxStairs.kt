package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.core.BotaniaCreativeTab
import vazkii.botania.common.item.block.ItemBlockMod
import vazkii.botania.common.lexicon.LexiconData
import java.awt.Color

/**
 * Created by l0nekitsune on 10/24/15.
 */
open class ShadowFoxStairs(source: Block, var meta: Int, name: String) : BlockStairs(source, meta) {

    init {
        setBlockName(name)
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
    }

    override fun setBlockName(par1Str: String): Block {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, par1Str)
        return super.setBlockName(par1Str)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(p_149741_1_: Int): Int {
        if (p_149741_1_ >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[p_149741_1_];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        val meta = p_149720_1_!!.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
    }

//    override fun getEntry(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, lexicon: ItemStack): LexiconEntry {
//        return LexiconData.decorativeBlocks
//    }

}
