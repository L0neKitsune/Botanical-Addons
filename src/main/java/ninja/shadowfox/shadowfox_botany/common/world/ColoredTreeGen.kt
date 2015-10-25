package ninja.shadowfox.shadowfox_botany.common.world

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import java.util.*


class ColoredTreeGen(val minTreeHeight: Int, val colorMeta: Int, val vinesGrow: Boolean) : WorldGenAbstractTree(true)
{

    init {}

    override fun generate(p_76484_1_: World?, p_76484_2_: Random?, p_76484_3_: Int, p_76484_4_: Int, p_76484_5_: Int): Boolean {
        val l: Int = p_76484_2_!!.nextInt(3) + minTreeHeight
        var flag: Boolean = true

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
        {
            var b0 : Byte
            var k1: Int
            var block : Block

            isGen@ for (i1 in p_76484_4_..(p_76484_4_ + 1 + l))
            {
                b0 = 1

                if (i1 == p_76484_4_)
                {
                    b0 = 0
                }

                if (i1 >= (p_76484_4_ + 1 + l - 2))
                {
                    b0 = 2
                }

                for (j1 in (p_76484_3_ - b0)..(p_76484_3_ + b0))
                {
                    for (i2 in (p_76484_5_ - b0)..(p_76484_5_ + b0))
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            block = p_76484_1_!!.getBlock(j1, i1, i2);

                            if (!this.isReplaceable(p_76484_1_, j1, i1, i2))
                            {
                                flag = false
                                break@isGen
                            }
                        }
                        else
                        {
                            flag = false
                            break@isGen
                        }
                    }
                }
            }

            if (!flag) return false

            else
            {
                var block2: Block = p_76484_1_!!.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);

                var isSoil: Boolean = block2 == ShadowFoxBlocks.coloredDirtBlock

                if (isSoil && p_76484_4_ < 256 - l - 1)
                {
                    block2.onPlantGrow(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, p_76484_3_, p_76484_4_, p_76484_5_);
                    b0 = 3;
                    var b1: Byte = 0
                    var l1: Int
                    var i2: Int
                    var j2: Int
                    var i3: Int

                    for (k1 in p_76484_4_ - b0 + l..(p_76484_4_ + l))
                    {
                        i3 = k1 - (p_76484_4_ + l);
                        l1 = b1 + 1 - i3 / 2;

                        for (i2 in (p_76484_3_ - l1)..(p_76484_3_ + l1))
                        {
                            j2 = i2 - p_76484_3_;

                            for (k2 in p_76484_5_ - l1..p_76484_5_ + l1)
                            {
                                var l2: Int = k2 - p_76484_5_;

                                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || p_76484_2_.nextInt(2) != 0 && i3 != 0)
                                {
                                    var block1: Block = p_76484_1_.getBlock(i2, k1, k2);

                                    if (block1.isAir(p_76484_1_, i2, k1, k2) || block1.isLeaves(p_76484_1_, i2, k1, k2))
                                    {
                                        this.setBlockAndNotifyAdequately(p_76484_1_, i2, k1, k2, ShadowFoxBlocks.irisLeaves, colorMeta);
                                    }
                                }
                            }
                        }
                    }

                    for (k1 in 0..l - 1)
                    {
                        block = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_);

                        if (block.isAir(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_) || block.isLeaves(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_))
                        {
                            this.setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_, ShadowFoxBlocks.irisWood, colorMeta);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false
        }
    }


    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
     */
    private fun growVines(p_76529_1_: World, p_76529_2_: Int, p_76529_3_: Int, p_76529_4_: Int, p_76529_5_: Int)
    {
        this.setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_);
        var i1 = 4;
        var p3 = p_76529_3_
        while (true)
        {
            p3--

            if (!p_76529_1_.getBlock(p_76529_2_, p_76529_3_, p_76529_4_).isAir(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_) || i1 <= 0)
            {
                return;
            }

            setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_)
            --i1
        }
    }
}