package ninja.shadowfox.shadowfox_botany.common.core.handler


/**
 * @author WireSegal
 * Created at 9:28 PM on 2/15/16.
 */
@Deprecated("1.7.10")
class BifrostFlowerDispenserBehavior
//: IBehaviorDispenseItem {
//
//    private val defaultBehavior = BehaviorDefaultDispenseItem()
//
//    override fun dispense(block: IBlockSource, stack: ItemStack): ItemStack? {
//        if (stack.itemDamage != 6) return defaultBehavior.dispense(block, stack)
//
//        val facing = ForgeDirection.getOrientation(BlockDispenser.func_149937_b(block.blockMetadata).ordinal)
//        val x = block.xInt + facing.offsetX
//        val y = block.yInt + facing.offsetY
//        val z = block.zInt + facing.offsetZ
//
//        if (block.world.getBlock(x, y, z) == ModBlocks.flower) {
//            block.world.setBlock(x, y, z, ShadowFoxBlocks.rainbowGrass, 1, 3)
//            block.world.playSoundEffect(x.toDouble(), y.toDouble(), z.toDouble(), "botania:enchanterEnchant", 1f, 1f)
//            stack.stackSize--
//            return stack
//        }
//        return stack
//    }
//}
