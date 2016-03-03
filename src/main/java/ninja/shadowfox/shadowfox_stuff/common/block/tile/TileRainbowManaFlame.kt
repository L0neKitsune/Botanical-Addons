package ninja.shadowfox.shadowfox_stuff.common.block.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.FMLLaunchHandler
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.integration.coloredlights.ColoredLightHelper
import java.awt.Color
import java.util.*

/**
 * 1.8 mod
 * created on 2/25/16
 */
class TileRainbowManaFlame() : TileMod() {
    private val TAG_INVISIBLE = "invisible"
    var invisible = false

    fun getColor(): Int {
        if (FMLLaunchHandler.side().isServer) return 0xFFFFFF
        var time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks
        time += Random((this.pos.x xor this.pos.y xor this.pos.z).toLong()).nextInt(100000)
        return Color.HSBtoRGB(time * 0.005F, 1F, 1F)
    }

    public override fun updateEntity() {
        val c = 0.3f
        if (this.worldObj.isRemote && Math.random() < c.toDouble()) {
            val v = 0.1f
            val r = (getColor() shr 16 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
            val g = (getColor() shr 8 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
            val b = (getColor() and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
            val w = 0.15f
            val h = 0.05f
            val x = this.pos.x.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
            val y = this.pos.y.toDouble() + 0.25 + (Math.random() - 0.5) * h.toDouble()
            val z = this.pos.z.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
            val s = 0.2f + Math.random().toFloat() * 0.1f
            val m = 0.03f + Math.random().toFloat() * 0.015f
            Botania.proxy.wispFX(this.worldObj, x, y, z, r, g, b, s, -m)
        }
    }

    override fun writeCustomNBT(cmp: NBTTagCompound) {
        cmp.setBoolean(TAG_INVISIBLE, this.invisible)
    }

    override fun readCustomNBT(cmp: NBTTagCompound) {
        this.invisible = cmp.getBoolean(TAG_INVISIBLE)
    }

    override fun shouldRenderInPass(pass: Int): Boolean = Botania.proxy.isClientPlayerWearingMonocle || !this.invisible
}
