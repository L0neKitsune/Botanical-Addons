package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.util.ITickable
import net.minecraft.world.World

/**
 * 1.8 mod
 * created on 2/25/16
 */

open class TileMod : TileEntity(), ITickable {

    override fun shouldRefresh(world: World?, pos: BlockPos?, oldState: IBlockState, newState: IBlockState): Boolean {
        return oldState.block !== newState.block
    }

    override fun writeToNBT(par1nbtTagCompound: NBTTagCompound) {
        super.writeToNBT(par1nbtTagCompound)
        this.writeCustomNBT(par1nbtTagCompound)
    }

    override fun readFromNBT(par1nbtTagCompound: NBTTagCompound) {
        super.readFromNBT(par1nbtTagCompound)
        this.readCustomNBT(par1nbtTagCompound)
    }

    open fun writeCustomNBT(cmp: NBTTagCompound) { }

    open fun readCustomNBT(cmp: NBTTagCompound) { }

    override fun getDescriptionPacket(): Packet<*> {
        val nbttagcompound = NBTTagCompound()
        this.writeCustomNBT(nbttagcompound)
        return S35PacketUpdateTileEntity(this.pos, -999, nbttagcompound)
    }

    override fun onDataPacket(net: NetworkManager?, packet: S35PacketUpdateTileEntity?) {
        super.onDataPacket(net, packet)
        this.readCustomNBT(packet!!.nbtCompound)
    }

    override fun update() {
        if (!this.isInvalid && this.worldObj.isBlockLoaded(this.getPos(), false)) {
            if (!this.worldObj.isRemote) {
                this.updateEntity()
            } else {
                try {
                    this.updateEntity()
                } catch (var3: NullPointerException) {
                    var3.printStackTrace()
                }

            }
        }
    }

    protected open fun updateEntity() { }
}
