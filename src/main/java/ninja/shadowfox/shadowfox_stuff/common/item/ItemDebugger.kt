package ninja.shadowfox.shadowfox_stuff.common.item

import mod.chiselsandbits.chiseledblock.BlockChiseled
import mod.chiselsandbits.chiseledblock.TileEntityBlockChiseled
import mod.chiselsandbits.chiseledblock.TileEntityBlockChiseledTESR
import mod.chiselsandbits.core.api.ChiselAndBitsAPI
import net.minecraft.block.state.BlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraftforge.fml.common.FMLLog
import org.apache.logging.log4j.Level

/**
 * 1.8 mod
 * created on 2/29/16
 */
class ItemDebugger(): ItemMod("itemDebugger") {

    init {

    }

    override fun onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (pos != null && !worldIn.isRemote) {
            var blockState = worldIn.getBlockState(pos)
            var block = blockState.block

            var tile = worldIn.getTileEntity(pos)

            if(tile != null && tile is TileEntityBlockChiseled) {
                var info = tile.getBlob()

                playerIn.addChatMessage(ChatComponentText("${info}"))
            }
        }

        return true
    }
}
