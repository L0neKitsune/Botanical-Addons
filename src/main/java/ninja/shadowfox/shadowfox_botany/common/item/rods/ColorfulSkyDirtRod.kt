package ninja.shadowfox.shadowfox_botany.common.item.rods

import java.awt.Color
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.StatCollector
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.baubles.ItemPriestEmblem
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler
import vazkii.botania.api.item.IAvatarTile
import vazkii.botania.api.item.IAvatarWieldable
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.Vector3

/**
 * Created by l0nekitsune on 10/19/15.
 */
class ColorfulSkyDirtRod(name: String = "colorfulSkyDirtRod") : ColorfulDirtRod(name), IAvatarWieldable {

    private val avatarOverlay = ResourceLocation("shadowfox_botany:textures/model/avatarDirtRainbow.png")

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {
        if(!world.isRemote) {
            if (player.isSneaking) {
                if (stack.itemDamage >= 15) stack.itemDamage = 0 else stack.itemDamage++
                world.playSoundAtEntity(player, "botania:ding", 0.1F, 1F)
                val dirtStack = ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, stack.itemDamage)
                dirtStack.setStackDisplayName(StatCollector.translateToLocal("misc.shadowfox_botany.color." + stack.itemDamage))
                ItemsRemainingRenderHandler.set(dirtStack, -2)
            }

            else if (ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, false)) {
                val color = Color(getColorFromItemStack(stack, 0))
                val r = color.getRed() / 255F
                val g = color.getGreen() / 255F
                val b = color.getBlue() / 255F

                val sif = (ItemPriestEmblem.getEmblem(1, player) != null)
                var basePlayerRange = 5.0
                if (player is EntityPlayerMP)
                    basePlayerRange = player.theItemInWorldManager.getBlockReachDistance()
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
                    val stackToPlace: ItemStack = ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, stack.itemDamage)
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
        if(world.isRemote)
            player.swingItem()

        return stack
    }

    override fun onAvatarUpdate(tile: IAvatarTile, stack: ItemStack) {
        var te = tile as TileEntity
        var world = te.getWorldObj()
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
        val r = color.getRed() / 255F
        val g = color.getGreen() / 255F
        val b = color.getBlue() / 255F

        if (tile.getCurrentMana() >= COST && block.isAir(world, x+xl, y, z+zl) && tile.getElapsedFunctionalTicks() % 50 == 0 && tile.isEnabled()) {
            world.setBlock(x+xl, y, z+zl, ShadowFoxBlocks.coloredDirtBlock, stack.itemDamage, 1 or 2)
            tile.recieveMana(-COST)
            for (i in 0..6)
                Botania.proxy.sparkleFX(world, x+xl + Math.random(), y + Math.random(), z+zl + Math.random(),
                        r, g, b, 1F, 5)
            world.playAuxSFX(2001, x+xl, y, z+zl, Block.getIdFromBlock(ShadowFoxBlocks.coloredDirtBlock) + (stack.itemDamage shl 12))

        }
    }

    override fun getOverlayResource(tile: IAvatarTile, stack: ItemStack): ResourceLocation {
        return avatarOverlay
    }
}
