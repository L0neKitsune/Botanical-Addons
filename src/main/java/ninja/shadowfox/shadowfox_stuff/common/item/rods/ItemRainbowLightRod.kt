package ninja.shadowfox.shadowfox_stuff.common.item.rods

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_stuff.common.block.ModBlocks
import ninja.shadowfox.shadowfox_stuff.common.block.tile.TileRainbowManaFlame
import ninja.shadowfox.shadowfox_stuff.common.item.ItemMod
import vazkii.botania.api.item.IPhantomInkable
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.ItemNBTHelper
import java.awt.Color

/**
 * 1.8 mod
 * created on 2/21/16
 */
class ItemRainbowLightRod : ItemMod("rainbowLightRod"), IManaUsingItem, IPhantomInkable {

    val COST = 100

    init {
        setMaxStackSize(1)
    }

    override fun getColorFromItemStack(par1ItemStack: ItemStack, pass: Int): Int {
        if (pass > 0)
            return 0xFFFFFF
        return Color.HSBtoRGB(Botania.proxy.worldElapsedTicks * 2 % 360 / 360F, 1F, 1F)
    }

    override fun onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (worldIn.getBlockState(pos).block == ModBlocks.rainbowFlame) {
            worldIn.setBlockToAir(pos)
            worldIn.playSoundEffect(pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5, "random.fizz", 0.3F, Math.random().toFloat() * 0.4F + 0.8F)
            return true
        }
        var toPlace = ItemStack(ModBlocks.rainbowFlame)
        if (ManaItemHandler.requestManaExactForTool(stack, playerIn, COST, false)) {
            if (worldIn.isAirBlock(pos.offset(side))) {
                toPlace.onItemUse(playerIn, worldIn, pos, side, hitX, hitY, hitZ)
                if (toPlace.stackSize == 0) {
                    worldIn.playSoundEffect(pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5, "fire.ignite", 0.3F, Math.random().toFloat() * 0.4F + 0.8F)
                    ManaItemHandler.requestManaExactForTool(stack, playerIn, COST, true)
                    val tile = worldIn.getTileEntity(pos.offset(side))
                    if (tile is TileRainbowManaFlame) {
                        tile.invisible = hasPhantomInk(stack)
                    }
                }
                return true
            }
        }
        return false
    }

    fun addStringToTooltip(s: String, tooltip: MutableList<String>) {
        tooltip.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(stack: ItemStack?, playerIn: EntityPlayer?, tooltip: MutableList<String>, advanced: Boolean) {
        if (stack == null) return
        if (hasPhantomInk(stack))
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), tooltip)
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true
    }

    override fun hasPhantomInk(stack: ItemStack): Boolean {
        return ItemNBTHelper.getBoolean(stack, "invisible", false)
    }

    override fun setPhantomInk(stack: ItemStack, ink: Boolean) {
        ItemNBTHelper.setBoolean(stack, "invisible", ink)
    }

    override fun isFull3D(): Boolean {
        return true
    }
}
