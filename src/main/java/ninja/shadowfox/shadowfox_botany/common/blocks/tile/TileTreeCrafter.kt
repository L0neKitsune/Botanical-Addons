package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.entity.Entity
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ChunkCoordinates
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import vazkii.botania.api.lexicon.multiblock.Multiblock
import vazkii.botania.api.lexicon.multiblock.MultiblockSet
import vazkii.botania.api.lexicon.multiblock.component.ColorSwitchingComponent
import vazkii.botania.api.mana.spark.ISparkAttachable
import vazkii.botania.api.mana.spark.ISparkEntity
import vazkii.botania.common.block.ModBlocks


class TileTreeCrafter() : ShadowFoxTile(), ISparkAttachable {

    companion object {
        public val ITEMDISPLAY_LOCATIONS = arrayOf(intArrayOf( 4, 1, 0), intArrayOf( 3, 1,  3), intArrayOf( 3, 1, -3), intArrayOf( 0, 1,  4), intArrayOf( 0, 1, -4), intArrayOf(-3, 1,  3), intArrayOf(-3, 1, -3), intArrayOf(-4, 1,  0))
        public val COLOREDWOOD_LOCATIONS = arrayOf(intArrayOf( 2, 0, 2), intArrayOf( 2, 0,  1), intArrayOf( 2, 0, -1), intArrayOf( 2, 0, -2), intArrayOf( 1, 0,  2), intArrayOf( 1, 0, -2), intArrayOf(-1, 0,  2), intArrayOf(-1, 0, -2), intArrayOf(-2, 0, 2), intArrayOf(-2, 0, 1), intArrayOf(-2, 0, -1), intArrayOf(-2, 0, -2))
        public val OBSIDIAN_LOCATIONS =    arrayOf(intArrayOf( 3, 0, 2), intArrayOf( 3, 0,  1), intArrayOf( 3, 0,  0), intArrayOf( 3, 0, -1), intArrayOf( 3, 0, -2), intArrayOf( 2, 0,  3), intArrayOf( 2, 0,  0), intArrayOf( 2, 0, -3), intArrayOf( 1, 0, 3), intArrayOf( 1, 0, 0), intArrayOf( 1, 0, -3), intArrayOf( 0, 0,  3), intArrayOf( 0, 0, 2), intArrayOf( 0, 0, 1), intArrayOf( 0, 0, -1), intArrayOf( 0, 0, -2), intArrayOf( 0, 0, -3), intArrayOf(-1, 0, 3), intArrayOf(-1,  0, 0), intArrayOf(-1, 0, -3), intArrayOf(-2, 0, 3), intArrayOf(-2, 0, 0), intArrayOf(-2, 0, -3), intArrayOf(-3, 0, 2), intArrayOf(-3, 0, 1), intArrayOf(-3, 0, 0), intArrayOf(-3, 0, -1), intArrayOf(-3, 0, -2), intArrayOf( 1, 0, 1), intArrayOf( 1, 0, -1), intArrayOf(-1, 0,  1), intArrayOf(-1, 0, -1))

        public fun makeMultiblockSet(): MultiblockSet {
            val mb = Multiblock()

            for (i in ITEMDISPLAY_LOCATIONS.indices) {
                mb.addComponent(ITEMDISPLAY_LOCATIONS[i][0], ITEMDISPLAY_LOCATIONS[i][1] + 2, ITEMDISPLAY_LOCATIONS[i][2], ModBlocks.pylon, 0)
                mb.addComponent(ITEMDISPLAY_LOCATIONS[i][0], ITEMDISPLAY_LOCATIONS[i][1], ITEMDISPLAY_LOCATIONS[i][2], ShadowFoxBlocks.itemDisplay, 0)
            }

            for (i in OBSIDIAN_LOCATIONS.indices) {
                mb.addComponent(OBSIDIAN_LOCATIONS[i][0], OBSIDIAN_LOCATIONS[i][1], OBSIDIAN_LOCATIONS[i][2], Blocks.obsidian, 0)
            }

            for (i in COLOREDWOOD_LOCATIONS.indices) {
                mb.addComponent(ColorSwitchingComponent(ChunkCoordinates(COLOREDWOOD_LOCATIONS[i][0], COLOREDWOOD_LOCATIONS[i][1], COLOREDWOOD_LOCATIONS[i][2]), ShadowFoxBlocks.coloredPlanks))
            }

            mb.addComponent(ColorSwitchingComponent(ChunkCoordinates(0, 0, 0), ShadowFoxBlocks.coloredDirtBlock))
            return mb.makeSet()
        }
    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) { }
    override fun readCustomNBT(nbttagcompound: NBTTagCompound) { }

    override fun canAttachSpark(p0: ItemStack?): Boolean = true

    override fun areIncomingTranfersDone(): Boolean {
        return true
    }

    override fun getAvailableSpaceForMana(): Int {
        return Math.max(0, 0)
    }

    override fun isFull(): Boolean {
        return true
    }

    override fun recieveMana(mana: Int) { }

    override fun canRecieveManaFromBursts(): Boolean {
        return false
    }

    override fun getCurrentMana(): Int = 0

    override fun attachSpark(entity: ISparkEntity) { }

    override fun getAttachedSpark(): ISparkEntity? {
        val sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity::class.java, AxisAlignedBB.getBoundingBox(this.xCoord.toDouble(), (this.yCoord + 1).toDouble(), this.zCoord.toDouble(), (this.xCoord + 1).toDouble(), (this.yCoord + 2).toDouble(), (this.zCoord + 1).toDouble()))
        if (sparks.size == 1) {
            val e = sparks[0] as Entity
            return e as ISparkEntity
        } else {
            return null
        }
    }
}

