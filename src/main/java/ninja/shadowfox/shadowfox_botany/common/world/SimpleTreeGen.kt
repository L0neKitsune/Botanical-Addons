package ninja.shadowfox.shadowfox_botany.common.world

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import java.util.*


class SimpleTreeGen(val minTreeHeight: Int) : WorldGenAbstractTree(true) {

    init {}

    override fun generate(world: World?, random: Random?, x: Int, y: Int, z: Int): Boolean {
        if (world != null) {
            val l: Int = random!!.nextInt(3) + minTreeHeight
            var flag: Boolean = true

            var variant = ShadowFoxAPI.getTreeVariant(world.getBlock(x, y-1, z), world.getBlockMetadata(x, y - 1, z))
            if (variant != null) {

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
                                    block = world.getBlock(j1, i1, i2)

                                    if (!block.isReplaceable(world, j1, i1, i2) && !block.isLeaves(world, j1, i1, i2) && block != variant.wood) {
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
                        var block2: Block = world.getBlock(x, y - 1, z)
                        val soilMeta = world.getBlockMetadata(x, y - 1, z)

                        if (y < 256 - l - 1) {
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
                                                setBlockAndNotifyAdequately(world, i2, k1, k2, variant.leaves, variant.getMeta(block2, soilMeta, variant.leaves))
                                            }
                                        }
                                    }
                                }
                            }

                            for (k1 in 0..l - 1) {
                                block = world.getBlock(x, y + k1, z)

                                if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z)) {
                                    setBlockAndNotifyAdequately(world, x, y + k1, z, variant.wood, variant.getMeta(block2, soilMeta, variant.wood))
                                }
                            }
                            return true
                        }
                        else return false
                    }
                }
            }
        }
        return false

    }

    fun isWood(block: Block): Boolean {
        return block.getMaterial() == Material.wood
    }
}
