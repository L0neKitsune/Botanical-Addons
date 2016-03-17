package ninja.shadowfox.shadowfox_botany.common.blocks.tile


import vazkii.botania.common.Botania
import vazkii.botania.common.integration.coloredlights.ColoredLightHelper

abstract class TileManaFlame : TileMod() {

    abstract fun getColor(): Int

    abstract fun shouldRender(): Boolean

    override fun updateEntity() {
        try {
            if (shouldRender()) {
                val c = 0.3f
                if (Math.random() < c.toDouble()) {
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
        } catch (e: NullPointerException) {
        }
    }

    fun getLightColor(): Int {
        val r = (getColor() shr 16 and 255).toFloat() / 255.0f
        val g = (getColor() shr 8 and 255).toFloat() / 255.0f
        val b = (getColor() and 255).toFloat() / 255.0f
        return ColoredLightHelper.makeRGBLightValue(r, g, b, 1.0f)
    }

}
