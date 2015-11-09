package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.block.BlockStairs
import net.minecraft.block.material.MapColor
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraft.world.IBlockAccess
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxStairsItemBlock0
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxStairsItemBlock1
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxStairsItemBlock2
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxStairsItemBlock3
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color


class StairsColoredWood(val source: Block, val colorSet: Int) : BlockStairs(source, 0), ILexiconable
{
    val TYPES: Int = 4

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
        setStepSound(source.stepSound)
        setBlockName(source.unlocalizedName.replace("tile.".toRegex(), "") + "Stairs${colorSet}")
    }

    override fun setBlockName(par1Str: String): Block {
        if (colorSet == 0) {
            GameRegistry.registerBlock(this, ShadowFoxStairsItemBlock0::class.java, par1Str)
        } else if (colorSet == 1) {
            GameRegistry.registerBlock(this, ShadowFoxStairsItemBlock1::class.java, par1Str)
        } else if (colorSet == 2) {
            GameRegistry.registerBlock(this, ShadowFoxStairsItemBlock2::class.java, par1Str)
        } else if (colorSet == 3) {
            GameRegistry.registerBlock(this, ShadowFoxStairsItemBlock3::class.java, par1Str)
        }
        return super.setBlockName(par1Str)
    }

    override fun damageDropped(p_149692_1_: Int): Int {
        return p_149692_1_ and 3
    }

    @SideOnly(Side.CLIENT)
    fun colorMeta(meta: Int): Int {
        return meta + (colorSet * 4)
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (colorMeta(meta / 4) >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[colorMeta(meta / 4)];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        val meta = p_149720_1_!!.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_)

        if (colorMeta(meta / 4) >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[colorMeta(meta / 4)];
        return Color(color[0], color[1], color[2]).rgb;
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if(list != null && item != null) {
            list.add(ItemStack(this, 1, 0))
            list.add(ItemStack(this, 1, 4))
            list.add(ItemStack(this, 1, 8))
            list.add(ItemStack(this, 1, 12))
        }
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
