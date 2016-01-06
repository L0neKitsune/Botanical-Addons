package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxRotatedPillar
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemUniqueSubtypedBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.lib.ALT_TYPES
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

public class BlockAltWood(val set: Int) : ShadowFoxRotatedPillar(Material.wood), ILexiconable {

    lateinit protected var iconsTop: Array<IIcon>
    lateinit protected var iconsSide: Array<IIcon>

    init {
        setBlockName("altWood$set")
        isBlockContainer = true
        blockHardness = 2F
    }

    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
    override fun isWood(world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean = true

    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, fortune: Int) {
        var b0: Byte = 4
        var i1: Int = b0 + 1

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (j1 in -b0..b0) for (k1 in -b0..b0)
                for (l1 in -b0..b0) {
                    var blockInWorld: Block = world.getBlock(x + j1, y + k1, z + l1)
                    if (blockInWorld.isLeaves(world, x + j1, y + k1, z + l1)) {
                        blockInWorld.beginLeavesDecay(world, x + j1, y + k1, z + l1)
                    }
                }
        }
        super.breakBlock(world, x, y, z, block, fortune)
    }

    override fun damageDropped(meta: Int): Int = meta and 3
    override fun quantityDropped(random: Random): Int = 1

    override fun register(par1Str: String) {
        GameRegistry.registerBlock(this, ItemUniqueSubtypedBlockMod::class.java, par1Str)
    }

    override fun getTopIcon(meta: Int): IIcon {
        return iconsTop[meta]
    }

    override fun getSideIcon(meta: Int): IIcon {
        return iconsSide[meta]
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null) {
            if (set == 0) {
                list.add(ItemStack(this, 1, 0))
                list.add(ItemStack(this, 1, 1))
                list.add(ItemStack(this, 1, 2))
                list.add(ItemStack(this, 1, 3))
            } else {
                list.add(ItemStack(this, 1, 0))
                list.add(ItemStack(this, 1, 1))
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        iconsTop = Array(if (set == 0 ) 4 else 2, { i -> IconHelper.forName(par1IconRegister, "altOak${ALT_TYPES[(set * 4) + i]}Top") })
        iconsSide = Array(if (set == 0 ) 4 else 2, { i -> IconHelper.forName(par1IconRegister, "altOak${ALT_TYPES[(set * 4) + i]}Side") })
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
