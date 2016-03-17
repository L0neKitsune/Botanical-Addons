package ninja.shadowfox.shadowfox_botany.common.item.base

import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.stats.Achievement
import vazkii.botania.common.achievement.ICraftAchievement
import vazkii.botania.common.achievement.IPickupAchievement

/**
 * BotanicalAddons
 * created on 3/16/16
 */
open class ItemBlockWithMetadataAndName(par2Block: Block) : ItemBlock(par2Block), IPickupAchievement, ICraftAchievement {
    init {
        this.setHasSubtypes(true)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("tile.".toRegex(), "tile.shadowfox_botany:")
    }

    override fun getUnlocalizedName(par1ItemStack: ItemStack?): String {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack!!.itemDamage
    }

    override fun getAchievementOnCraft(stack: ItemStack, player: EntityPlayer, matrix: IInventory): Achievement? {
        return if (this.block is ICraftAchievement) this.block.getAchievementOnCraft(stack, player, matrix) else null
    }

    override fun getAchievementOnPickup(stack: ItemStack, player: EntityPlayer, item: EntityItem): Achievement? {
        return if (this.block is IPickupAchievement) this.block.getAchievementOnPickup(stack, player, item) else null
    }

    override fun getMetadata(damage: Int): Int {
        return damage
    }
}
