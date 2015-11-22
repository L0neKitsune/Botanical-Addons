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
//                par2World.chunkProvider.genNetherBridge.
            }

        }
        else if(!par2World.isRemote && par3EntityPlayer.dimension != -1)
        {
            par3EntityPlayer.addChatComponentMessage(ChatComponentText("You need to be in the Nether to use this."));
        }

        return par1ItemStack;
    }
}
