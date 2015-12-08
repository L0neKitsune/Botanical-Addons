package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenerator
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredSapling
import ninja.shadowfox.shadowfox_botany.common.world.HeartWoodTreeGen
import java.util.*


class BlockLightningSapling() : BlockColoredSapling(name = "magicalSapling") {
    override fun growTree(world: World?, x: Int, y: Int, z: Int, random: Random?) {
        if(world != null) {

            for (i in -64..64 + 1 - 1) {
                for (j in -64..64 + 1 - 1) {
                    for (k in -64..64 + 1 - 1) {
                        val xp = x + i
                        val yp = y + j
                        val zp = z + k
                        val block = world.getBlock(xp, yp, zp)
                        val meta = world.getBlockMetadata(xp, yp, zp)

                        if (block is BlockLightningWood && block.isHeartWood(meta)) {
                            world.setBlockMetadataWithNotify(x, y, z, 0, 3)
                            return
                        }
                    }
                }
            }

            if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return
            val plantedOn: Block = world.getBlock(x, y - 1, z)

            if(canGrowHere(plantedOn)) {
                val l = world.getBlockMetadata(x, y, z)

                val obj: WorldGenerator = HeartWoodTreeGen(5, ShadowFoxBlocks.lightningWood, 0,
                        ShadowFoxBlocks.lightningWood, 1, ShadowFoxBlocks.lightningLeaves, 0)

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
