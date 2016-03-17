package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak

//import cpw.mods.fml.common.registry.GameRegistry
//import net.minecraft.block.Block
//import net.minecraft.block.material.Material
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.item.ItemStack
//import net.minecraft.world.IBlockAccess
//import net.minecraft.world.World
//import net.minecraftforge.common.util.ForgeDirection
//import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxRotatedPillar
//import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
//import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
//import vazkii.botania.api.lexicon.ILexiconable
//import vazkii.botania.api.lexicon.LexiconEntry
//import java.util.*

@Deprecated("1.7.10")
class BlockNetherWood()
//: ShadowFoxRotatedPillar(Material.wood), ILexiconable {
//
//    init {
//        setBlockName("netherWood")
//        blockHardness = 2f
//    }
//
//    override fun isInterpolated(): Boolean = true
//
//    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
//    override fun isWood(world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean = true
//
//    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, fortune: Int) {
//        var b0: Byte = 4
//        var i1: Int = b0 + 1
//
//        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
//            for (j1 in -b0..b0) for (k1 in -b0..b0)
//                for (l1 in -b0..b0) {
//                    var blockInWorld: Block = world.getBlock(x + j1, y + k1, z + l1)
//                    if (blockInWorld.isLeaves(world, x + j1, y + k1, z + l1)) {
//                        blockInWorld.beginLeavesDecay(world, x + j1, y + k1, z + l1)
//                    }
//                }
//        }
//        super.breakBlock(world, x, y, z, block, fortune)
//    }
//
//    override fun isFireSource(world: World?, x: Int, y: Int, z: Int, side: ForgeDirection?): Boolean = true
//    override fun isFlammable(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Boolean = false
//    override fun getFireSpreadSpeed(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Int = 0
//
//    override fun damageDropped(meta: Int): Int = 0
//    override fun quantityDropped(random: Random): Int = 1
//
//    override fun register(par1Str: String) {
//        GameRegistry.registerBlock(this, ItemBlockMod::class.java, par1Str)
//    }
//
//    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
//        return LexiconRegistry.netherSapling
//    }
//}
