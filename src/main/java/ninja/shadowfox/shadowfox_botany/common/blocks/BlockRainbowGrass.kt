package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockTallGrass
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.client.event.TextureStitchEvent
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxRainbowItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates

public class BlockRainbowGrass() : BlockTallGrass(), ILexiconable {

    init {
        setCreativeTab(ShadowFoxCreativeTab)
        setStepSound(Block.soundTypeGrass)
        MinecraftForge.EVENT_BUS.register(this);
        setBlockName("rainbowGrass")

    }

    override fun func_149851_a(world:World, x:Int, y:Int, z:Int, remote: Boolean): Boolean {
        return true
    }

    override fun func_149853_b(world:World, random:Random, x:Int, y:Int, z:Int) {
        if (ShadowFoxBlocks.rainbowTallGrass.canPlaceBlockAt(world, x, y, z)) {
            world.setBlock(x, y, z, ShadowFoxBlocks.rainbowTallGrass, 0, 2)
            world.setBlock(x, y + 1, z, ShadowFoxBlocks.rainbowTallGrass, 8, 2)
        }
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxRainbowItemBlock::class.java, name)
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

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        list!!.add(ItemStack(item))
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {}

    @SideOnly(Side.CLIENT)
    override fun getIcon(meta: Int, pass: Int): IIcon {
        return this.blockIcon
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 0) {
            var icon = InterpolatedIcon("shadowfox_botany:rainbowGrass");
            if(event.map.setTextureEntry("shadowfox_botany:rainbowGrass", icon))
                this.blockIcon = icon
        }
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.pastoralSeeds
    }
}
