package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxSlabs
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import kotlin.text.replace
import kotlin.text.toRegex


open class BlockRainbowWoodSlab(full: Boolean, source: Block = ShadowFoxBlocks.rainbowPlanks): ShadowFoxSlabs(full, 0, source, source.unlocalizedName.replace("tile.".toRegex(), "") + "Slab" + (if (full) "Full" else "")), IFuelHandler {
    init {
        this.setHardness(1.5f)
        this.setResistance(10.0f)
        GameRegistry.registerFuelHandler(this)
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("axe"))
    }

    override fun getHarvestTool(metadata : Int): String {
        return "axe"
    }

    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.rainbowSlabsFull as BlockSlab
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.rainbowSlabs as BlockSlab
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
        return (source as ILexiconable).getEntry(world, x, y, z, player, lexicon)
    }

    override fun getBurnTime(fuel: ItemStack): Int {
        return if (fuel.item == Item.getItemFromBlock(this)) 150 else 0
    }
}
