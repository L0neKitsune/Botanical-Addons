package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import vazkii.botania.api.recipe.IFlowerComponent
import vazkii.botania.common.Botania
import java.awt.Color
import java.util.*

class ItemColorSeeds() : ItemIridescent("irisSeeds"), IFlowerComponent {
    private val blockSwappers = HashMap<Int, MutableList<BlockSwapper?>>()

    init {
        FMLCommonHandler.instance().bus().register(this)
    }

    override fun canFit(stack: ItemStack, inventory: IInventory): Boolean {
        return stack.itemDamage == 16
    }

    override fun getParticleColor(stack: ItemStack): Int {
        return ItemIridescent.rainbowColor()
    }

    override fun getSubItems(par1: Item, par2: CreativeTabs?, par3: MutableList<Any?>) {
        for (i in 0..(TYPES))
            par3.add(ItemStack(par1, 1, i))
    }

    override fun onItemUse(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3World: World, par4: Int, par5: Int, par6: Int, par7: Int, par8: Float, par9: Float, par10: Float): Boolean {
        val block = par3World.getBlock(par4, par5, par6)
        val bmeta = par3World.getBlockMetadata(par4, par5, par6)

        val color = Color(getColorFromItemStack(par1ItemStack, 0))
        val r = color.red / 255F
        val g = color.green / 255F
        val b = color.blue / 255F

        var x: Double
        var y: Double
        var z: Double
        val velMul = 0.025f

        if ((block === Blocks.dirt || block == Blocks.grass) && bmeta == 0) {
            var meta = par1ItemStack.itemDamage
            var swapper = addBlockSwapper(par3World, par4, par5, par6, meta)
            par3World.setBlock(par4, par5, par6, swapper.blockToSet, swapper.metaToSet, 1 or 2)
            if (par3World.getBlock(par4, par5 + 1, par6) == Blocks.tallgrass && par3World.getBlockMetadata(par4, par5 + 1, par6) == 1) {
                if (ItemIridescent.isRainbow(meta))
                    par3World.setBlock(par4, par5 + 1, par6, ShadowFoxBlocks.rainbowGrass, 0, 1 or 2)
                else
                    par3World.setBlock(par4, par5 + 1, par6, ShadowFoxBlocks.irisGrass, swapper.metaToSet, 1 or 2)
            } else if (par3World.getBlock(par4, par5 + 1, par6) == Blocks.double_plant && par3World.getBlockMetadata(par4, par5 + 1, par6) == 2) {
                if (ItemIridescent.isRainbow(meta)) {
                    par3World.setBlock(par4, par5 + 1, par6, ShadowFoxBlocks.rainbowTallGrass, 0, 2)
                    par3World.setBlock(par4, par5 + 2, par6, ShadowFoxBlocks.rainbowTallGrass, 8, 2)
                } else {
                    if (swapper.metaToSet < 8) {
                        par3World.setBlock(par4, par5 + 1, par6, ShadowFoxBlocks.irisTallGrass0, swapper.metaToSet, 2)
                        par3World.setBlock(par4, par5 + 2, par6, ShadowFoxBlocks.irisTallGrass0, 8, 2)
                    } else {
                        par3World.setBlock(par4, par5 + 1, par6, ShadowFoxBlocks.irisTallGrass1, swapper.metaToSet - 8, 2)
                        par3World.setBlock(par4, par5 + 2, par6, ShadowFoxBlocks.irisTallGrass1, 8, 2)
                    }
                }
            }
            for (i in 0..49) {
                x = (Math.random() - 0.5) * 3
                y = Math.random() - 0.5 + 1
                z = (Math.random() - 0.5) * 3
                Botania.proxy.wispFX(par3World, par4 + 0.5 + x, par5 + 0.5 + y, par6 + 0.5 + z, r, g, b, Math.random().toFloat() * 0.15f + 0.15f, (-x).toFloat() * velMul, (-y).toFloat() * velMul, (-z).toFloat() * velMul)
            }
            par1ItemStack.stackSize--
        }
        return true
    }

    @SubscribeEvent
    public fun onTickEnd(event: TickEvent.WorldTickEvent) {
        if (event.phase == Phase.END) {
            var dim = event.world.provider.dimensionId
            if (blockSwappers.containsKey(dim)) {
                var swappers = blockSwappers[dim] as ArrayList<BlockSwapper?>
                var swappersSafe = ArrayList(swappers)
                for (s in swappersSafe)
                    if (!(s?.tick() ?: false))
                        swappers.remove(s)
            }
        }
    }

