package ninja.shadowfox.shadowfox_botany.common.blocks.base

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemIridescentBlockMod
import vazkii.botania.api.lexicon.ILexiconable
import java.util.*


abstract class ShadowFoxRotatedPillar(mat: Material) : ShadowFoxBlockMod(mat), ILexiconable {

    lateinit protected var iconTop: IIcon
    lateinit protected var iconSide: IIcon

    init {
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    open fun register(par1Str: String) {
        GameRegistry.registerBlock(this, ItemIridescentBlockMod::class.java, par1Str)
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(this)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        val k = meta and 12
        return if (k == 0 && (side == 1 || side == 0)) this.getTopIcon(meta and 3) else (if (k == 4 && (side == 5 || side == 4)) this.getTopIcon(meta and 3) else (if (k == 8 && (side == 2 || side == 3)) this.getTopIcon(meta and 3) else this.getSideIcon(meta and 3)))
    }

    override fun quantityDropped(random: Random): Int = 1
    override fun shouldRegisterInNameSet(): Boolean = false
    override fun damageDropped(meta: Int): Int = meta and 3
    override fun getRenderType(): Int = 31

    @SideOnly(Side.CLIENT) open fun getSideIcon(meta: Int): IIcon = iconSide
    @SideOnly(Side.CLIENT) open fun getTopIcon(meta: Int): IIcon = iconTop

    override fun createStackedBlock(meta: Int): ItemStack {
        return ItemStack(Item.getItemFromBlock(this), 1, meta and 3)
    }

    override fun onBlockPlaced(world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, meta: Int): Int {
        val j1 = meta and 3
        var b0: Int = 0

        when (side) {
            0, 1 -> b0 = 0
            2, 3 -> b0 = 8
            4, 5 -> b0 = 4
        }

        return j1 or b0
    }

    abstract override fun registerBlockIcons(par1IconRegister: IIconRegister)
    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta and 3)
    }
}
