package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
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
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockSlab
import java.awt.Color
import java.util.*

/**
 * Created by l0nekitsune on 10/24/15.
 */
abstract class ShadowFoxSlabs(full: Boolean, mat: Material, internal var name: String) : BlockSlab(full, mat) {

    init {
        setBlockName(name)
        if (!full) {
            setCreativeTab(ShadowFoxCreativeTab)
            useNeighborBrightness = true
        }
    }

    abstract val fullBlock: BlockSlab
    abstract val singleBlock: BlockSlab

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }


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

    fun register() {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockSlab::class.java, name)
    }

    override fun func_150002_b(i: Int): String {
        return name
    }

//    override fun getEntry(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, lexicon: ItemStack): LexiconEntry {
//        return LexiconData.decorativeBlocks
//    }

}