    private fun addBlockSwapper(world: World, x: Int, y: Int, z: Int, meta: Int): BlockSwapper {
        var swapper = swapperFromMeta(world, x, y, z, meta)

        var dim = world.provider.dimensionId
        if (!blockSwappers.containsKey(dim))
            blockSwappers.put(dim, ArrayList<BlockSwapper?>())
        blockSwappers[dim]?.add(swapper)

        return swapper
    }

    private fun swapperFromMeta(world: World, x: Int, y: Int, z: Int, meta: Int): BlockSwapper {
        return BlockSwapper(world, ChunkCoordinates(x, y, z), meta)
    }

    private class BlockSwapper(world: World, coords: ChunkCoordinates, meta: Int) {

        var world: World
        var rand: Random
        var blockToSet: Block?
        var rainbow: Boolean
        var metaToSet: Int

        var grassBlock: Block
        var tallGrassMeta: Int
        var tallGrassBlock: Block

        var startCoords: ChunkCoordinates
        var ticksExisted = 0

        val RANGE = 3
        val TICK_RANGE = 1

        init {
            this.world = world
            blockToSet = ItemIridescent.dirtFromMeta(meta)
            rainbow = ItemIridescent.isRainbow(meta)
            metaToSet = meta % 16
            var seed = coords.posX xor coords.posY xor coords.posZ
            rand = Random(seed.toLong())
            startCoords = coords

            grassBlock = if (rainbow) ShadowFoxBlocks.rainbowGrass else ShadowFoxBlocks.irisGrass
            tallGrassMeta = metaToSet % 8
            tallGrassBlock = if (rainbow) ShadowFoxBlocks.rainbowTallGrass else (if (meta > 8) ShadowFoxBlocks.irisTallGrass1 else ShadowFoxBlocks.irisTallGrass0)
        }

        fun tick(): Boolean {
            ticksExisted++
            for (i in -RANGE..RANGE) {
                for (j in -RANGE..RANGE) {
                    var x = startCoords.posX + i
                    var y = startCoords.posY
                    var z = startCoords.posZ + j
                    var block: Block = world.getBlock(x, y, z)
                    var meta: Int = world.getBlockMetadata(x, y, z)

                    if (block === blockToSet && meta === metaToSet) {
                        // Only make changes every 20 ticks
                        if (ticksExisted % 20 != 0) continue

                        tickBlock(x, y, z)
                    }
                }
            }

            return ticksExisted < 80
        }

        fun tickBlock(x: Int, y: Int, z: Int) {
            val validCoords = ArrayList<ChunkCoordinates>()

            for (xOffset in -TICK_RANGE..TICK_RANGE) {
                for (zOffset in -TICK_RANGE..TICK_RANGE) {
                    if (xOffset == 0 && zOffset == 0) continue

                    if (isValidSwapPosition(x + xOffset, y, z + zOffset))
                        validCoords.add(ChunkCoordinates(x + xOffset, y, z + zOffset))
                }
            }
            if (!validCoords.isEmpty() && !world.isRemote) {
                val toSwap = validCoords[rand.nextInt(validCoords.size)]
                world.setBlock(toSwap.posX, toSwap.posY, toSwap.posZ, blockToSet, metaToSet, 3)

                val blockAbove = world.getBlock(toSwap.posX, toSwap.posY + 1, toSwap.posZ)
                val metaAbove = world.getBlockMetadata(toSwap.posX, toSwap.posY + 1, toSwap.posZ)

                if (blockAbove == Blocks.tallgrass && metaAbove == 1) {
                    world.setBlock(toSwap.posX, toSwap.posY + 1, toSwap.posZ, grassBlock, metaToSet, 1 or 2)
                } else if (blockAbove == Blocks.double_plant && metaAbove == 2) {
                    world.setBlock(toSwap.posX, toSwap.posY + 1, toSwap.posZ, tallGrassBlock, tallGrassMeta, 2)
                    world.setBlock(toSwap.posX, toSwap.posY + 2, toSwap.posZ, tallGrassBlock, 8, 2)
                }
            }
        }
        
        fun isValidSwapPosition(x: Int, y: Int, z: Int): Boolean {
            val block = world.getBlock(x, y, z)
            val meta = world.getBlockMetadata(x, y, z)
            val aboveBlock = world.getBlock(x, y + 1, z)

            return (block == Blocks.dirt || block == Blocks.grass)
                    && (meta == 0)
                    && (aboveBlock.getLightOpacity(world, x, y, z) <= 1);
        }
    }
}
