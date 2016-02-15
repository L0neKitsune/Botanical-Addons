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
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemIridescentBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemIridescentGrassMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemRainbowGrassMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

public class BlockRainbowGrass() : BlockTallGrass(), ILexiconable {

    var flowerIcon: IIcon? = null

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
        return true
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {
        if (ShadowFoxBlocks.rainbowTallGrass.canPlaceBlockAt(world, x, y, z)) {
            world.setBlock(x, y, z, ShadowFoxBlocks.rainbowTallGrass, world.getBlockMetadata(x, y, z), 2)
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

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        val ret = ArrayList<ItemStack>()
        ret.add(ItemStack(this))
        return ret
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>) {
        list.add(ItemStack(item))
        list.add(ItemStack(item, 1, 1))
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
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon? {
        return if (meta == 0) blockIcon else flowerIcon
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }
}
