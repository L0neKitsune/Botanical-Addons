package ninja.shadowfox.botania_addon.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import ninja.shadowfox.botania_addon.common.core.BotaniaModCreativeTab
import vazkii.botania.client.core.helper.IconHelper


open class BlockMod(par2Material: Material) : Block(par2Material) {
    val registerInCreative: Boolean = true

    init {
        if (registerInCreative) {
            setCreativeTab(BotaniaModCreativeTab)
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        blockIcon = IconHelper.forBlock(par1IconRegister, this)
    }
}
