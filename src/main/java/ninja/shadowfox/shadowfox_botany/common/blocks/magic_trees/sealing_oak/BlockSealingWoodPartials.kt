package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.sealing_oak

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.world.World
import net.minecraftforge.client.event.sound.PlaySoundEvent17
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodSlab
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowWoodStairs
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemSlabMod

class BlockSealingWoodSlab(full: Boolean, source: Block = ShadowFoxBlocks.sealingPlanks) : BlockRainbowWoodSlab(full, source), ISoundSilencer {
    override fun getFullBlock(): BlockSlab {
        return ShadowFoxBlocks.sealingSlabsFull as BlockSlab
    }

    override fun register() {
        GameRegistry.registerBlock(this, ItemSlabMod::class.java, name)
    }

    override fun getSingleBlock(): BlockSlab {
        return ShadowFoxBlocks.sealingSlabs as BlockSlab
    }

    override fun canSilence(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Boolean = dist <= 8
    override fun getVolumeMultiplier(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Float = 0.5f
}

class BlockSealingWoodStairs(source: Block = ShadowFoxBlocks.sealingPlanks) : BlockRainbowWoodStairs(source), ISoundSilencer {
    override fun register() {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
    }

    override fun canSilence(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Boolean = dist <= 8
    override fun getVolumeMultiplier(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Float = 0.5f
}
