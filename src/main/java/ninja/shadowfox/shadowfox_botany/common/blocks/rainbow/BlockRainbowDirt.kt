package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.client.event.TextureStitchEvent
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.wand.IWandable
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.util.Random
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileTreeCrafter
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxColoredItemBlock

class BlockRainbowDirt() : ShadowFoxBlockMod(Material.ground), IGrowable, ILexiconable, IWandable {

    private val name = "rainbowDirt"

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        stepSound = Block.soundTypeGravel
        if (FMLLaunchHandler.side().isClient())
            MinecraftForge.EVENT_BUS.register(this)
        setBlockName(this.name)
    }

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, remote: Boolean): Boolean {
        return true
    }
    override fun func_149852_a(world: World, random: Random, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun onUsedByWand(p0: EntityPlayer?, p1: ItemStack?, p2: World?, p3: Int, p4: Int, p5: Int, p6: Int): Boolean {
        if(p2 != null){
            if (TileTreeCrafter.canEnchanterExist(p2, p3, p4, p5, p6, p0)){
                p2.setBlock(p3, p4, p5, ShadowFoxBlocks.treeCrafterBlock, p6, 3)
                p2.playSoundEffect(p3, p4, p5, "botania:enchanterBlock", 0.5F, 0.6F)

                return true
            }
        }

        return false
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
        GameRegistry.registerBlock(this, ShadowFoxColoredItemBlock::class.java, name)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 0) {
            var icon = InterpolatedIcon("shadowfox_botany:rainbowDirt")
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
