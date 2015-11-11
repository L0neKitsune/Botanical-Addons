package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ColorfulItem
import vazkii.botania.api.item.IBlockProvider
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania

public open class ColorfulDirtRod(name : String = "colorfulDirtRod") : ColorfulItem(name), IManaUsingItem, IBlockProvider {

    val COST : Int = 150

    init {
        setMaxStackSize(1)
    }

    override fun onItemUse(par1ItemStack : ItemStack, par2EntityPlayer : EntityPlayer, par3World : World,
                           par4 : Int, par5 : Int, par6 : Int, par7 : Int, par8 : Float, par9 : Float, par10 : Float) : Boolean{
        return place(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, ShadowFoxBlocks.coloredDirtBlock, COST, 0.35F, 0.2F, 0.05F)
    }

    companion object {
        public fun place(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3World: World,
                         par4: Int, par5: Int, par6: Int, par7: Int, par8: Float, par9: Float,
                         par10: Float, block: Block, cost: Int, r: Float, g: Float, b: Float): Boolean {

            if (ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, cost, false)) {
                val dir = ForgeDirection.getOrientation(par7)

                val aabb =  AxisAlignedBB.getBoundingBox((par4 + dir.offsetX).toDouble(),
                        (par5+dir.offsetY).toDouble(), (par6+dir.offsetZ).toDouble(),
                        (par4+dir.offsetX+1).toDouble(), (par5+dir.offsetY+1).toDouble(), (par6+dir.offsetZ+1).toDouble())
                val entities = par3World.getEntitiesWithinAABB(EntityLivingBase::class.java, aabb).size

                if (entities == 0) {
                    val stackToPlace : ItemStack = ItemStack(block, 1, par1ItemStack.itemDamage)
                    stackToPlace.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10)

                    if (stackToPlace.stackSize == 0) {
                        ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, cost, true)
                        for (i in 0..6)
                            Botania.proxy.sparkleFX(par3World, par4 + dir.offsetX + Math.random(), par5 + dir.offsetY + Math.random(), par6 + dir.offsetZ + Math.random(), r, g, b, 1F, 5)
                    }
                }
            }

            return true
        }
    }

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {
        if(!world.isRemote && player.isSneaking) {
            if (stack.itemDamage >= 15) stack.itemDamage = 0 else stack.itemDamage++
            world.playSoundAtEntity(player, "botania:ding", 0.1F, 1F)
        }

        return stack
    }

    override fun isFull3D() : Boolean {
        return true
    }

    override fun usesMana(stack : ItemStack) : Boolean {
        return true
    }

    override fun provideBlock(player : EntityPlayer, requestor : ItemStack, stack: ItemStack, block: Block, meta: Int, doit: Boolean) : Boolean{
        if(block == ShadowFoxBlocks.coloredDirtBlock)
            return !doit || ManaItemHandler.requestManaExactForTool(requestor, player, COST, true)
        return false
    }

    override fun getBlockCount(player: EntityPlayer, requestor: ItemStack, stack: ItemStack, block: Block, meta: Int) : Int {
        if(block == ShadowFoxBlocks.coloredDirtBlock)
            return -1
        return 0
    }
}
