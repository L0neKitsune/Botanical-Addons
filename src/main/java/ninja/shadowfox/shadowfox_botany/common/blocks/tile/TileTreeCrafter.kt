package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import cpw.mods.fml.common.FMLLog
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.colored.BlockColoredSapling
import ninja.shadowfox.shadowfox_botany.common.lexicon.MultiblockComponentRainbow

import org.apache.logging.log4j.Level
import vazkii.botania.api.internal.VanillaPacketDispatcher
import vazkii.botania.api.lexicon.multiblock.Multiblock
import vazkii.botania.api.lexicon.multiblock.MultiblockSet
import vazkii.botania.api.lexicon.multiblock.component.ColorSwitchingComponent
import vazkii.botania.api.mana.IManaPool
import vazkii.botania.api.mana.spark.ISparkAttachable
import vazkii.botania.api.mana.spark.ISparkEntity
import vazkii.botania.api.mana.spark.SparkHelper
import vazkii.botania.client.core.helper.RenderHelper
import vazkii.botania.common.Botania
import vazkii.botania.common.block.ModBlocks
import java.util.*


class TileTreeCrafter() : ShadowFoxTile(), ISparkAttachable {

    companion object {
        public val ITEMDISPLAY_LOCATIONS = arrayOf(Pos(-3, 1, 3), Pos(-4, 1, 0), Pos(0, 1, 4), Pos(-3, 1, -3), Pos(0, 1, -4), Pos(3, 1, -3), Pos(4, 1, 0), Pos(3, 1, 3))
        public val COLOREDWOOD_LOCATIONS = arrayOf(Pos(2, 0, 2), Pos(2, 0, 1), Pos(2, 0, -1), Pos(2, 0, -2), Pos(1, 0, 2), Pos(1, 0, -2), Pos(-1, 0, 2), Pos(-1, 0, -2), Pos(-2, 0, 2), Pos(-2, 0, 1), Pos(-2, 0, -1), Pos(-2, 0, -2))
        public val OBSIDIAN_LOCATIONS = arrayOf(Pos(3, 0, 2), Pos(3, 0, 1), Pos(3, 0, 0), Pos(3, 0, -1), Pos(3, 0, -2), Pos(2, 0, 3), Pos(2, 0, 0), Pos(2, 0, -3), Pos(1, 0, 3), Pos(1, 0, 0), Pos(1, 0, -3), Pos(0, 0, 3), Pos(0, 0, 2), Pos(0, 0, 1), Pos(0, 0, -1), Pos(0, 0, -2), Pos(0, 0, -3), Pos(-1, 0, 3), Pos(-1, 0, 0), Pos(-1, 0, -3), Pos(-2, 0, 3), Pos(-2, 0, 0), Pos(-2, 0, -3), Pos(-3, 0, 2), Pos(-3, 0, 1), Pos(-3, 0, 0), Pos(-3, 0, -1), Pos(-3, 0, -2), Pos(1, 0, 1), Pos(1, 0, -1), Pos(-1, 0, 1), Pos(-1, 0, -1))

        public fun makeMultiblockSet(): MultiblockSet {
            val mb = Multiblock()

            for (i in ITEMDISPLAY_LOCATIONS) {
                mb.addComponent(i.x, i.y + 1, i.z, ModBlocks.pylon, 0)
                mb.addComponent(i.x, i.y, i.z, ShadowFoxBlocks.itemDisplay, 0)
            }

            for (i in OBSIDIAN_LOCATIONS) {
                mb.addComponent(i.x, i.y, i.z, Blocks.obsidian, 0)
            }

            for (i in COLOREDWOOD_LOCATIONS) {
                mb.addComponent(MultiblockComponentRainbow(ChunkCoordinates(i.x, i.y, i.z), ShadowFoxBlocks.coloredPlanks, ShadowFoxBlocks.rainbowPlanks))
            }

            mb.addComponent(MultiblockComponentRainbow(ChunkCoordinates(0, 0, 0), ShadowFoxBlocks.coloredDirtBlock, ShadowFoxBlocks.rainbowDirtBlock))
            mb.addComponent(MultiblockComponentRainbow(ChunkCoordinates(0, 4, 0), ShadowFoxBlocks.coloredPlanks, ShadowFoxBlocks.rainbowPlanks))

            return mb.makeSet()
        }

        fun canEnchanterExist(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer?): Boolean {

            val y0 = y - 4

            for (i in ITEMDISPLAY_LOCATIONS) {
                if (!i.isBlock(world, ModBlocks.pylon, x0 = x, y0 = y0 + 1, z0 = z)) {
                    FMLLog.log(Level.INFO, "Pylon at $i")
                    return false
                }
                if (!i.isBlock(world, ShadowFoxBlocks.itemDisplay, x0 = x, y0 = y0, z0 = z)) {
                    FMLLog.log(Level.INFO, "Item Display at $i")
                    return false
                }
            }

            for (i in OBSIDIAN_LOCATIONS) {
                if (!i.isBlock(world, Blocks.obsidian, x0 = x, y0 = y0, z0 = z)) {
                    FMLLog.log(Level.INFO, "Obsidian at $i")
                    return false
                }
            }

            for (i in COLOREDWOOD_LOCATIONS) {
                if (!i.isBlock(world, ShadowFoxBlocks.coloredPlanks, x0 = x, y0 = y0, z0 = z) &&
                        !i.isBlock(world, ShadowFoxBlocks.rainbowPlanks, x0 = x, y0 = y0, z0 = z)) {
                    FMLLog.log(Level.INFO, "Colored Wood at $i")
                    return false
                }
            }

            if (world.getBlock(x, y0 + 4, z) !== ShadowFoxBlocks.treeCrafterBlock &&
                    world.getBlock(x, y0 + 4, z) !== ShadowFoxBlocks.coloredPlanks && 
                     world.getBlock(x, y0 + 4, z) !== ShadowFoxBlocks.rainbowPlanks) {
                FMLLog.log(Level.INFO, "Core Block")
                return false
            }

            if (world.getBlock(x, y0, z) !== ShadowFoxBlocks.coloredDirtBlock &&
                    world.getBlock(x, y0, z) !== ShadowFoxBlocks.rainbowDirtBlock) {
                FMLLog.log(Level.INFO, "Dirt Block")
                return false
            }

            return true
        }
    }

