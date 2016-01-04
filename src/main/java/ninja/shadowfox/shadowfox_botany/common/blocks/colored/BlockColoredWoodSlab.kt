package ninja.shadowfox.shadowfox_botany.common.blocks.colored

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxSlabs
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color

class BlockColoredWoodSlab(full: Boolean, meta: Int, source: Block = ShadowFoxBlocks.coloredPlanks) :
        ShadowFoxSlabs(full, meta, source, source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "") + meta), ILexiconable, IFuelHandler {
    init {
        this.setHardness(1.5f)
        this.setResistance(10.0f)
        GameRegistry.registerFuelHandler(this)
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(m: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("axe", true))
    }

    override fun getHarvestTool(metadata : Int): String {
        return "axe"
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabsFull[meta] as BlockSlab
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.coloredSlabs[meta] as BlockSlab
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
        return LexiconRegistry.irisSapling
    }

    override fun getBurnTime(fuel: ItemStack): Int {
        return if (fuel.item == Item.getItemFromBlock(this)) 150 else 0
    }
}
