package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxLeaves
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*


class BlockLightningLeaves(): ShadowFoxLeaves() {
    init {
        setBlockName("lightningLeaves")
    }

    override fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, name)
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.lightningSapling)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(60) == 0) 1 else 0
    }

    override fun func_150125_e(): Array<out String>? {
        return arrayOf("lightningLeaves")
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return null
    }
}
