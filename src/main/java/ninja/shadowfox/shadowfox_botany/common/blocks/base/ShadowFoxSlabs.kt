package ninja.shadowfox.shadowfox_botany.common.blocks.base

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxColoredItemBlock
import vazkii.botania.api.lexicon.ILexiconable
import java.util.*


abstract class ShadowFoxSlabs(val full: Boolean, val meta: Int, val source: Block, val name: String) :
        BlockSlab(full, source.material), ILexiconable {

    init {
        this.setStepSound(source.stepSound)
        this.setBlockName(name)
        if (!full) {
            setCreativeTab(ShadowFoxCreativeTab)
            this.useNeighborBrightness = true
        }

    }

    override fun setBlockName(p_149663_1_: String?): Block? {
        return super.setBlockName(name)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(par1: Int, par2: Int): IIcon {
        return source.getIcon(par1, par2)
    }

    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        return ItemStack(getSingleBlock())
    }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random?, p_149650_3_: Int): Item {
        return Item.getItemFromBlock(this.getSingleBlock())
    }

    override fun quantityDropped(meta: Int, fortune: Int, random: Random): Int {
        return super.quantityDropped(meta, fortune, random)
    }

    public override fun createStackedBlock(par1: Int): ItemStack {
        return ItemStack(getSingleBlock())
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    open fun register() {
        GameRegistry.registerBlock(this, ShadowFoxColoredItemBlock::class.java, name)
    }

    override fun func_150002_b(i: Int): String {
        return this.name
    }

    abstract fun getFullBlock(): BlockSlab
    abstract fun getSingleBlock(): BlockSlab

}
