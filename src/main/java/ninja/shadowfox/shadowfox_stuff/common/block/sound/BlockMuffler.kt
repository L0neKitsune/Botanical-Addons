package ninja.shadowfox.shadowfox_stuff.common.block.sound

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_stuff.common.core.CreativeTab
import ninja.shadowfox.shadowfox_stuff.common.item.ItemBlockMod

/**
 * 1.8 mod
 * created on 2/21/16
 */
class BlockMuffler(): Block(Material.cloth), ISoundSilencer {
    init {
        unlocalizedName = "muffler"
        blockHardness = 2f
        setCreativeTab(CreativeTab)
    }

    override fun setUnlocalizedName(name: String?): Block? {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
        return super.setUnlocalizedName(name)
    }

    override fun canSilence(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent): Boolean = true

    override fun getVolumeMultiplier(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent): Float {
        return .25f
    }
}
