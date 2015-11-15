package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
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
import net.minecraftforge.client.event.TextureStitchEvent
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxRainbowItemBlock
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.awt.Color
import java.util.Random
import net.minecraft.util.*
import kotlin.properties.Delegates

class BlockRainbowDirt() : ShadowFoxBlockMod(Material.ground), IGrowable, ILexiconable {

    private val name = "rainbowDirt"
    private val TYPES = 16

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        stepSound = Block.soundTypeGravel
        MinecraftForge.EVENT_BUS.register(this)
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

                    if ((world.getBlock(i1, j1 - 1, k1) == this || world.getBlock(i1, j1 - 1, k1) == ShadowFoxBlocks.coloredDirtBlock) && !world.getBlock(i1, j1, k1).isNormalCube) {
                        ++l1
                        continue
                    }
                }
                else if (world.getBlock(i1, j1, k1).isAir(world, i1, j1, k1)) {
                    if (random.nextInt(8) != 0) {
                        if (ShadowFoxBlocks.rainbowGrass.canBlockStay(world, i1, j1, k1)) {
                            var meta = world.getBlockMetadata(i1, j1-1, k1)
                            world.setBlock(i1, j1, k1, ShadowFoxBlocks.rainbowGrass, meta, 3)
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

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxRainbowItemBlock::class.java, name)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 0) {
            var icon = InterpolatedIcon("shadowfox_botany:rainbowDirt");
            if(event.map.setTextureEntry("shadowfox_botany:rainbowDirt", icon))
                this.blockIcon = icon
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.coloredDirt
    }
}
