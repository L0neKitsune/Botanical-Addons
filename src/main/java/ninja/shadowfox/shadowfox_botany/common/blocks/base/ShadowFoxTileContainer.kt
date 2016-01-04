package ninja.shadowfox.shadowfox_botany.common.blocks.base

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import vazkii.botania.client.core.helper.IconHelper


abstract class ShadowFoxTileContainer<T: TileEntity>(material: Material): BlockContainer(material) {
    var originalLight: Int = 0
    open val registerInCreative: Boolean = true

    init {
        if (registerInCreative) {
            setCreativeTab(ShadowFoxCreativeTab)
        }

    }

    override fun setBlockName(par1Str: String): Block {
        if (this.shouldRegisterInNameSet()) {
            GameRegistry.registerBlock(this, ItemBlockMod::class.java, par1Str)
        }

        return super.setBlockName(par1Str)
    }

    protected fun shouldRegisterInNameSet(): Boolean {
        return true
    }

    override fun setLightLevel(light: Float): Block {
        this.originalLight = (light * 15.0f).toInt()
        return super.setLightLevel(light)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        this.blockIcon = IconHelper.forBlock(par1IconRegister, this)
    }

    abstract override fun createNewTileEntity(var1: World, var2: Int): T
}
