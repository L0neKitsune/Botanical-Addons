package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.MovingObjectPosition
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.oredict.RecipeSorter
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileInvisibleManaFlame
import vazkii.botania.api.internal.IManaBurst
import vazkii.botania.api.mana.BurstProperties
import vazkii.botania.api.mana.ILens
import vazkii.botania.common.Botania
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.crafting.recipe.LensDyeingRecipe
import java.awt.Color


class ItemLensFlashInvisible() : StandardItem("invisibleFlashLens"), ILens {

    init {
        GameRegistry.addRecipe(LensDyeingRecipe())
        RecipeSorter.register("shadowfox_botany:lensDying", LensDyeingRecipe::class.java, RecipeSorter.Category.SHAPELESS, "")
    }

    override fun doParticles(p0: IManaBurst?, p1: ItemStack?): Boolean = true

    override fun updateBurst(burst: IManaBurst, stack: ItemStack) {
        val entity = burst as EntityThrowable
        val storedColor = getStoredColor(stack)
        if (storedColor == 16 && entity.worldObj.isRemote) {
            burst.color = this.getLensColor(stack)
        }
    }

    override fun getLensColor(stack: ItemStack?): Int {
        val storedColor = getStoredColor(stack!!)
        if (storedColor == -1) {
            return 16777215
        } else if (storedColor == 16) {
            return Color.HSBtoRGB((Botania.proxy.worldElapsedTicks * 2L % 360L).toFloat() / 360.0f, 1.0f, 1.0f)
        } else {
            val color = EntitySheep.fleeceColorTable[storedColor]
            return (Color(color[0], color[1], color[2])).rgb
        }
    }

    fun getStoredColor(stack: ItemStack): Int {
        return ItemNBTHelper.getInt(stack, "color", -1)
    }

    fun setLensColor(stack: ItemStack, color: Int): ItemStack {
        ItemNBTHelper.setInt(stack, "color", color)
        return stack
    }

    override fun collideBurst(burst: IManaBurst?, pos: MovingObjectPosition?, isManaBlock: Boolean, dead: Boolean, p4: ItemStack?): Boolean {
        val entity: EntityThrowable? = burst as EntityThrowable

        if(pos != null && entity != null) {
            val coords = burst.burstSourceChunkCoordinates

            if ((coords.posX != pos.blockX || coords.posY != pos.blockY || coords.posZ != pos.blockZ) && !burst.isFake && !isManaBlock) {
                val dir = ForgeDirection.getOrientation(pos.sideHit)

                val x = pos.blockX + dir.offsetX
                val y = pos.blockY + dir.offsetY
                val z = pos.blockZ + dir.offsetZ

                val blockAt = entity.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ)
                val blockAt_ = entity.worldObj.getBlock(x, y, z)

                if (blockAt === ModBlocks.manaFlame || blockAt === ShadowFoxBlocks.invisibleFlame)
                    entity.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air)
                else if (blockAt_.isAir(entity.worldObj, x, y, z) || blockAt_.isReplaceable(entity.worldObj, x, y, z)) {
                    entity.worldObj.setBlock(x, y, z, ShadowFoxBlocks.invisibleFlame, 0, 0)

                    val tile = entity.worldObj.getTileEntity(x, y, z)
                    if (tile is TileInvisibleManaFlame) {
                        tile.color = burst.color
                    }
                }
            }
        }
        return dead
    }

    override fun apply(p0: ItemStack?, p1: BurstProperties?) {
        if (p0 != null && p1 != null) {
            val storedColor = getStoredColor(p0!!)
            if (storedColor != -1) {
                p1.color = this.getLensColor(p0)
            }
        }
    }

    override fun getCompositeLens(p0: ItemStack?): ItemStack? { return null }
    override fun setCompositeLens(p0: ItemStack?, p1: ItemStack?): ItemStack? { return null }
    override fun canCombineLenses(p0: ItemStack?, p1: ItemStack?): Boolean = false
}