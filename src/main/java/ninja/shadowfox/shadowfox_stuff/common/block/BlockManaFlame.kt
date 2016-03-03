package ninja.shadowfox.shadowfox_stuff.common.block

import com.google.common.collect.ImmutableList
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.common.Optional
import ninja.shadowfox.shadowfox_stuff.common.block.tile.TileRainbowManaFlame
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.block.tile.TileManaFlame
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.lexicon.LexiconData
import vazkii.botania.common.world.WorldTypeSkyblock
import java.util.*

/**
 * 1.8 mod
 * created on 2/25/16
 */
class BlockManaFlame() : BlockModContainer<TileEntity>(Material.cloth), ILexiconable {

    init {
        unlocalizedName = "rainbowFlame"
        val f = 0.25f
        this.setStepSound(Block.soundTypeCloth)
        this.setBlockBounds(f, f, f, 1.0f - f, 1.0f - f, 1.0f - f)
        this.setLightLevel(1.0f)
    }

    override fun registerInCreative(): Boolean {
        return false
    }

    @Optional.Method(modid = "easycoloredlights")
    override fun getLightValue(world: IBlockAccess, pos: BlockPos): Int {
        return (world.getTileEntity(pos) as TileManaFlame).lightColor
    }

    override fun getRenderType(): Int {
        return -1
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun isFullCube(): Boolean {
        return false
    }

    override fun isPassable(p_149655_1_: IBlockAccess?, pos: BlockPos?): Boolean {
        return true
    }

    override fun getCollisionBoundingBox(p_149668_1_: World?, pos: BlockPos, state: IBlockState?): AxisAlignedBB? {
        return null
    }

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, s: EnumFacing?, xs: Float, ys: Float, zs: Float): Boolean {
        if (WorldTypeSkyblock.isWorldSkyblock(world)) {
            val stack = player!!.currentEquippedItem
            if (stack != null && stack.item === Item.getItemFromBlock(Blocks.sapling) && !player.inventory.hasItem(ModItems.lexicon)) {
                if (!world!!.isRemote) {
                    --stack.stackSize
                }

                if (!player.inventory.addItemStackToInventory(ItemStack(ModItems.lexicon))) {
                    player.dropPlayerItemWithRandomChoice(ItemStack(ModItems.lexicon), false)
                }

                return true
            }
        }

        return false
    }

    override fun getDrops(world: IBlockAccess?, pos: BlockPos?, state: IBlockState, fortune: Int): List<ItemStack> {
        return ImmutableList.of<ItemStack>()
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return TileRainbowManaFlame()
    }

    override fun getEntry(world: World, pos: BlockPos, player: EntityPlayer, lexicon: ItemStack): LexiconEntry {
        return LexiconData.lenses
    }
}
