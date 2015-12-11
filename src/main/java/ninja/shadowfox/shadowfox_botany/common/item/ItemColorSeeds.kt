package ninja.shadowfox.shadowfox_botany.common.item

import java.awt.Color
import java.util.ArrayList
import java.util.HashMap
import java.util.Random
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.World
import vazkii.botania.common.Botania
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase

class ItemColorSeeds() : ItemIridescent("irisSeeds") {
    private val blockSwappers = HashMap<Int, MutableList<BlockSwapper?>>()

    init {
        FMLCommonHandler.instance().bus().register(this)
    }

    override fun getSubItems(par1: Item, par2: CreativeTabs?, par3: MutableList<Any?>) {
        for(i in 0..(TYPES))
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
            if (par3World.getBlock(par4, par5+1, par6) == Blocks.tallgrass && par3World.getBlockMetadata(par4, par5+1, par6) == 1) {
                if (ItemIridescent.isRainbow(meta))
                    par3World.setBlock(par4, par5+1, par6, ShadowFoxBlocks.rainbowGrass, 0, 1 or 2)
                else
                    par3World.setBlock(par4, par5+1, par6, ShadowFoxBlocks.irisGrass, swapper.metaToSet, 1 or 2)
            }
            else if (par3World.getBlock(par4, par5+1, par6) == Blocks.double_plant && par3World.getBlockMetadata(par4, par5+1, par6) == 2) {
                if (ItemIridescent.isRainbow(meta)) {
                    par3World.setBlock(par4, par5+1, par6, ShadowFoxBlocks.rainbowTallGrass, 0, 2)
                    par3World.setBlock(par4, par5+2, par6, ShadowFoxBlocks.rainbowTallGrass, 8, 2)
                }
                else {
                    if (swapper.metaToSet < 8) {
                        par3World.setBlock(par4, par5+1, par6, ShadowFoxBlocks.irisTallGrass0, swapper.metaToSet, 2)
                        par3World.setBlock(par4, par5+2, par6, ShadowFoxBlocks.irisTallGrass0, 8, 2)
                    }
                    else {
                        par3World.setBlock(par4, par5+1, par6, ShadowFoxBlocks.irisTallGrass1, swapper.metaToSet-8, 2)
                        par3World.setBlock(par4, par5+2, par6, ShadowFoxBlocks.irisTallGrass1, 8, 2)
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
        if(event.phase == Phase.END) {
            var dim = event.world.provider.dimensionId
            if(blockSwappers.containsKey(dim)) {
                var swappers = blockSwappers[dim] as ArrayList<BlockSwapper?>
                var swappersSafe = ArrayList(swappers)
                for (s in swappersSafe)
                    s?.tick(swappers)
            }
        }
    }

    private fun addBlockSwapper(world: World, x: Int, y: Int, z: Int, meta: Int): BlockSwapper {
        var swapper = swapperFromMeta(world, x, y, z, meta)

        var dim = world.provider.dimensionId
        if(!blockSwappers.containsKey(dim))
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

        var startCoords: ChunkCoordinates
        var ticksExisted = 0

        init {
            this.world = world
            blockToSet = ItemIridescent.dirtFromMeta(meta)
            rainbow = ItemIridescent.isRainbow(meta)
            metaToSet = meta % 16
            var seed = coords.posX xor coords.posY xor coords.posZ
            rand = Random(seed.toLong())
            startCoords = coords
        }

        fun tick(list: MutableList<BlockSwapper?>) {
            ticksExisted++
            if (ticksExisted % 20 === 0) {
                var range = 3
                for(i in -range..range) {
                    for(j in -range..range) {
                        var x = startCoords.posX + i
                        var y = startCoords.posY
                        var z = startCoords.posZ + j
                        var block: Block = world.getBlock(x, y, z)
                        var meta: Int = world.getBlockMetadata(x, y, z)

                        if(block === blockToSet && meta === metaToSet) {
                            if(ticksExisted % 20 == 0) {
                                var validCoords = ArrayList<ChunkCoordinates>()
                                for(k in -1..1)
                                    for(l in -1..1) {
                                        var x1 = x + k
                                        var z1 = z + l
                                        var block1 = world.getBlock(x1, y, z1)
                                        var meta1 = world.getBlockMetadata(x1, y, z1)
                                        if((block1 == Blocks.dirt || block1 == Blocks.grass) && meta1 == 0) {
                                            validCoords.add(ChunkCoordinates(x1, y, z1))
                                        }
                                    }
                                if(!validCoords.isEmpty() && !world.isRemote) {
                                    var coords = validCoords[rand.nextInt(validCoords.size)]
                                    world.setBlock(coords.posX, coords.posY, coords.posZ, blockToSet, metaToSet, 1 or 2)
                                    if (world.getBlock(coords.posX, coords.posY+1, coords.posZ) == Blocks.tallgrass && world.getBlockMetadata(coords.posX, coords.posY+1, coords.posZ) == 1) {
                                        if (rainbow)
                                            world.setBlock(coords.posX, coords.posY+1, coords.posZ, ShadowFoxBlocks.rainbowGrass, 0, 1 or 2)
                                        else
                                            world.setBlock(coords.posX, coords.posY+1, coords.posZ, ShadowFoxBlocks.irisGrass, metaToSet, 1 or 2)
                                    }
                                    else if (world.getBlock(coords.posX, coords.posY+1, coords.posZ) == Blocks.double_plant && world.getBlockMetadata(coords.posX, coords.posY+1, coords.posZ) == 2) {
                                        if (rainbow) {
                                            world.setBlock(coords.posX, coords.posY+1, coords.posZ, ShadowFoxBlocks.rainbowTallGrass, 0, 2)
                                            world.setBlock(coords.posX, coords.posY+2, coords.posZ, ShadowFoxBlocks.rainbowTallGrass, 8, 2)
                                        }
                                        else {
                                            if (metaToSet < 8) {
                                                world.setBlock(coords.posX, coords.posY + 1, coords.posZ, ShadowFoxBlocks.irisTallGrass0, metaToSet, 2)
                                                world.setBlock(coords.posX, coords.posY + 2, coords.posZ, ShadowFoxBlocks.irisTallGrass0, 8, 2)
                                            }
                                            else {
                                                world.setBlock(coords.posX, coords.posY + 1, coords.posZ, ShadowFoxBlocks.irisTallGrass1, metaToSet-8, 2)
                                                world.setBlock(coords.posX, coords.posY + 2, coords.posZ, ShadowFoxBlocks.irisTallGrass1, 8, 2)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(ticksExisted >= 80)
                list.remove(this)
        }
    }
}
