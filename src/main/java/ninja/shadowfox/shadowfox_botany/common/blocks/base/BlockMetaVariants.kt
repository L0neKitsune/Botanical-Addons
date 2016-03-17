package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IVariantArrayHolder
import ninja.shadowfox.shadowfox_botany.common.blocks.base.Property
import ninja.shadowfox.shadowfox_botany.common.blocks.base.PropertyArray
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockMod
import ninja.shadowfox.shadowfox_botany.lib.asVariantArray

/**
 * kitsune
 * created on 3/10/16
 */
open class BlockMetaVariants(name: String, materialIn: Material, override val varientArray: Array<Property>) : BlockMod(name, materialIn, *varientArray.asVariantArray()), IVariantArrayHolder {
    val variantProp: PropertyArray?

    init {
        this.variantProp = temporaryVariantProp
        defaultState = blockState.baseState.withProperty(variantProp, varientArray[0])
    }

    public override fun createBlockState(): BlockState {
        return BlockState(this, temporaryVariantProp)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(variantProp as IProperty<Property>? ?: temporaryVariantProp as IProperty<Property>).meta
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        var meta = meta
        if (meta >= varientArray.size)
            meta = 0

        return defaultState.withProperty(variantProp, varientArray[meta])
    }

    override fun damageDropped(state: IBlockState): Int {
        return getMetaFromState(state)
    }

    override fun getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos, player: EntityPlayer): ItemStack {
        return ItemStack(this, 1, getMetaFromState(world.getBlockState(pos)))
    }

}
var temporaryVariantProp: PropertyArray? = null