    class Pos(val x: Int, val y: Int, val z: Int) {
        fun getBlock(world: World, x0: Int = 0, y0: Int = 0, z0: Int = 0): Block = world.getBlock(x + x0, y + y0, z + z0)
        fun isBlock(world: World, block: Block, x0: Int = 0, y0: Int = 0, z0: Int = 0): Boolean = world.getBlock(x + x0, y + y0, z + z0) === block

        override fun toString(): String {
            return "X: $x Y: $y Z: $z"
        }
    }

    private var ticksAlive: Int = 0

    private var mana: Int = 0
    private var manaRequired: Int = 0
    private var stage: Int = 0
    private var stageTicks: Int = 0
    public var signal = 0

    override fun updateEntity() {
        if (!canEnchanterExist(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), null)) {
            val meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord)
            worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ShadowFoxBlocks.coloredPlanks, meta, 3)

            for (var11 in 0..49) {
                val var13 = Math.random().toFloat()
                val var16 = Math.random().toFloat()
                val var19 = Math.random().toFloat()
                Botania.proxy.wispFX(this.worldObj, this.xCoord.toDouble() + 0.5, this.yCoord.toDouble() + 0.5, this.zCoord.toDouble() + 0.5, var13, var16, var19, Math.random().toFloat() * 0.15f + 0.15f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f)
            }

            worldObj.playSoundEffect(this.xCoord.toDouble(), this.yCoord.toDouble(), this.zCoord.toDouble(), "botania:enchanterBlock", 0.5F, 10F)

            return //finishes execution just in case
        }

        itemDisplays {
            if (it.getStackInSlot(0) != null) {
                val s = 0.2f + Math.random().toFloat() * 0.1f
                val m = 0.03f + Math.random().toFloat() * 0.015f

                Botania.proxy.wispFX(this.worldObj, it.xCoord.toDouble() + .5, it.yCoord + 3.toDouble() + .5, it.zCoord.toDouble() + .5,
                        1.0f, 1.0f, 1.0f, s, -m)
            }
        }

        20 tickDelay { if (getRecipe() == null) stage = 0 }

        when (stage) {
            0 -> {
                signal = 0
                60 tickDelay {
                    forRecipe {
                        mana = 0
                        manaRequired = it.mana
                        advanceStage()
                    }
                }
            }
            1 -> {
                stageTicks++
                signal = 1
                10 tickDelay {
                    for (i in 0..359) {
                        val radian = (i.toDouble() * 3.141592653589793 / 180.0).toFloat()
                        val xp = xCoord.toDouble() + Math.cos(radian.toDouble()) * 3
                        val zp = zCoord.toDouble() + Math.sin(radian.toDouble()) * 3
                        Botania.proxy.wispFX(worldObj, xp + 0.5, yCoord - 3.0, zp + 0.5, 0.0f, 1.0f, 1.0f, 0.3f, -0.01f)
                    }
                }

                if (mana >= manaRequired) {

                    manaRequired = 0
                    advanceStage()
                } else {
                    if (attachedSpark != null) {
                        val sparks = SparkHelper.getSparksAround(worldObj, xCoord.toDouble() + 0.5, yCoord.toDouble() + 0.5, zCoord.toDouble() + 0.5)
                        if (sparks != null) for (spark in sparks)
                            if (spark != null && attachedSpark !== spark && spark.attachedTile != null && spark.attachedTile is IManaPool)
                                spark.registerTransfer(attachedSpark)
                    }
                }
            }
            2 -> {
                stageTicks++
                signal = 2
                20 tickDelay { FMLLog.log(Level.INFO, "Stage 2: $stageTicks ticks") }
                forRecipe { craftingFanciness(it) }
            }
            3 -> 40 tickDelay { advanceStage() }
            else -> {
                stage == 0
            }
        }

        ticksAlive++
    }

    fun getRecipe() : RecipeTreeCrafting? {
        if (worldObj.getBlock(xCoord, yCoord - 3, zCoord) is BlockColoredSapling) {
            for (recipe in ShadowFoxAPI.treeRecipes)
                if (recipe.matches(getRecipeInputs())) return recipe
        }

        return null
    }

    fun forRecipe(action: (RecipeTreeCrafting) -> Unit){
        val recipe = getRecipe()
        if (recipe != null){
            action.invoke(recipe)
        }
    }

    infix fun Int.tickDelay(lambda: () -> Any) {
        if (ticksAlive % this == 0) {
            lambda.invoke()
        }
    }

    fun itemDisplays(do_this: (display: TileItemDisplay) -> Unit) {
        for (i in ITEMDISPLAY_LOCATIONS) {
            val block = worldObj.getTileEntity(i.x + xCoord, i.y + yCoord - 4, i.z + zCoord)
            if (block is TileItemDisplay) {
                do_this.invoke(block)
            }
        }
    }

    fun renderHUD(mc: Minecraft, res: ScaledResolution) {
        val recipe = getRecipe()
        if (this.manaRequired > 0 && recipe != null) {
            val x = res.scaledWidth / 2 + 20
            val y = res.scaledHeight / 2 - 8
            RenderHelper.renderProgressPie(x, y, this.mana.toFloat() / this.manaRequired.toFloat(), ItemStack(recipe.output))
        }

    }

    fun getRecipeInputs(): ArrayList<ItemStack> {
        var items = ArrayList<ItemStack>()

        for (i in ITEMDISPLAY_LOCATIONS) {
            val block = worldObj.getTileEntity(i.x + xCoord, i.y + yCoord - 4, i.z + zCoord)

            if (block is TileItemDisplay) {
                val item = block.getStackInSlot(0)

                if (item != null) items.add(item)
            }

        }

        return items
    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
        nbttagcompound.setInteger("mana", mana)
        nbttagcompound.setInteger("manaRequired", manaRequired)
        nbttagcompound.setInteger("stage", stage)
        nbttagcompound.setInteger("stageTicks", stageTicks)
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
        mana = nbttagcompound.getInteger("mana")
        manaRequired = nbttagcompound.getInteger("manaRequired")
        stage = nbttagcompound.getInteger("stage")
        stageTicks = nbttagcompound.getInteger("stageTicks")
    }

    fun advanceStage() {
        ++stage
        this.stageTicks = 0
        this.sync()
    }

    fun sync() {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
    }

    fun craftingFanciness(recipe: RecipeTreeCrafting) {
        stage = 0

        worldObj.setBlockToAir(xCoord, yCoord - 3 , zCoord)
        worldObj.setBlock(xCoord, yCoord - 3, zCoord, recipe.output, recipe.meta, 3)

        this.worldObj.playSoundEffect(this.xCoord.toDouble(), this.yCoord.toDouble(), this.zCoord.toDouble(), "botania:enchanterEnchant", 1.0f, 1.0f)

        for (i in 0..24) {
            val red = Math.random().toFloat()
            val green = Math.random().toFloat()
            val blue = Math.random().toFloat()
            Botania.proxy.sparkleFX(this.worldObj, this.xCoord.toDouble() + 0.5 + Math.random() * 0.4 - 0.2, (this.yCoord + 1).toDouble(), this.zCoord.toDouble() + 0.5 + Math.random() * 0.4 - 0.2, red, green, blue, Math.random().toFloat(), 10)
        }

        itemDisplays {
            it.apply {
                setInventorySlotContents(0, null)
                invalidate()
            }
        }
    }

    /**
     * ISparkAttachable overrides
     */

    override fun canAttachSpark(p0: ItemStack?): Boolean = true

    override fun areIncomingTranfersDone(): Boolean = stage != 1
    override fun getAvailableSpaceForMana(): Int = Math.max(0, manaRequired - mana)
    override fun isFull(): Boolean = (mana >= manaRequired)
    override fun canRecieveManaFromBursts(): Boolean = manaRequired > 0
    override fun getCurrentMana(): Int = mana

    override fun recieveMana(mana: Int) {
        this.mana = Math.min(this.manaRequired, this.mana + mana)
    }

    override fun attachSpark(entity: ISparkEntity) {
        /*Pass*/
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

