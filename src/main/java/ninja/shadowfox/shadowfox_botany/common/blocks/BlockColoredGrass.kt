package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.material.Material
import net.minecraft.block.Block
import net.minecraft.block.BlockTallGrass
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IShearable
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxGrassItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates

public class BlockColoredGrass() : BlockTallGrass(), ILexiconable {

    val TYPES: Int = 16
    internal var field_150128_a: IntArray? = null
    protected var icons: IIcon by Delegates.notNull()

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        setBlockName("irisGrass")

    }

    override fun func_149851_a(world:World, x:Int, y:Int, z:Int, remote: Boolean): Boolean {
        return true
    }

    override fun func_149853_b(world:World, random:Random, x:Int, y:Int, z:Int) {
        var l = world.getBlockMetadata(x, y, z)
        var b0 = l % 8

        if (ShadowFoxBlocks.irisTallGrass0.canPlaceBlockAt(world, x, y, z)) {
            if (l < 8) {
                world.setBlock(x, y, z, ShadowFoxBlocks.irisTallGrass0, b0, 2)
                world.setBlock(x, y + 1, z, ShadowFoxBlocks.irisTallGrass0, 8, 2)
            }
            else {
                world.setBlock(x, y, z, ShadowFoxBlocks.irisTallGrass1, b0, 2)
                world.setBlock(x, y + 1, z, ShadowFoxBlocks.irisTallGrass1, 8, 2)
            }
        }
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxGrassItemBlock::class.java, name)
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this, 1, world.getBlockMetadata(x, y, z)))
        return ret
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(access: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        val meta = access!!.getBlockMetadata(x, y, z)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {
        icons = IconHelper.forBlock(iconRegister, this)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side : Int, meta : Int) : IIcon {
        return icons
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }
}
