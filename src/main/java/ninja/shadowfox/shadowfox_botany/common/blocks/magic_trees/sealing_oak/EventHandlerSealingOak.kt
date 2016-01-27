package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.sealing_oak

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.ITickableSound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.sound.PlaySoundEvent17
import net.minecraftforge.common.MinecraftForge

/**
 * @author WireSegal
 * Created at 8:49 AM on 1/27/16.
 */
class EventHandlerSealingOak {

    companion object {
        val instance = EventHandlerSealingOak()

        fun register() {
            MinecraftForge.EVENT_BUS.register(instance)
        }
    }

    val MAXRANGE = 16

    @SubscribeEvent
    fun onSound(event: PlaySoundEvent17) {
        if (Minecraft.getMinecraft().theWorld != null && event.result != null) {
            val world = Minecraft.getMinecraft().theWorld
            if (event.result !is ITickableSound) {

                val x = event.result.xPosF
                val y = event.result.yPosF
                val z = event.result.zPosF

                var volumeMultiplier = 1f

                for (dx in (x - MAXRANGE).toInt()/16..(x + MAXRANGE).toInt()/16) {
                    for (dz in (z - MAXRANGE).toInt()/16..(z + MAXRANGE).toInt()/16) {
                        val dxactual = dx * 16
                        val dzactual = dz * 16
                        for (te in world.getChunkFromChunkCoords(dxactual, dzactual).chunkTileEntityMap.values) {
                            val tile = te as TileEntity
                            val block = te.getBlockType()
                            if (block is ISoundSilencer) {
                                var distance = dist(tile, x, y, z)
                                if (block.canSilence(world, te.xCoord, te.yCoord, te.zCoord, distance, event)) {
                                    volumeMultiplier *= block.getVolumeMultiplier(world, te.xCoord, te.yCoord, te.zCoord, distance, event)
                                }
                            }
                        }
                    }
                }
                if (volumeMultiplier != 1f) {
                    event.result = LowVolumeSound(event.result, volumeMultiplier)
                }
            }
        }

    }

    inner class LowVolumeSound(val sound: ISound, val volumeMult: Float) : ISound {
        override fun getPositionedSoundLocation(): ResourceLocation = this.sound.positionedSoundLocation
        override fun canRepeat(): Boolean = this.sound.canRepeat()
        override fun getRepeatDelay(): Int = this.sound.repeatDelay
        override fun getVolume(): Float = this.sound.volume * volumeMult
        override fun getPitch(): Float = this.sound.pitch
        override fun getXPosF(): Float = this.sound.xPosF
        override fun getYPosF(): Float = this.sound.yPosF
        override fun getZPosF(): Float = this.sound.zPosF
        override fun getAttenuationType(): ISound.AttenuationType = this.sound.attenuationType
    }

    private fun dist(tileEntity: TileEntity, x: Float, y: Float, z: Float): Double {
        val xd = Math.pow(tileEntity.xCoord + 0.5 - x, 2.0)
        val yd = Math.pow(tileEntity.yCoord + 0.5 - y, 2.0)
        val zd = Math.pow(tileEntity.zCoord + 0.5 - z, 2.0)
        return Math.sqrt(xd + yd + zd)
    }
}
