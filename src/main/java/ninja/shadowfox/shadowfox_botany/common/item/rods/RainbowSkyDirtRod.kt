package ninja.shadowfox.shadowfox_botany.common.item.rods

import java.awt.Color
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.StatCollector
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.client.renderer.texture.IIconRegister
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.RainbowItem
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.item.IBlockProvider
import vazkii.botania.api.mana.IManaUsingItem
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.Vector3


class RainbowSkyDirtRod(name: String = "rainbowSkyDirtRod") : RainbowItem(name), IAvatarWieldable, IBlockProvider, IManaUsingItem {

    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarDirtRainbow.png")

    val COST = 150

    @SideOnly(Side.CLIENT)
    override fun registerIcons(iconRegister : IIconRegister) {
        this.itemIcon = IconHelper.forName(iconRegister, "colorfulSkyDirtRod")
        this.overlayIcon = IconHelper.forName(iconRegister, "colorfulSkyDirtRodOverlay")
    }

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {
        if(!world.isRemote) {
            if (ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, false)) {
                val color = Color(RainbowItem.getColor())
                val r = color.red.toFloat() / 255F
                val g = color.green.toFloat() / 255F
                val b = color.blue.toFloat() / 255F

                val sif = (ItemPriestEmblem.getEmblem(1, player) != null)
                var basePlayerRange = 5.0
                if (player is EntityPlayerMP)
                    basePlayerRange = player.theItemInWorldManager.blockReachDistance
                val distmultiplier = if (sif) basePlayerRange else 3.0

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
                    val stackToPlace: ItemStack = ItemStack(ShadowFoxBlocks.rainbowDirtBlock)
                    stackToPlace.tryPlaceItemIntoWorld(player, world, x, y, z, 0, 0F, 0F, 0F)

                    if (stackToPlace.stackSize == 0) {
                        ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, true)
                        for (i in 0..6)
                            Botania.proxy.sparkleFX(world, x + Math.random(), y + Math.random(), z + Math.random(),
                                    r, g, b, 1F, 5)
                    }
                }
            }
        }
        else
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
            world.setBlock(x+xl, y, z+zl, ShadowFoxBlocks.rainbowDirtBlock)
            tile.recieveMana(-COST)
            for (i in 0..6)
                Botania.proxy.sparkleFX(world, x+xl + Math.random(), y + Math.random(), z+zl + Math.random(),
                        r, g, b, 1F, 5)
            world.playAuxSFX(2001, x+xl, y, z+zl, Block.getIdFromBlock(ShadowFoxBlocks.rainbowDirtBlock))

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
        if(block == ShadowFoxBlocks.rainbowDirtBlock)
            return !doit || ManaItemHandler.requestManaExactForTool(requestor, player, COST, true)
        return false
    }

    override fun getBlockCount(player: EntityPlayer, requestor: ItemStack, stack: ItemStack, block: Block, meta: Int) : Int {
        if(block == ShadowFoxBlocks.rainbowDirtBlock)
            return -1
        return 0
    }
}
