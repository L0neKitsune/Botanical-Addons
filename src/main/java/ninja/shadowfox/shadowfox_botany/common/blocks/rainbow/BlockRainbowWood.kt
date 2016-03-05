package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxRotatedPillar
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemIridescentBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry


class BlockRainbowWood() : ShadowFoxRotatedPillar(Material.wood), ILexiconable {

    private val name = "rainbowWood"

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = Block.soundTypeWood

        setBlockName(name)
    }

    override fun register(par1Str: String) {
        GameRegistry.registerBlock(this, ItemIridescentBlockMod::class.java, par1Str)
    }

    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, fortune: Int) {
        var b0: Byte = 4
        var i1: Int = b0 + 1

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (j1 in -b0..b0) for (k1 in -b0..b0)
                for (l1 in -b0..b0) {
                    var blockInWorld: Block = world.getBlock(x + j1, y + k1, z + l1)
                    if (blockInWorld.isLeaves(world, x + j1, y + k1, z + l1)) {
                        blockInWorld.beginLeavesDecay(world, x + j1, y + k1, z + l1)
                    }
                }
        }
    }

    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true

    override fun isInterpolated(): Boolean = true


    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
