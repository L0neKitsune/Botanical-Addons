package ninja.shadowfox.shadowfox_botany.common.item.rods

import java.awt.Color
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import vazkii.botania.client.core.handler.ItemsRemainingRenderHandler
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania
import vazkii.botania.common.core.helper.Vector3

/**
 * Created by l0nekitsune on 10/19/15.
 */
class ColorfulSkyDirtRod(name: String = "colorfulSkyDirtRod") : ColorfulDirtRod(name) {

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {
        if(!world.isRemote) {
            if (player.isSneaking) {
                if (stack.itemDamage >= 15) stack.itemDamage = 0 else stack.itemDamage++
                world.playSoundAtEntity(player, "botania:ding", 0.1F, 1F);
                val dirtStack = ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, stack.itemDamage)
                dirtStack.setStackDisplayName(StatCollector.translateToLocal("misc.shadowfox_botany.color." + stack.itemDamage))
                ItemsRemainingRenderHandler.set(dirtStack, -2);
            }

            else if (ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, false)) {
                val color = Color(getColorFromItemStack(stack, 0));
                val r = color.getRed() / 255F;
                val g = color.getGreen() / 255F;
                val b = color.getBlue() / 255F;

                val playerVec = Vector3.fromEntityCenter(player);
                val lookVec = Vector3(player.lookVec).multiply(3.toDouble());
                val placeVec = playerVec.copy().add(lookVec);

                val x = MathHelper.floor_double(placeVec.x);
                val y = MathHelper.floor_double(placeVec.y) + 1;
                val z = MathHelper.floor_double(placeVec.z);

                val entities = world.getEntitiesWithinAABB(EntityLivingBase::class.java,
                        AxisAlignedBB.getBoundingBox(x.toDouble(), y.toDouble(), z.toDouble(), (x + 1).toDouble(),
                                (y + 1).toDouble(), (z + 1).toDouble())).size();

                if (entities == 0) {
                    val stackToPlace: ItemStack = ItemStack(ShadowFoxBlocks.coloredDirtBlock, 1, stack.itemDamage)
                    stackToPlace.tryPlaceItemIntoWorld(player, world, x, y, z, 0, 0F, 0F, 0F);

                    if (stackToPlace.stackSize == 0) {
                        ManaItemHandler.requestManaExactForTool(stack, player, COST * 2, true);
                        for (i in 0..6)
                            Botania.proxy.sparkleFX(world, x + Math.random(), y + Math.random(), z + Math.random(),
                                    r, g, b, 1F, 5);
                    }
                }
            }
        }
        if(world.isRemote)
            player.swingItem();

        return stack;
    }
}