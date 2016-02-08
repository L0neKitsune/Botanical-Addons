package ninja.shadowfox.shadowfox_botany.common.item.blocks

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileEntityStar
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileRainbowManaFlame
import ninja.shadowfox.shadowfox_botany.common.item.ItemIridescent
import ninja.shadowfox.shadowfox_botany.common.item.ItemMod
import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.core.helper.ItemNBTHelper
import java.awt.Color
import java.util.*


/**
 * @author WireSegal
 * Created at 9:55 PM on 2/6/16.
 */
class ItemStarPlacer : ItemMod("starPlacer") {

    companion object {
        val TAG_COLOR = "color"

        val defaultColors = ArrayList<Int>()

        init {
            for (color in EntitySheep.fleeceColorTable) {
                var colorint = Color(color[0], color[1], color[2]).rgb
                colorint += 0xFFFFFF + 1
                defaultColors.add(colorint)
            }
            defaultColors.add(-1)
        }

        fun setColor(stack: ItemStack, color: Int) {
            ItemNBTHelper.setInt(stack, TAG_COLOR, color)
        }

        fun getColor(stack: ItemStack): Int {
            return ItemNBTHelper.getInt(stack, TAG_COLOR, -1)
        }

        fun colorStack(color: Int): ItemStack {
            val stack = ItemStack(ShadowFoxItems.star)
            setColor(stack, color)
            return stack
        }

        fun forColor(colorVal: Int): ItemStack {
            return colorStack(defaultColors[colorVal % defaultColors.size])
        }
    }

    override fun addInformation(stack: ItemStack, player: EntityPlayer?, list: MutableList<Any?>, par4: Boolean) {
        super.addInformation(stack, player, list, par4)
        val color = getColor(stack)
        if (color in defaultColors)
            list.add(StatCollector.translateToLocal("misc.shadowfox_botany.color.${defaultColors.indexOf(color)}"))
        else
            list.add("#${Integer.toHexString(color).toUpperCase()}")
    }

    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
        for (color in defaultColors) {
            list.add(colorStack(color))
        }
    }

    override fun getColorFromItemStack(stack: ItemStack, pass: Int): Int {
        val color = getColor(stack)
        if (color == -1) return ItemIridescent.rainbowColor()
        return color
    }

    override fun onItemUse(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3World: World,
                           x: Int, y: Int, z: Int, direction: Int, par8: Float, par9: Float, par10: Float): Boolean {
        if (par3World.isRemote) return false

        var toPlace = ItemStack(ShadowFoxBlocks.star)
        val dir = ForgeDirection.getOrientation(direction)
        if (par3World.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isAir(par3World, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
            toPlace.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, x, y, z, direction, par8, par9, par10)
            if (toPlace.stackSize == 0) {
                val tile = par3World.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
                if (tile is TileEntityStar)
                    tile.starColor = getColor(par1ItemStack)
                if (!par2EntityPlayer.capabilities.isCreativeMode) par1ItemStack.stackSize--
            }
            return true
        }
        return false
    }
}
