package ninja.shadowfox.shadowfox_botany.common.world

import net.minecraft.block.Block
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import java.util.*


class SimpleTreeGen(val minTreeHeight: Int) : WorldGenAbstractTree(true) {

    init {
    }

    override fun generate(world: World?, random: Random?, pos: BlockPos): Boolean {
        if (world != null) {
            val l: Int = random!!.nextInt(3) + minTreeHeight
            var flag: Boolean = true

            var variant = ShadowFoxAPI.getTreeVariant(world.getBlockState(pos.down()))
            if (variant != null) {
                val wood = variant.getWood(world.getBlockState(pos.down()).block, world.getBlockState(pos.down()).block.getMetaFromState(world.getBlockState(pos.down())))
                val leaves = variant.getLeaves(world.getBlockState(pos.down()).block, world.getBlockState(pos.down()).block.getMetaFromState(world.getBlockState(pos.down())))

                if (pos.y >= 1 && pos.y + l + 1 <= 256) {
                    var b0: Byte
                    var block: Block

                    isGen@ for (i1 in pos.y..(pos.y + 1 + l)) {
                        b0 = 1

                        if (i1 == pos.y) b0 = 0
                        if (i1 >= (pos.y + 1 + l - 2)) b0 = 2


                        for (j1 in (pos.x - b0)..(pos.x + b0)) {
                            for (i2 in (pos.z - b0)..(pos.z + b0)) {
                                if (i1 >= 0 && i1 < 256) {
                                    block = world.getBlockState(BlockPos(j1, i1, i2)).block

                                    if (!block.isReplaceable(world, BlockPos(j1, i1, i2)) && !block.isLeaves(world, BlockPos(j1, i1, i2)) && block != wood) {
                                        flag = false
                                        break@isGen
                                    }
                                } else {
                                    flag = false
                                    break@isGen
                                }
                            }
                        }
                    }

                    if (!flag) return false
                    else {
                        var block2: Block = world.getBlockState(pos.down()).block
                        val soilMeta = block2.getMetaFromState(world.getBlockState(pos.down()))

                        if (pos.y < 256 - l - 1) {
                            block2.onPlantGrow(world, pos.down(), pos)
                            b0 = 3
                            var b1: Byte = 0
                            var l1: Int
                            var j2: Int
                            var i3: Int

                            for (k1 in pos.y - b0 + l..(pos.y + l)) {
                                i3 = k1 - (pos.y + l)
                                l1 = b1 + 1 - i3 / 2

                                for (i2 in (pos.x - l1)..(pos.x + l1)) {
                                    j2 = i2 - pos.x

                                    for (k2 in pos.z - l1..pos.z + l1) {
                                        var l2: Int = k2 - pos.z

                                        if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0) {
                                            var block1: Block = world.getBlockState(BlockPos(i2, k1, k2)).block

                                            if (block1.isAir(world, BlockPos(i2, k1, k2)) || block1.isLeaves(world, BlockPos(i2, k1, k2))) {
                                                setBlockAndNotifyAdequately(world, BlockPos(i2, k1, k2), leaves.getStateFromMeta(variant.getMeta(block2, soilMeta, leaves)))
                                            }
                                        }
                                    }
                                }
                            }

                            for (k1 in 0..l - 1) {
                                block = world.getBlockState(pos.add(0, k1, 0)).block

                                if (block.isAir(world, pos.add(0, k1, 0)) || block.isLeaves(world,pos.add(0, k1, 0))) {
                                    setBlockAndNotifyAdequately(world, pos.add(0, k1, 0), wood.getStateFromMeta(variant.getMeta(block2, soilMeta, wood)))
                                }
                            }
                            return true
                        } else return false
                    }
                }
            }
        }
        return false

    }
}
