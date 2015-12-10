package ninja.shadowfox.shadowfox_botany.common.world

import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import java.util.*


class SimpleTreeGen(val minTreeHeight: Int) : WorldGenAbstractTree(true) {

    init {}

    override fun generate(world: World?, random: Random?, x: Int, y: Int, z: Int): Boolean {
        val l: Int = random!!.nextInt(3) + minTreeHeight
        var flag: Boolean = true

        if (y >= 1 && y + l + 1 <= 256) {
            var b0 : Byte
            var block : Block

            isGen@ for (i1 in y..(y + 1 + l)) {
                b0 = 1

                if (i1 == y) b0 = 0
                if (i1 >= (y + 1 + l - 2)) b0 = 2


                for (j1 in (x - b0)..(x + b0)) {
                    for (i2 in (z - b0)..(z + b0)) {
                        if (i1 >= 0 && i1 < 256) {
                            block = world!!.getBlock(j1, i1, i2)

                            if (!block.isReplaceable(world, j1, i1, i2)) {
                                flag = false
                                break@isGen
                            }
                        }
                        else {
                            flag = false
                            break@isGen
                        }
                    }
                }
            }

            if (!flag) return false

            else {
                var block2: Block = world!!.getBlock(x, y - 1, z)
                val soilMeta = world.getBlockMetadata(x, y - 1, z)

                var isSoil: Boolean = (block2 == ShadowFoxBlocks.coloredDirtBlock || block2 == ShadowFoxBlocks.rainbowDirtBlock)

                if (isSoil && y < 256 - l - 1) {
                    block2.onPlantGrow(world, x, y - 1, z, x, y, z)
                    b0 = 3
                    var b1: Byte = 0
                    var l1: Int
                    var j2: Int
                    var i3: Int

                    for (k1 in y - b0 + l..(y + l)) {
                        i3 = k1 - (y + l)
                        l1 = b1 + 1 - i3 / 2

                        for (i2 in (x - l1)..(x + l1)) {
                            j2 = i2 - x

                            for (k2 in z - l1..z + l1) {
                                var l2: Int = k2 - z

                                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0) {
                                    var block1: Block = world.getBlock(i2, k1, k2)

                                    if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
                                        var leavesType = if (soilMeta > 8) ShadowFoxBlocks.irisLeaves1 else ShadowFoxBlocks.irisLeaves0
                                        if(block2 == ShadowFoxBlocks.coloredDirtBlock) {
                                            setBlockAndNotifyAdequately(world, i2, k1, k2, leavesType, soilMeta % 8)
                                        } else {
                                            setBlockAndNotifyAdequately(world, i2, k1, k2, ShadowFoxBlocks.rainbowLeaves, 0)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (k1 in 0..l - 1) {
                        block = world.getBlock(x, y + k1, z)

                        if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z)) {
                            if(block2 == ShadowFoxBlocks.coloredDirtBlock){
                            var woodType : Block
                            when ((soilMeta / 4).toInt()) {
                                1 -> woodType = ShadowFoxBlocks.irisWood1
                                2 -> woodType = ShadowFoxBlocks.irisWood2
                                3 -> woodType = ShadowFoxBlocks.irisWood3
                                else -> {
                                    woodType = ShadowFoxBlocks.irisWood0
                                }
                            }

                            setBlockAndNotifyAdequately(world, x, y + k1, z, woodType, soilMeta and 3)
                            }
                            else {
                                setBlockAndNotifyAdequately(world, x, y + k1, z, ShadowFoxBlocks.rainbowWood, 0)

                            }
                        }
                    }
                    return true
                }
                else return false
            }
        }
        else return false

    }
}
