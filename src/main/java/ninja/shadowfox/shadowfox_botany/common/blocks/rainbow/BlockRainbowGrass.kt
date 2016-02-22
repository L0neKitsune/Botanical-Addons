package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockTallGrass
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemRainbowGrassMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

public class BlockRainbowGrass() : BlockTallGrass(), ILexiconable {

    var flowerIcon: IIcon? = null
    var glowingIcon: IIcon? = null
    var shroomIcon: IIcon? = null

    val GRASS = 0
    val FLOWER = 1
    val GLIMMER = 2
    val SHROOM = 3

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
        setBlockName("rainbowGrass")

    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int = 0xFFFFFF

    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int = 0xFFFFFF

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int = 0xFFFFFF

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, remote: Boolean): Boolean {
        val meta = world.getBlockMetadata(x, y, z)
        return meta == GRASS || meta == FLOWER
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {
        val meta = world.getBlockMetadata(x, y, z)
        if (meta != GRASS && meta != FLOWER) return
        if (ShadowFoxBlocks.rainbowTallGrass.canPlaceBlockAt(world, x, y, z)) {
            world.setBlock(x, y, z, ShadowFoxBlocks.rainbowTallGrass, meta, 2)
            world.setBlock(x, y + 1, z, ShadowFoxBlocks.rainbowTallGrass, 8, 2)
        }
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ItemRainbowGrassMod::class.java, name)
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun getDrops(world: World?, x: Int, y: Int, z: Int, meta: Int, fortune: Int): ArrayList<ItemStack>? {
        return when (world?.getBlockMetadata(x, y, z) ?: null) {
            GRASS -> super.getDrops(world, x, y, z, meta, fortune)
            else -> arrayListOf(ItemStack(this, 1, meta))
        }
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this))
        return ret
    }

    override fun isShearable(item: ItemStack?, world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean {
        return (item?.itemDamage ?: null) == GRASS
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>) {
        for (i in 0..3)
            list.add(ItemStack(item, 1, i))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 0) {
            this.blockIcon = InterpolatedIconHelper.forBlock(event.map, this)
            this.flowerIcon = InterpolatedIconHelper.forBlock(event.map, this, "Flower")
            this.glowingIcon = InterpolatedIconHelper.forBlock(event.map, this, "Glimmer")
            this.shroomIcon = InterpolatedIconHelper.forBlock(event.map, this, "Shroom")
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon? {
        return when (meta) {
            GRASS -> blockIcon
            FLOWER -> flowerIcon
            GLIMMER -> glowingIcon
            SHROOM -> shroomIcon
            else -> null
        }
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return when (p5?.itemDamage ?: null) {
            GRASS -> LexiconRegistry.pastoralSeeds
            FLOWER -> null // todo
            GLIMMER -> null // todo
            SHROOM -> null // todo
            else -> null
        }
    }
}
