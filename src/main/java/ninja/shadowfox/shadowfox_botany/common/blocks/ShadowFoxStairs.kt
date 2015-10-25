package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.core.BotaniaCreativeTab
import vazkii.botania.common.item.block.ItemBlockMod
import vazkii.botania.common.lexicon.LexiconData

/**
 * Created by l0nekitsune on 10/24/15.
 */
open class ShadowFoxStairs(source: Block, meta: Int, name: String) : BlockStairs(source, meta) {

    init {
        setBlockName(name)
        setCreativeTab(ShadowFoxCreativeTab)
        useNeighborBrightness = true
    }

    override fun setBlockName(par1Str: String): Block {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, par1Str)
        return super.setBlockName(par1Str)
    }

//    override fun getEntry(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, lexicon: ItemStack): LexiconEntry {
//        return LexiconData.decorativeBlocks
//    }

}
