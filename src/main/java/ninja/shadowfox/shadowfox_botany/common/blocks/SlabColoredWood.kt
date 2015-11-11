package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraft.world.IBlockAccess
import java.awt.Color
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry

class SlabColoredWood(val full: Boolean, val source: Block) : ShadowFoxSlabs(full, source.material), ILexiconable {
    private val TYPES = 16

    init {
        setStepSound(source.stepSound)
        setBlockName(source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else ""))
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        val meta = world!!.getBlockMetadata(x, y, z)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override val singleBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabs as BlockSlab
    override val fullBlock: BlockSlab get() = ShadowFoxBlocks.coloredSlabsFull as BlockSlab

    @SideOnly(Side.CLIENT)
    override fun getIcon(par1: Int, par2: Int): IIcon {
        return source.getIcon(par1, par2)
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
