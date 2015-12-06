package ninja.shadowfox.shadowfox_botany.common.blocks.colored

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.Random
import net.minecraft.util.*
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileTreeCrafter
import vazkii.botania.api.wand.IWandable
import kotlin.properties.Delegates

class BlockColoredDirt() : ShadowFoxBlockMod(Material.ground), IGrowable, ILexiconable, IWandable {

    private val name = "coloredDirt"
    private val TYPES = 16
    internal var icons: IIcon by Delegates.notNull()

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        stepSound = Block.soundTypeGravel
        setBlockName(this.name)
    }

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, remote: Boolean): Boolean {
        return true
    }
    override fun func_149852_a(world: World, random: Random, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {
        var l = 0

        while (l < 128) {
            var i1 = x
            var j1 = y + 1
            var k1 = z
            var l1 = 0

            while (true) {
                if (l1 < l / 16) {
                    i1 += random.nextInt(3) - 1
                    j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2
                    k1 += random.nextInt(3) - 1

                    if ((world.getBlock(i1, j1 - 1, k1) == this || world.getBlock(i1, j1 - 1, k1) == ShadowFoxBlocks.rainbowDirtBlock) && !world.getBlock(i1, j1, k1).isNormalCube) {
                        ++l1
                        continue
                    }
                }
                else if (world.getBlock(i1, j1, k1).isAir(world, i1, j1, k1)) {
                    if (random.nextInt(8) != 0) {
                        if (ShadowFoxBlocks.irisGrass.canBlockStay(world, i1, j1, k1)) {
                            var meta = world.getBlockMetadata(i1, j1-1, k1)
                            world.setBlock(i1, j1, k1, ShadowFoxBlocks.irisGrass, meta, 3)
                        }
                    }
                    else {
                        world.getBiomeGenForCoords(i1, k1).plantFlower(world, random, i1, j1, k1)
                    }
                }

                ++l
                break
            }
        }
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("shovel", true))
    }

    override fun getHarvestTool(metadata : Int): String {
        return "shovel"
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    override fun onUsedByWand(p0: EntityPlayer?, p1: ItemStack?, p2: World?, p3: Int, p4: Int, p5: Int, p6: Int): Boolean {
        if(p2 != null){
            if (TileTreeCrafter.canEnchanterExist(p2, p3, p4, p5, p6, p0)){
                p2.setBlock(p3, p4, p5, ShadowFoxBlocks.treeCrafterBlock, p6, 3)
                p2.playSoundEffect(p3.toDouble(), p4.toDouble(), p5.toDouble(), "botania:enchanterBlock", 0.5F, 0.6F)

                return true
            }
        }

        return false
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

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun damageDropped(par1: Int): Int {
        return par1
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side : Int, meta : Int) : IIcon {
        return icons
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }


    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = IconHelper.forBlock(par1IconRegister, this)
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.coloredDirt
    }
}
