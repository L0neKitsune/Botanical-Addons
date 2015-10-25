package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod


open class ShadowFoxBlockMod(par2Material: Material) : Block(par2Material) {
    var originalLight: Int = 0

    val registerInCreative: Boolean = true

    init {
        if (registerInCreative) {
            setCreativeTab(ShadowFoxCreativeTab)
        }
    }

    override fun setBlockName(par1Str: String): Block {
        if (shouldRegisterInNameSet())
            GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, par1Str)
        return super.setBlockName(par1Str)
    }

    protected open fun shouldRegisterInNameSet(): Boolean {
        return true
    }

    override fun setLightLevel(p_149715_1_: Float): Block {
        originalLight = (p_149715_1_ * 15).toInt()
        return super.setLightLevel(p_149715_1_)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        blockIcon = IconHelper.forBlock(par1IconRegister, this)
    }

    internal fun registerInCreative(): Boolean {
        return true
    }
}
