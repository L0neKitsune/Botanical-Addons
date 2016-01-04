package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry

class BlockKindling() : ShadowFoxBlockMod(Material.cloth), IFuelHandler, ILexiconable {

    init {
        this.setBlockName("kindling")
        this.setStepSound(Block.soundTypeCloth)

        GameRegistry.registerFuelHandler(this)
    }

    override fun onBlockActivated(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, meta: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (world != null && player != null) {
            if (player.inventory.getCurrentItem() == null) {
                if (world.getBlock(x,y+1,z).isAir(world,x,y+1,z)) {
                    world.setBlock(x,y+1,z, Blocks.fire)
                    world.playSoundEffect(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "fire.ignite", 1.0F, Math.random().toFloat() * 0.4F + 0.8F)
                    return true
                } else if (world.getBlock(x,y+1,z) == Blocks.fire) {
                    world.setBlock(x,y+1,z, Blocks.air)
                    world.playSoundEffect(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "random.fizz", 1.0F, Math.random().toFloat() * 0.4F + 0.8F)
                }
            }
        }
        return false
    }

    override fun getBurnTime(fuel: ItemStack): Int {
        return if (fuel.item == Item.getItemFromBlock(this)) 3200 else 0
    }

    override fun isFireSource(world: World?, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean {
        return side == ForgeDirection.UP
    }

    override fun getFireSpreadSpeed(world: IBlockAccess?, x: Int, y: Int, z: Int, side: ForgeDirection): Int {
        return if (side == ForgeDirection.UP) 500 else 0
    }

    override fun isFlammable(world: IBlockAccess?, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean {
        return side == ForgeDirection.UP
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry {
        return LexiconRegistry.kindling
    }
}
