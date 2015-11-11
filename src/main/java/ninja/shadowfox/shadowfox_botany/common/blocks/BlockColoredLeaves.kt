package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockLeaves
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
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.*

public class BlockColoredLeaves() : BlockLeaves(), IShearable, ILexiconable {

    val TYPES: Int = 16
    internal var field_150128_a: IntArray? = null
    protected var icons: Array<IIcon> = emptyArray()

    init {
        setHardness(0.2f)
        setCreativeTab(ShadowFoxCreativeTab)
        setLightOpacity(1)
        setStepSound(Block.soundTypeGrass)
        setBlockName("irisLeaves")

    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(20) == 0) 1 else 0
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
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
    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        val meta = world!!.getBlockMetadata(x, y, z)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics)
        return icons[if (this.field_150121_P) 0 else 1]
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    private fun removeLeaves(world: World, x: Int, y: Int, z: Int) {
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0)
        world.setBlockToAir(x, y, z)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = Array(2, { i -> IconHelper.forBlock(par1IconRegister, this, if (i == 0) "" else "_opaque") })
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling)
    }

    override fun isLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun func_150125_e(): Array<String> {
        return arrayOf("ColoredLeaves")
    }

    override fun isShearable(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this, 1, world.getBlockMetadata(x, y, z)))
        return ret
    }

    override fun beginLeavesDecay(world: World?, x: Int, y: Int, z: Int) {

    }

    override fun updateTick(world: World?, x: Int, y: Int, z: Int, random: Random?) {
        if (!world!!.isRemote) {
            val b0 = 4
            val i1 = b0 + 1
            val b1 = 32
            val j1 = b1 * b1
            val k1 = b1 / 2

            if (this.field_150128_a == null) {
                this.field_150128_a = IntArray(b1.toInt() * b1.toInt() * b1.toInt())
            }

            var l1: Int

            if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
                var i2: Int
                var j2: Int

                l1 = -b0
                while (l1 <= b0) {
                    i2 = -b0
                    while (i2 <= b0) {
                        j2 = -b0
                        while (j2 <= b0) {
                            val block = world.getBlock(x + l1, y + i2, z + j2)

                            if (!block.canSustainLeaves(world, x + l1, y + i2, z + j2)) {
                                if (block.isLeaves(world, x + l1, y + i2, z + j2)) {
                                    field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2
                                } else {
                                    field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1
                                }
                            } else {
                                field_150128_a!![(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0
                            }
                            ++j2
                        }
                        ++i2
                    }
                    ++l1
                }

                l1 = 1
                while (l1 <= 4) {
                    i2 = -b0
                    while (i2 <= b0) {
                        j2 = -b0
                        while (j2 <= b0) {
                            for (k2 in -b0..b0) {
                                if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                    if (field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1
                                    }

                                    if (field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
                                        field_150128_a!![(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1
                                    }
                                }
                            }
                            ++j2
                        }
                        ++i2
                    }
                    ++l1
                }
            }

            l1 = field_150128_a!![k1 * j1 + k1 * b1 + k1]

            if (l1 >= 0) { }
            else removeLeaves(world, x, y, z)

        }
    }

    override fun getDamageValue(world: World, x: Int, y: Int, z: Int): Int {
       return world.getBlockMetadata(x, y, z)
    }

    override fun dropBlockAsItemWithChance(world: World, x: Int, y: Int, z: Int, metadata: Int,
                                           chance: Float, fortune: Int) {
        super.dropBlockAsItemWithChance(world, x, y, z, metadata, 1.0f, fortune)
    }

    override fun getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): ArrayList<ItemStack> {
        var ret: ArrayList<ItemStack> = ArrayList()

        var chance: Int = 20

        if (fortune > 0) {
            chance -= 2 shl fortune
            if (chance < 10) chance = 10
        }

        if (world.rand.nextInt(chance) == 0)
            ret.add(ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1, 0))

        chance = 200
        if (fortune > 0) {
            chance -= 10 shl fortune
            if (chance < 40) chance = 40
        }

        this.captureDrops(true)
        this.func_150124_c(world, x, y, z, metadata, chance); // Dammet mojang
        ret.addAll(this.captureDrops(false))
        return ret
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
