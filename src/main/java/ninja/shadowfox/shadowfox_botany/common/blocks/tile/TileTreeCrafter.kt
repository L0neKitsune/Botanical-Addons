package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.common.blocks.BlockItemDisplay
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredSapling
import vazkii.botania.api.lexicon.multiblock.Multiblock
import vazkii.botania.api.lexicon.multiblock.MultiblockSet
import vazkii.botania.api.lexicon.multiblock.component.ColorSwitchingComponent
import vazkii.botania.api.mana.spark.ISparkAttachable
import vazkii.botania.api.mana.spark.ISparkEntity
import vazkii.botania.common.Botania
import vazkii.botania.common.block.ModBlocks
import java.util.*


class TileTreeCrafter() : ShadowFoxTile(), ISparkAttachable {

    companion object {
        public val ITEMDISPLAY_LOCATIONS = arrayOf(
                intArrayOf(-3, 1, 3),
                intArrayOf(-4, 1, 0),
                intArrayOf(0, 1, 4),
                intArrayOf(-3, 1, -3),
                intArrayOf(0, 1, -4),
                intArrayOf(3, 1, -3),
                intArrayOf(4, 1, 0),
                intArrayOf(3, 1, 3)
        )


        public val COLOREDWOOD_LOCATIONS = arrayOf(intArrayOf(2, 0, 2), intArrayOf(2, 0, 1), intArrayOf(2, 0, -1), intArrayOf(2, 0, -2), intArrayOf(1, 0, 2), intArrayOf(1, 0, -2), intArrayOf(-1, 0, 2), intArrayOf(-1, 0, -2), intArrayOf(-2, 0, 2), intArrayOf(-2, 0, 1), intArrayOf(-2, 0, -1), intArrayOf(-2, 0, -2))
        public val OBSIDIAN_LOCATIONS = arrayOf(intArrayOf(3, 0, 2), intArrayOf(3, 0, 1), intArrayOf(3, 0, 0), intArrayOf(3, 0, -1), intArrayOf(3, 0, -2), intArrayOf(2, 0, 3), intArrayOf(2, 0, 0), intArrayOf(2, 0, -3), intArrayOf(1, 0, 3), intArrayOf(1, 0, 0), intArrayOf(1, 0, -3), intArrayOf(0, 0, 3),
                intArrayOf(0, 0, 2), intArrayOf(0, 0, 1), intArrayOf(0, 0, -1), intArrayOf(0, 0, -2), intArrayOf(0, 0, -3), intArrayOf(-1, 0, 3),
                intArrayOf(-1, 0, 0), intArrayOf(-1, 0, -3), intArrayOf(-2, 0, 3), intArrayOf(-2, 0, 0), intArrayOf(-2, 0, -3), intArrayOf(-3, 0, 2), intArrayOf(-3, 0, 1), intArrayOf(-3, 0, 0), intArrayOf(-3, 0, -1), intArrayOf(-3, 0, -2), intArrayOf(1, 0, 1), intArrayOf(1, 0, -1), intArrayOf(-1, 0, 1), intArrayOf(-1, 0, -1))

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

        fun canEnchanterExist(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer?): Boolean {

            for (i in ITEMDISPLAY_LOCATIONS.indices) {
                if (world.getBlock(ITEMDISPLAY_LOCATIONS[i][0] + x, ITEMDISPLAY_LOCATIONS[i][1] + 2 + y, ITEMDISPLAY_LOCATIONS[i][2] + z) !== ModBlocks.pylon) {
                    player?.addChatMessage((ChatComponentText("Pylons")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))

                    return false
                }
                if (world.getBlock(ITEMDISPLAY_LOCATIONS[i][0] + x, ITEMDISPLAY_LOCATIONS[i][1] + y, ITEMDISPLAY_LOCATIONS[i][2] + z) !== ShadowFoxBlocks.itemDisplay) {
                    player?.addChatMessage((ChatComponentText("Displays")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
                    return false
                }
            }

            for (i in OBSIDIAN_LOCATIONS.indices) {
                if (world.getBlock(OBSIDIAN_LOCATIONS[i][0] + x, OBSIDIAN_LOCATIONS[i][1] + y, OBSIDIAN_LOCATIONS[i][2] + z) !== Blocks.obsidian) {
                    player?.addChatMessage((ChatComponentText("Obsidian")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
                    return false
                }
            }

            for (i in COLOREDWOOD_LOCATIONS.indices) {
                if (world.getBlock(COLOREDWOOD_LOCATIONS[i][0] + x, COLOREDWOOD_LOCATIONS[i][1] + y, COLOREDWOOD_LOCATIONS[i][2] + z) !== ShadowFoxBlocks.coloredPlanks) {
                    player?.addChatMessage((ChatComponentText("Wood")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
                    return false
                }
            }

            if (world.getBlock(x, y, z) !== ShadowFoxBlocks.treeCrafterBlock && world.getBlock(x, y, z) !== ShadowFoxBlocks.coloredDirtBlock) {
                player?.addChatMessage((ChatComponentText("Dirt")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
                return false
            }

            return true
        }
    }

    var ticks: Int = 0

    override fun updateEntity() {
        if (!canEnchanterExist(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), null)) {
            val meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord)
            worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ShadowFoxBlocks.coloredDirtBlock, meta, 3)

            for (var11 in 0..49) {
                val var13 = Math.random().toFloat()
                val var16 = Math.random().toFloat()
                val var19 = Math.random().toFloat()
                Botania.proxy.wispFX(this.worldObj, this.xCoord.toDouble() + 0.5, this.yCoord.toDouble() + 0.5, this.zCoord.toDouble() + 0.5, var13, var16, var19, Math.random().toFloat() * 0.15f + 0.15f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f)
            }

            return //finishes execution just incase
        }

        //        for (i in ITEMDISPLAY_LOCATIONS.indices) {
        //            if(this.worldObj.rand.nextInt(40) == 0) {
        //                val loc1x = (ITEMDISPLAY_LOCATIONS[i][0] + xCoord).toDouble()
        //                val loc1y = (ITEMDISPLAY_LOCATIONS[i][1] + yCoord + 2).toDouble()
        //                val loc1z = (ITEMDISPLAY_LOCATIONS[i][2] + zCoord).toDouble()
        //
        //                var i2 = i + 1
        //                if (i2 == ITEMDISPLAY_LOCATIONS.size) i2 = 0
        //
        //                val loc2x = (ITEMDISPLAY_LOCATIONS[i2][0] + xCoord).toDouble()
        //                val loc2y = (ITEMDISPLAY_LOCATIONS[i2][1] + yCoord + 2).toDouble()
        //                val loc2z = (ITEMDISPLAY_LOCATIONS[i2][2] + zCoord).toDouble()
        //
        //                Botania.proxy.lightningFX(worldObj, Vector3(loc1x, loc1y, loc1z), Vector3(loc2x, loc2y, loc2z), 3f, 0xFFF, 0xFFF)
        //            }
        //        }


        if (ticks >= 50) {
            ticks = 0
            var inputs = getRecipeInputs()
            if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) is BlockColoredSapling) {
                Minecraft.getMinecraft().thePlayer.addChatMessage((ChatComponentText("Trying $inputs")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
                if (ShadowFoxAPI.treeRecipes[0].matches(inputs)) {
                    worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord)

                    val output: ItemStack = ShadowFoxAPI.treeRecipes[0].output.copy()
                    Minecraft.getMinecraft().thePlayer.addChatMessage((ChatComponentText("Outputting $output")).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))

                    if (!worldObj.isRemote) {
                        worldObj.spawnEntityInWorld(EntityItem(worldObj, xCoord.toDouble(), yCoord.toDouble() + 4, zCoord.toDouble(), output))
                    }

                    for (i in ITEMDISPLAY_LOCATIONS.indices) {
                        val block = worldObj.getTileEntity(ITEMDISPLAY_LOCATIONS[i][0] + xCoord, ITEMDISPLAY_LOCATIONS[i][1] + yCoord, ITEMDISPLAY_LOCATIONS[i][2] + zCoord)

                        if (block is TileItemDisplay) {
                            block.setInventorySlotContents(0, null)
                        }

                    }
                }
            }

        } else ticks++
    }

    fun getRecipeInputs(): ArrayList<ItemStack> {
        var items = ArrayList<ItemStack>()

        for (i in ITEMDISPLAY_LOCATIONS.indices) {
            val block = worldObj.getTileEntity(ITEMDISPLAY_LOCATIONS[i][0] + xCoord, ITEMDISPLAY_LOCATIONS[i][1] + yCoord, ITEMDISPLAY_LOCATIONS[i][2] + zCoord)

            if (block is TileItemDisplay) {
                val item = block.getStackInSlot(0)

                if (item != null)
                    items.add(item)
            }

        }

        return items
    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
    }

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

    override fun recieveMana(mana: Int) {
    }

    override fun canRecieveManaFromBursts(): Boolean {
        return false
    }

    override fun getCurrentMana(): Int = 0

    override fun attachSpark(entity: ISparkEntity) {
    }

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

