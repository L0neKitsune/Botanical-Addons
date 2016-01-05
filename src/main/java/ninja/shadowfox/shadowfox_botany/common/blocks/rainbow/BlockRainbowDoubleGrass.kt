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
import net.minecraft.stats.StatList
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemRainbowDoubleGrassMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.lib.Constants
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.util.*
import kotlin.properties.Delegates

public class BlockRainbowDoubleGrass() : BlockDoublePlant(), ILexiconable {

    val name = "rainbowDoubleGrass"
    internal var field_150128_a: IntArray? = null
    var topIcon: IIcon by Delegates.notNull()
    var bottomIcon: IIcon by Delegates.notNull()

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        if (FMLLaunchHandler.side().isClient())
            MinecraftForge.EVENT_BUS.register(this)
        setBlockNameSafe(name)
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 0) {
            var localTopIcon = InterpolatedIcon("shadowfox_botany:rainbowDoubleGrassTop")
            if (event.map.setTextureEntry("shadowfox_botany:rainbowDoubleGrassTop", localTopIcon)) {
                this.topIcon = localTopIcon
            }
            var localBottomIcon = InterpolatedIcon("shadowfox_botany:rainbowDoubleGrass")
            if (event.map.setTextureEntry("shadowfox_botany:rainbowDoubleGrass", localBottomIcon)) {
                this.bottomIcon = localBottomIcon
            }
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

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        list!!.add(ItemStack(item))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        return this.topIcon
    }

    @SideOnly(Side.CLIENT)
    override fun func_149888_a(top: Boolean, index: Int): IIcon {
        return if (top) this.topIcon else this.bottomIcon
    }

    fun dropBlock(world: World, x: Int, y: Int, z: Int, meta: Int, player: EntityPlayer): Boolean {
        if (isTop(meta)) {
            return false
        } else {
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1)
            this.dropBlockAsItem(world, x, y, z, ItemStack(ShadowFoxBlocks.rainbowGrass, 2, meta))
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
            ret.add(ItemStack(ShadowFoxBlocks.rainbowGrass, 2, meta))
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

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }
}
