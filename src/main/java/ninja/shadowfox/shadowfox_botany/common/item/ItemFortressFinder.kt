package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World
import net.minecraft.world.gen.ChunkProviderHell


class ItemFortressFinder() : StandardItem("itemFortressFinder") {

    override fun onItemRightClick(par1ItemStack: ItemStack?, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack? {

        if(par1ItemStack == null || par2World == null || par3EntityPlayer == null) return par1ItemStack

        if (!par2World.isRemote && par3EntityPlayer.dimension == -1)
        {
            if (par2World.chunkProvider is ChunkProviderHell)
            {
                val mapStructure = (par2World.chunkProvider as ChunkProviderHell).genNetherBridge

                val pos = mapStructure.func_151545_a(par2World, par3EntityPlayer.posX.toInt(), par3EntityPlayer.posY.toInt(), par3EntityPlayer.posZ.toInt())
                if (pos != null) {
                    par3EntityPlayer.addChatComponentMessage(ChatComponentText("x:${pos.chunkPosX} y:${pos.chunkPosY} z:${pos.chunkPosZ}"))
                }
            }

        }
        else if(!par2World.isRemote && par3EntityPlayer.dimension != -1)
        {
            par3EntityPlayer.addChatComponentMessage(ChatComponentText("You need to be in the Nether to use this."))
        }

        return par1ItemStack
    }
}
