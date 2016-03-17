package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockModContainer

/**
 * kitsune
 * created on 3/7/16
 */
abstract class BlockFacing(name: String, materialIn: Material, vararg variants: String): BlockModContainer(name, materialIn, *variants) {
    companion object {
        val FACING: PropertyDirection = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL)
    }


    init {
        defaultState = makeDefaultState()
    }

    override fun createBlockState(): BlockState {
        return BlockState(this, *arrayOf(FACING))
    }
    override fun getMetaFromState(state: IBlockState) = state.getValue(FACING).index

    override fun getStateFromMeta(meta: Int): IBlockState {
        var enumfacing = EnumFacing.getFront(meta);

        if(enumfacing.axis == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return defaultState.withProperty(FACING, enumfacing);
    }

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.horizontalFacing.opposite), 2);
    }

    private fun setDefaultFacing(worldIn: World, pos: BlockPos, state: IBlockState) {
        if(!worldIn.isRemote) {
            var block = worldIn.getBlockState(pos.north()).block;
            var block1 = worldIn.getBlockState(pos.south()).block;
            var block2 = worldIn.getBlockState(pos.west()).block;
            var block3 = worldIn.getBlockState(pos.east()).block;
            var enumfacing = state.getValue(FACING);

            if(enumfacing == EnumFacing.NORTH && block.isFullBlock && !block1.isFullBlock)
                enumfacing = EnumFacing.SOUTH;
            else if(enumfacing == EnumFacing.SOUTH && block1.isFullBlock && !block.isFullBlock)
                enumfacing = EnumFacing.NORTH;
            else if(enumfacing == EnumFacing.WEST && block2.isFullBlock && !block3.isFullBlock)
                enumfacing = EnumFacing.EAST;
            else if(enumfacing == EnumFacing.EAST && block3.isFullBlock && !block2.isFullBlock)
                enumfacing = EnumFacing.WEST;

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    fun makeDefaultState(): IBlockState {
        return blockState.baseState.withProperty(FACING, EnumFacing.NORTH);
    }

    override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
        setDefaultFacing(worldIn, pos, state);
    }

}
