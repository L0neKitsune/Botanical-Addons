package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.StatCollector
import net.minecraft.world.World

import net.minecraftforge.common.util.ForgeDirection

import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileRainbowManaFlame
import ninja.shadowfox.shadowfox_botany.common.item.StandardItem

import vazkii.botania.api.item.IPhantomInkable
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.core.helper.ItemNBTHelper

class RainbowLightRod : StandardItem("rainbowLightRod"), IManaUsingItem, IPhantomInkable {

    val COST = 100

    init {
        setMaxStackSize(1)
    }

    override fun onItemUse(par1ItemStack : ItemStack, par2EntityPlayer : EntityPlayer, par3World : World,
                           x : Int, y : Int, z : Int, direction : Int, par8 : Float, par9 : Float, par10 : Float) : Boolean {
        if (par3World.getBlock(x, y, z) == ShadowFoxBlocks.rainbowFlame) {
            par3World.setBlock(x, y, z, Blocks.air)
            return true
        }
        var toPlace = ItemStack(ShadowFoxBlocks.rainbowFlame)
        if (ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, COST, false)) {
                val dir = ForgeDirection.getOrientation(direction)

                val aabb = AxisAlignedBB.getBoundingBox((x + dir.offsetX).toDouble(),
                        (y+dir.offsetY).toDouble(), (z+dir.offsetZ).toDouble(),
                        (x+dir.offsetX+1).toDouble(), (y+dir.offsetY+1).toDouble(), (z+dir.offsetZ+1).toDouble())
                val entities = par3World.getEntitiesWithinAABB(EntityLivingBase::class.java, aabb).size

                if (entities == 0) {
                    toPlace!!.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, x, y, z, direction, par8, par9, par10)
                    if (toPlace.stackSize == 0) {
                        ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, COST, true)
                        val tile = par3World.getTileEntity(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ)
                        if (tile is TileRainbowManaFlame) {
                            tile.invisible = hasPhantomInk(par1ItemStack)
                        }
                    }
                }
            }

            return true
    }

    fun addStringToTooltip(s : String, tooltip : MutableList<Any?>) {
        tooltip.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, fpar4: Boolean) {
        if(par1ItemStack == null) return
        if (hasPhantomInk(par1ItemStack))
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), par3List)
    }

    override fun usesMana(stack: ItemStack): Boolean {
        return true
    }

    override fun hasPhantomInk(stack: ItemStack) : Boolean {
        return ItemNBTHelper.getBoolean(stack, "invisible", false)
    }

    override fun setPhantomInk(stack: ItemStack, ink: Boolean) {
        ItemNBTHelper.setBoolean(stack, "invisible", ink)
    }

    override fun isFull3D(): Boolean {
        return true
    }
}
