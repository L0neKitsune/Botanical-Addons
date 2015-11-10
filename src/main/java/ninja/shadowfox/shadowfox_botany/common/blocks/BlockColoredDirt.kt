package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.eventhandler.SubscribeEvent
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
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.event.entity.player.BonemealEvent
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.Random
import net.minecraft.util.*
import kotlin.properties.Delegates

class BlockColoredDirt() : ShadowFoxBlockMod(Material.ground), IGrowable, ILexiconable {

    private val name = "coloredDirt"
    private val TYPES = 16
    internal var icons: IIcon by Delegates.notNull()

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        stepSound = Block.soundTypeGravel

        MinecraftForge.EVENT_BUS.register(this)
        setBlockName(this.name)
    }

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, p_149851_5_: Boolean): Boolean {
        return true
    }
    override fun func_149852_a(world: World, random: Random, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {
        var l = 0;

        var meta = 0;

        while (l < 128)
        {
            var i1 = x;
            var j1 = y + 1;
            var k1 = z;
            var l1 = 0;

            while (true)
            {
                if (l1 < l / 16) {
                    i1 += random.nextInt(3) - 1;
                    j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                    k1 += random.nextInt(3) - 1;

                    if (world.getBlock(i1, j1 - 1, k1) == this && !world.getBlock(i1, j1, k1).isNormalCube())
                    {
                        ++l1;
                        continue;
                    }
                }
                else if (world.getBlock(i1, j1, k1).isAir(world, i1, j1, k1)) {
                    if (random.nextInt(8) != 0) {
                        if (ShadowFoxBlocks.irisGrass.canBlockStay(world, i1, j1, k1)) {
                            meta = world.getBlockMetadata(i1, j1-1, k1)
                            world.setBlock(i1, j1, k1, ShadowFoxBlocks.irisGrass, meta, 3);
                        }
                    }
                    else
                    {
                        world.getBiomeGenForCoords(i1, k1).plantFlower(world, random, i1, j1, k1);
                    }
                }

                ++l;
                break;
            }
        }
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("shovel", true))
    }

    override fun getHarvestTool(metadata : Int): String {
        return "shovel";
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }



    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(p_149741_1_: Int): Int {
        if (p_149741_1_ >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[p_149741_1_];
        return Color(color[0], color[1], color[2]).rgb;
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(p_149720_1_: IBlockAccess?, p_149720_2_: Int, p_149720_3_: Int, p_149720_4_: Int): Int {
        val meta = p_149720_1_!!.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF;

        var color = EntitySheep.fleeceColorTable[meta];
        return Color(color[0], color[1], color[2]).rgb;
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

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = IconHelper.forBlock(par1IconRegister, this)
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }

    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.coloredDirt
    }
}

