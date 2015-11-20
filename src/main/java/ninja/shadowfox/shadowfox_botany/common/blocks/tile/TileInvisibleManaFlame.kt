package ninja.shadowfox.shadowfox_botany.common.blocks.tile


import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import vazkii.botania.common.Botania

class TileInvisibleManaFlame() : TileEntity() {

    internal var color = 2162464

    override fun updateEntity() {
        if(Botania.proxy.isClientPlayerWearingMonocle) {
            val c = 0.3f
            if (Math.random() < c.toDouble()) {
                val v = 0.1f
                val r = (this.color shr 16 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                val g = (this.color shr 8 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                val b = (this.color and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
                val w = 0.15f
                val h = 0.05f
                val x = this.xCoord.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
                val y = this.yCoord.toDouble() + 0.25 + (Math.random() - 0.5) * h.toDouble()
                val z = this.zCoord.toDouble() + 0.5 + (Math.random() - 0.5) * w.toDouble()
                val s = 0.2f + Math.random().toFloat() * 0.1f
                val m = 0.03f + Math.random().toFloat() * 0.015f
                Botania.proxy.wispFX(this.worldObj, x, y, z, r, g, b, s, -m)
            }
        }
    }

    override fun writeToNBT(par1nbtTagCompound: NBTTagCompound) {
        super.writeToNBT(par1nbtTagCompound)
        this.writeCustomNBT(par1nbtTagCompound)
    }

    override fun readFromNBT(par1nbtTagCompound: NBTTagCompound) {
        super.readFromNBT(par1nbtTagCompound)
        this.readCustomNBT(par1nbtTagCompound)
    }

    fun writeCustomNBT(cmp: NBTTagCompound) {
    }

    fun readCustomNBT(cmp: NBTTagCompound) {
    }

    override fun getDescriptionPacket(): Packet {
        val nbttagcompound = NBTTagCompound()
        this.writeCustomNBT(nbttagcompound)
        return S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound)
    }

    override fun onDataPacket(net: NetworkManager?, packet: S35PacketUpdateTileEntity?) {
        super.onDataPacket(net, packet)
        this.readCustomNBT(packet!!.func_148857_g())
    }
}