package ninja.shadowfox.shadowfox_botany.common.item

import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.MovingObjectPosition
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import vazkii.botania.api.internal.IManaBurst
import vazkii.botania.api.mana.BurstProperties
import vazkii.botania.api.mana.ILens
import vazkii.botania.common.block.ModBlocks


class ItemLensFlashInvisible() : StandardItem("invisibleFlashLens"), ILens {
    override fun doParticles(p0: IManaBurst?, p1: ItemStack?): Boolean = true

    override fun updateBurst(p0: IManaBurst?, p1: ItemStack?) {}

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
                }
            }
        }
        return dead
    }

    override fun apply(p0: ItemStack?, p1: BurstProperties?) {}
    override fun getCompositeLens(p0: ItemStack?): ItemStack? { return null }
    override fun setCompositeLens(p0: ItemStack?, p1: ItemStack?): ItemStack? { return null }
    override fun canCombineLenses(p0: ItemStack?, p1: ItemStack?): Boolean = false
    override fun getLensColor(p0: ItemStack?): Int { return 16777215 }
}