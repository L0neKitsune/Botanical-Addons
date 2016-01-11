package ninja.shadowfox.shadowfox_botany.common.network

import cpw.mods.fml.common.network.ByteBufUtils
import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.MessageContext
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.ByteBuf
import net.minecraft.item.ItemStack


class PlayerItemMessage(var item: ItemStack? = null) : IMessage {
    override fun fromBytes(buf: ByteBuf?) {
        buf?.let {
            item = ByteBufUtils.readItemStack(buf)
        }
    }

    override fun toBytes(buf: ByteBuf?) {
        ByteBufUtils.writeItemStack(buf, item)
    }
}

class PlayerItemMessageHandler() : IMessageHandler<PlayerItemMessage, IMessage> {

    override fun onMessage(message: PlayerItemMessage?, ctx: MessageContext?): IMessage? {
        if (ctx != null && message != null && message.item != null && ctx.side.isServer) {
            val player = ctx.serverHandler.playerEntity

            var heldItem = player.currentEquippedItem

            if (heldItem == null) {
                player.setCurrentItemOrArmor(0, message.item!!.copy())
            } else if (!player.inventory.addItemStackToInventory(message.item!!.copy())) {
                player.dropPlayerItemWithRandomChoice(message.item!!.copy(), false)
            }
        }

        return null
    }
}
