package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxLeaves
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

/**
 * Created by l0nekitsune on 12/7/15.
 */
class BlockLightningLeaves(): ShadowFoxLeaves() {
    init {

    }

    override fun quantityDropped(random: Random): Int {
        throw UnsupportedOperationException()
    }

    override fun func_150125_e(): Array<out String>? {
        throw UnsupportedOperationException()
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return null
    }
}
