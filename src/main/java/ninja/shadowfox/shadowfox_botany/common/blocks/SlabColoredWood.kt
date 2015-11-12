package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockSlab
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import java.awt.Color
import java.util.*

class SlabColoredWood(val full: Boolean, val meta: Int, val source: Block = ShadowFoxBlocks.coloredPlanks) : BlockSlab(full, source.material), ILexiconable
{
    var name: String

    init {
        this.setHardness(1.5f)
        this.setResistance(10.0f)
        name = source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "") + meta
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

    fun register() {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockSlab::class.java, name)
    }

    override fun func_150002_b(i: Int): String {
        return this.name
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(m: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab
    }

    fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
        return LexiconRegistry.irisSapling
    }
}
