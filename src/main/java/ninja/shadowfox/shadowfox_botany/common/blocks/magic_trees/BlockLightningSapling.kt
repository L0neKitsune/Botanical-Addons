package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenerator
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredSapling
import ninja.shadowfox.shadowfox_botany.common.world.SimpleTreeGen
import java.util.*

/**
 * Created by l0nekitsune on 12/7/15.
 */
class BlockLightningSapling() : BlockColoredSapling() {
    override fun growTree(world: World?, x: Int, y: Int, z: Int, random: Random?) {
        if(world != null) {
            if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return

            val plantedOn: Block = world.getBlock(x, y - 1, z)

            if(canGrowHere(plantedOn)) {
                val l = world.getBlockMetadata(x, y, z)

                val obj: WorldGenerator = SimpleTreeGen(5)

                world.setBlock(x, y, z, Blocks.air, 0, 4)

                if (!obj.generate(world, random, x, y, z)) {
                    world.setBlock(x, y, z, this, l, 4)
                }
            }
        }
    }

    override fun canGrowHere(block: Block): Boolean {
        return block.material == Material.ground || block.material == Material.grass
    }
}
