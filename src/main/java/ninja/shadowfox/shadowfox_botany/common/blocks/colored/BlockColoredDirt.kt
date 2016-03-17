package ninja.shadowfox.shadowfox_botany.common.blocks.colored

import net.minecraft.block.Block
import net.minecraft.block.BlockFlowerPot
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.fml.common.registry.GameData
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.common.item.base.ItemBlockWithMetaNameAndColor
import ninja.shadowfox.shadowfox_botany.lib.PropertyStates
import ninja.shadowfox.shadowfox_botany.lib.getIridescentProperties
import ninja.shadowfox.shadowfox_botany.lib.getMetaNames
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.api.state.BotaniaStateProps
import java.awt.Color
import java.util.*


class BlockColoredDirt() : BlockMod(name, Material.ground), IGrowable, ILexiconable {

    companion object {
        val name = "iridescent_dirt"
    }

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        stepSound = Block.soundTypeGravel

        defaultState = blockState.baseState.withProperty(PropertyStates.COLOR, EnumDyeColor.WHITE)
    }

    public override fun createBlockState(): BlockState {
        return BlockState(this, BotaniaStateProps.COLOR)
    }

    override fun getMetaFromState(state: IBlockState?): Int {
        return state!!.getValue(BotaniaStateProps.COLOR).metadata
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        var meta = meta
        if (meta >= EnumDyeColor.values().size) {
            meta = 0
        }
        return defaultState.withProperty(PropertyStates.COLOR, EnumDyeColor.byMetadata(meta))

    }

    override fun customMeshDefinition(): ItemMeshDefinition? {
        return ItemMeshDefinition { stack ->
            val loc = GameData.getItemRegistry().getNameForObject(stack.item).toString()
            ModelResourceLocation(loc, "inventory")
        }
    }
//    override fun getSubBlocks(par1: Item, par2CreativeTabs: CreativeTabs?, par3List: MutableList<ItemStack>) {
//        for (i in 0..15)
//            par3List.add(ItemStack(par1, 1, i))
//    }

//    override val itemClass: Class<out ItemBlock> = ItemBlockWithMetaNameAndColor::class.java

    override fun damageDropped(state: IBlockState?): Int {
        return getMetaFromState(state)
    }

    override fun getRenderColor(state: IBlockState?): Int {
        return state!!.getValue(PropertyStates.COLOR).mapColor.colorValue
    }

    override fun colorMultiplier(par1iBlockAccess: IBlockAccess?, pos: BlockPos?, pass: Int): Int {
        return getRenderColor(par1iBlockAccess!!.getBlockState(pos))
    }

    override fun canSustainPlant(world: IBlockAccess?, pos: BlockPos?, direction: EnumFacing?, plantable: IPlantable?): Boolean {
        return direction == EnumFacing.UP
    }

    override fun getHarvestTool(state: IBlockState?): String =  "shovel"

    override fun canCreatureSpawn(world: IBlockAccess?, pos: BlockPos?, type: EntityLiving.SpawnPlacementType?): Boolean = true

    override fun isToolEffective(type: String?, state: IBlockState?): Boolean {
        return (type != null && type.equals("shovel"))
    }

    override fun canUseBonemeal(p0: World?, p1: Random?, p2: BlockPos?, p3: IBlockState?): Boolean = true

    override fun grow(p0: World?, p1: Random?, p2: BlockPos?, p3: IBlockState?) {
        //TODO grow grass
//        var l = 0
//
//        while (l < 128) {
//            var i1 = x
//            var j1 = y + 1
//            var k1 = z
//            var l1 = 0
//
//            while (true) {
//                if (l1 < l / 16) {
//                    i1 += random.nextInt(3) - 1
//                    j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2
//                    k1 += random.nextInt(3) - 1
//
//                    if ((world.getBlock(i1, j1 - 1, k1) == this || world.getBlock(i1, j1 - 1, k1) == ShadowFoxBlocks.coloredDirtBlock) && !world.getBlock(i1, j1, k1).isNormalCube) {
//                        ++l1
//                        continue
//                    }
//                } else if (world.getBlock(i1, j1, k1).isAir(world, i1, j1, k1)) {
//                    if (random.nextInt(8) != 0) {
//                        if (ShadowFoxBlocks.rainbowGrass.canBlockStay(world, i1, j1, k1)) {
//                            var meta = world.getBlockMetadata(i1, j1 - 1, k1)
//                            world.setBlock(i1, j1, k1, ShadowFoxBlocks.rainbowGrass, meta, 3)
//                        }
//                    } else {
//                        world.getBiomeGenForCoords(i1, k1).plantFlower(world, random, i1, j1, k1)
//                    }
//                }
//
//                ++l
//                break
//            }
//        }
    }

    override fun canGrow(p0: World?, p1: BlockPos?, p2: IBlockState?, p3: Boolean): Boolean = true

    override fun getEntry(p0: World?, p1: BlockPos?, p2: EntityPlayer?, p3: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.coloredDirt
        return null
    }
}
