package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockSlab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import java.awt.Color
import java.util.*

/**
 * Created by l0nekitsune on 10/24/15.
 */
abstract class ShadowFoxSlabs(full: Boolean, mat: Material) : BlockSlab(full, mat) {

    init {
        if (!full) {
            setCreativeTab(ShadowFoxCreativeTab)
            useNeighborBrightness = true
        }
    }

    override fun setBlockName(par1Str: String): Block {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, par1Str)
        return super.setBlockName(par1Str)
    }

    abstract val fullBlock: BlockSlab
    abstract val singleBlock: BlockSlab

    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        return ItemStack(singleBlock)
    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random?, p_149650_3_: Int): Item {
        return Item.getItemFromBlock(singleBlock)
    }

    override fun quantityDropped(meta: Int, fortune: Int, random: Random): Int {
        return super.quantityDropped(meta, fortune, random)
    }

    public override fun createStackedBlock(par1: Int): ItemStack {
        return ItemStack(singleBlock)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        // NO-OP
    }

    override fun func_150002_b(i: Int): String {
        return unlocalizedName
    }

//    override fun getEntry(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, lexicon: ItemStack): LexiconEntry {
//        return LexiconData.decorativeBlocks
//    }

}