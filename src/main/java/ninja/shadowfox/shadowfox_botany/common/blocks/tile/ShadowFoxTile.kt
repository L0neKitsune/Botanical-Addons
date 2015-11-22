package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

/**
 * Created by l0nekitsune on 11/21/15.
 */
abstract class ShadowFoxTile() : TileEntity() {
    abstract fun writeCustomNBT(nbttagcompound: NBTTagCompound)
    abstract fun readCustomNBT(nbttagcompound: NBTTagCompound)

    override fun readFromNBT(nbttagcompound: NBTTagCompound) {
        super.readFromNBT(nbttagcompound)
        this.readCustomNBT(nbttagcompound)
    }

    override fun writeToNBT(nbttagcompound: NBTTagCompound) {
        super.writeToNBT(nbttagcompound)
        this.writeCustomNBT(nbttagcompound)
    }

    override fun getDescriptionPacket(): Packet {
        val nbttagcompound = NBTTagCompound()
        this.writeCustomNBT(nbttagcompound)
        return S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound)
    }

    override fun onDataPacket(net: NetworkManager?, pkt: S35PacketUpdateTileEntity?) {
        super.onDataPacket(net, pkt)
        this.readCustomNBT(pkt!!.func_148857_g())
    }
}