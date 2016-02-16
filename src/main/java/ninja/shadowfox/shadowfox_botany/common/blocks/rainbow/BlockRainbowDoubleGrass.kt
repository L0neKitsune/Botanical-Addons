package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockDoublePlant
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
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IDoublePlant
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemRainbowDoubleGrassMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import ninja.shadowfox.shadowfox_botany.lib.Constants
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

public class BlockRainbowDoubleGrass() : BlockDoublePlant(), ILexiconable, IDoublePlant {

    val name = "rainbowDoubleGrass"
    var topIcon: IIcon? = null
    var bottomIcon: IIcon? = null

    var topFlowerIcon: IIcon? = null
    var bottomFlowerIcon: IIcon? = null

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
        setBlockNameSafe(name)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 0) {
            this.topIcon = InterpolatedIconHelper.forBlock(event.map, this, "Top")!!
            this.bottomIcon = InterpolatedIconHelper.forBlock(event.map, this)!!
            this.topFlowerIcon = InterpolatedIconHelper.forBlock(event.map, this, "FlowerTop")!!
            this.bottomFlowerIcon = InterpolatedIconHelper.forBlock(event.map, this, "Flower")!!
        }
    }

    override fun func_149851_a(world: World, x: Int, y: Int, z: Int, isRemote: Boolean): Boolean {
        return false
    }

    override fun func_149853_b(world: World, random: Random, x: Int, y: Int, z: Int) {
    }

    fun isTop(meta: Int): Boolean {
        return (meta and 8) != 0
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ItemRainbowDoubleGrassMod::class.java, name)
    }

    fun setBlockNameSafe(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun setBlockName(par1Str: String): Block? {
        return null
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>) {
        list.add(ItemStack(item))
        list.add(ItemStack(item, 1, 1))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon? {
        return getTopIcon(meta)
    }

    @SideOnly(Side.CLIENT)
    override fun func_149888_a(top: Boolean, index: Int): IIcon? {
        return if (top) this.getTopIcon(index) else this.getBottomIcon(index)
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item? {
        return null
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int, fortune: Int): ArrayList<ItemStack> {
        var ret = ArrayList<ItemStack>()
        var meta = world.getBlockMetadata(x, y, z)
        if (isTop(meta)) {
            if (y > 0 && world.getBlock(x, y - 1, z) == this) {
                var downMeta = world.getBlockMetadata(x, y - 1, z)
                if (downMeta == 1)
                    ret.add(ItemStack(this, 1, 1))
                else
                    ret.add(ItemStack(ShadowFoxBlocks.irisGrass, 2, downMeta))
            }
        } else {
            if (meta == 1)
                ret.add(ItemStack(this, 1, 1))
            else
                ret.add(ItemStack(ShadowFoxBlocks.irisGrass, 2, meta))
        }
        return ret
    }

    override fun getRenderType(): Int {
        return Constants.doubleFlowerRenderID
    }

    override fun isShearable(item: ItemStack, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean {
        return true
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }

    override fun getBottomIcon(lowerMeta: Int): IIcon? {
        return if (lowerMeta == 0) bottomIcon else bottomFlowerIcon
    }

    override fun getTopIcon(lowerMeta: Int): IIcon? {
        return if (lowerMeta == 0) topIcon else topFlowerIcon
    }
}
