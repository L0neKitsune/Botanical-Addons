package ninja.shadowfox.shadowfox_stuff.common.block

import net.minecraft.block.Block
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_stuff.common.block.minesweeper.BlockMine
import ninja.shadowfox.shadowfox_stuff.common.block.sound.BlockMuffler
import ninja.shadowfox.shadowfox_stuff.common.block.tile.TileRainbowManaFlame

/**
 * 1.8 mod
 * created on 2/20/16
 */
object ModBlocks {
    val muffler: Block
    val rainbowFlame: Block
    val blockMine: Block

    init {
        muffler = BlockMuffler()
        rainbowFlame = BlockManaFlame()
        blockMine = BlockMine()


        GameRegistry.registerTileEntity(TileRainbowManaFlame::class.java, "shadowfox_stuff:manaRainbowFlame")

    }
}
