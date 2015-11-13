package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockDoublePlant
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.stats.StatList
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxDoubleGrassItemBlock0
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxDoubleGrassItemBlock1
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.lib.Constants
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.EntityRenderer
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates

public class BlockColoredDoubleGrass(var colorSet: Int) : BlockDoublePlant(), ILexiconable {

    val name = "irisDoubleGrass$colorSet"
    val TYPES: Int = 8
    internal var field_150128_a: IntArray? = null
    var topIcon: IIcon by Delegates.notNull()
    var bottomIcon: IIcon by Delegates.notNull()

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        setBlockNameSafe(name)
    }

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, isRemote: Boolean): Boolean {
        return false
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {}

    fun isTop(meta: Int): Boolean {
        return (meta and 8) != 0
    }

    internal fun register(name: String) {
        when (colorSet) {
            1 -> GameRegistry.registerBlock(this, ShadowFoxDoubleGrassItemBlock1::class.java, name)
            else -> GameRegistry.registerBlock(this, ShadowFoxDoubleGrassItemBlock0::class.java, name)
        }
    }

    fun setBlockNameSafe(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun setBlockName(par1Str: String): Block? {return null}

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (meta >= TYPES)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta+TYPES*colorSet]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(access: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        val meta = access!!.getBlockMetadata(x, y, z)
        if (meta >= TYPES)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta+TYPES*colorSet]
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
        topIcon = IconHelper.forName(iconRegister, "irisDoubleGrassTop")
        bottomIcon = IconHelper.forName(iconRegister, "irisDoubleGrass")
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side : Int, meta : Int) : IIcon {
        return this.topIcon
    }

    @SideOnly(Side.CLIENT)
    override fun func_149888_a(top: Boolean, index: Int): IIcon {
        return if (top) this.topIcon else this.bottomIcon
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }

    fun func_149886_b(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer): Boolean {
        if (isTop(meta)) {
            return false
        }
        else {
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1)
            var b0 = TYPES * colorSet + meta
            this.dropBlockAsItem(world, x, y, z, ItemStack(ShadowFoxBlocks.irisGrass, 2, b0))
            return true
        }
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item? {
        return null
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        var ret = ArrayList<ItemStack>()
        var meta = world.getBlockMetadata(x, y, z)
        if (!isTop(meta)) {
            var b0 = TYPES * colorSet + meta
            ret.add(ItemStack(ShadowFoxBlocks.irisGrass, 2, b0))
        }
        return ret
    }

    override fun getRenderType(): Int {
        return Constants.doubleFlowerRenderID
    }

    override fun isShearable(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean {
        var meta = world.getBlockMetadata(x, y, z)
        return !isTop(meta)
    }

    public class ColoredDoublePlantRenderer: ISimpleBlockRenderingHandler {
        public override fun getRenderId(): Int {
            return Constants.doubleFlowerRenderID
        }

        public override fun shouldRender3DInInventory(modelId: Int): Boolean {return false}

        public override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks){}

        public override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
            val tessellator = Tessellator.instance
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
            var l: Int
            if ((world.getBlockMetadata(x, y, z) and 8) != 0) {
                l = block.colorMultiplier(world, x, y-1, z)
            } else {
                l = block.colorMultiplier(world, x, y, z)
            }
            var f = (l shr 16 and 255).toFloat() / 255.0f
            var f1 = (l shr 8 and 255).toFloat() / 255.0f
            var f2 = (l and 255).toFloat() / 255.0f
            if (EntityRenderer.anaglyphEnable) {
                var f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f
                var f4 = (f * 30.0f + f1 * 70.0f) / 100.0f
                var f5 = (f * 30.0f + f2 * 70.0f) / 100.0f
                f = f3
                f1 = f4
                f2 = f5
            }
            tessellator.setColorOpaque_F(f, f1, f2)
            var j1 = (x * 3129871).toLong() xor z.toLong() * 116129781L
            j1 = j1 * j1 * 42317861L + j1 * 11L
            var d19 = x.toDouble()
            var d0 = y.toDouble()
            var d1 = z.toDouble()
            d19 += (((j1 shr 16 and 15L).toFloat() / 15.0f).toDouble() - 0.5) * 0.3
            d1 += (((j1 shr 24 and 15L).toFloat() / 15.0f).toDouble() - 0.5) * 0.3
            var i1 = world.getBlockMetadata(x, y, z)
            var flag = (i1 and 8) != 0
            if (flag && world.getBlock(x, y - 1, z) !== block && world.getBlock(x, y - 1, z) !== block) {
                return false
            }
            val iicon = if ((i1 and 8) != 0) (block as BlockColoredDoubleGrass).topIcon else (block as BlockColoredDoubleGrass).bottomIcon
            renderer.drawCrossedSquares(iicon, d19, d0, d1, 1.0f)
            return true
        }
    }
}
