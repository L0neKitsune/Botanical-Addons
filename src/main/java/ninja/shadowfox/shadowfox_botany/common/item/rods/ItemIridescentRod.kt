package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.ItemIridescent
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.item.IBlockProvider
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.Vector3
import java.awt.Color


class ItemIridescentRod(name: String = "colorfulSkyDirtRod") : ItemIridescent(name), IAvatarWieldable, IManaUsingItem, IBlockProvider {

    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarDirtRainbow.png")

    val COST = 150

    companion object {
        public fun place(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3World: World,
                         par4: Int, par5: Int, par6: Int, par7: Int, par8: Float, par9: Float,
                         par10: Float, toPlace: ItemStack?, cost: Int, r: Float, g: Float, b: Float): Boolean {

            if (ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, cost, false)) {
                val dir = ForgeDirection.getOrientation(par7)

                val aabb =  AxisAlignedBB.getBoundingBox((par4 + dir.offsetX).toDouble(),
                        (par5+dir.offsetY).toDouble(), (par6+dir.offsetZ).toDouble(),
                        (par4+dir.offsetX+1).toDouble(), (par5+dir.offsetY+1).toDouble(), (par6+dir.offsetZ+1).toDouble())
                val entities = par3World.getEntitiesWithinAABB(EntityLivingBase::class.java, aabb).size

                if (entities == 0) {
                    toPlace!!.tryPlaceItemIntoWorld(par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10)

                    if (toPlace.stackSize == 0) {
                        ManaItemHandler.requestManaExactForTool(par1ItemStack, par2EntityPlayer, cost, true)
                        for (i in 0..6)
                            Botania.proxy.sparkleFX(par3World, par4 + dir.offsetX + Math.random(), par5 + dir.offsetY + Math.random(), par6 + dir.offsetZ + Math.random(), r, g, b, 1F, 5)
                    }
                }
            }

            return true
        }
    }

    init {
        setMaxStackSize(1)
    }

    override fun onItemUse(par1ItemStack : ItemStack, par2EntityPlayer : EntityPlayer, par3World : World,
                           par4 : Int, par5 : Int, par6 : Int, par7 : Int, par8 : Float, par9 : Float, par10 : Float) : Boolean{
        return place(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, ItemIridescent.dirtStack(par1ItemStack.itemDamage), COST, 0.35F, 0.2F, 0.05F)
    }

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {
        var blockstack = ItemIridescent.dirtStack(stack.itemDamage)
        if (player.isSneaking) {
            var damage = stack.itemDamage
            if (!world.isRemote) {
                if (stack.itemDamage >= 16) stack.itemDamage = 0 else stack.itemDamage++
                damage = stack.itemDamage
            } else if (damage >= 16) damage = 0 else damage++
            world.playSoundAtEntity(player, "botania:ding", 0.1F, 1F)
            blockstack = ItemIridescent.dirtStack(damage)
            blockstack!!.setStackDisplayName(StatCollector.translateToLocal("misc.shadowfox_botany.color." + damage))
            ItemsRemainingRenderHandler.set(blockstack, -2)
        }

        else if (!world.isRemote && ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, false)) {

            val color = Color(getColorFromItemStack(stack, 0))
            val r = color.red / 255F
            val g = color.green / 255F
            val b = color.blue / 255F

            val sif = (ItemPriestEmblem.getEmblem(1, player) != null)
            var basePlayerRange = 5.0
            if (player is EntityPlayerMP)
                basePlayerRange = player.theItemInWorldManager.blockReachDistance
            val distmultiplier = if (sif) basePlayerRange-1 else 3.0

            val playerVec = Vector3.fromEntityCenter(player)
            val lookVec = Vector3(player.lookVec).multiply(distmultiplier)
            val placeVec = playerVec.copy().add(lookVec)

            val x = MathHelper.floor_double(placeVec.x)
            val y = MathHelper.floor_double(placeVec.y) + 1
            val z = MathHelper.floor_double(placeVec.z)

            val entities = world.getEntitiesWithinAABB(EntityLivingBase::class.java,
                    AxisAlignedBB.getBoundingBox(x.toDouble(), y.toDouble(), z.toDouble(), (x + 1).toDouble(),
                            (y + 1).toDouble(), (z + 1).toDouble())).size

            if (entities == 0) {
                blockstack!!.tryPlaceItemIntoWorld(player, world, x, y, z, 0, 0F, 0F, 0F)

                if (blockstack.stackSize == 0) {
                    ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, true)
                    for (i in 0..6)
                        Botania.proxy.sparkleFX(world, x + Math.random(), y + Math.random(), z + Math.random(),
                                r, g, b, 1F, 5)
                }
            }
        }
        if(world.isRemote)
            player.swingItem()

        return stack
    }

    override fun onAvatarUpdate(tile: IAvatarTile, stack: ItemStack) {
        var te = tile as TileEntity
        var world = te.worldObj
        val x = te.xCoord
        val y = te.yCoord
        val z = te.zCoord

        var xl = 0
        var zl = 0

        when (te.getBlockMetadata() - 2) {
            0 -> zl = -2
            1 -> zl = 2
            2 -> xl = -2
            3 -> xl = 2
        }

        val block = world.getBlock(x+xl, y, z+zl)

        val color = Color(getColorFromItemStack(stack, 0))
        val r = color.red / 255F
        val g = color.green / 255F
        val b = color.blue / 255F

        if (tile.currentMana >= COST && block.isAir(world, x+xl, y, z+zl) && tile.elapsedFunctionalTicks % 50 == 0 && tile.isEnabled) {
            world.setBlock(x+xl, y, z+zl, ItemIridescent.dirtFromMeta(stack.itemDamage), stack.itemDamage, 1 or 2)
            tile.recieveMana(-COST)
            for (i in 0..6)
                Botania.proxy.sparkleFX(world, x+xl + Math.random(), y + Math.random(), z+zl + Math.random(),
                        r, g, b, 1F, 5)
            if (stack.itemDamage == TYPES)
                world.playAuxSFX(2001, x+xl, y, z+zl, Block.getIdFromBlock(ShadowFoxBlocks.rainbowDirtBlock))
            else
                world.playAuxSFX(2001, x+xl, y, z+zl, Block.getIdFromBlock(ShadowFoxBlocks.coloredDirtBlock) + (stack.itemDamage shl 12))

        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }

    override fun isFull3D() : Boolean {
        return true
    }

    override fun usesMana(stack : ItemStack) : Boolean {
        return true
    }

    override fun provideBlock(player : EntityPlayer, requestor : ItemStack, stack: ItemStack, block: Block, meta: Int, doit: Boolean) : Boolean{
        if(block == ShadowFoxBlocks.coloredDirtBlock || block == ShadowFoxBlocks.rainbowDirtBlock)
            return !doit || ManaItemHandler.requestManaExactForTool(requestor, player, COST, true)
        return false
    }

    override fun getBlockCount(player: EntityPlayer, requestor: ItemStack, stack: ItemStack, block: Block, meta: Int) : Int {
        if(block == ShadowFoxBlocks.coloredDirtBlock || block == ShadowFoxBlocks.rainbowDirtBlock)
            return -1
        return 0
    }
}
