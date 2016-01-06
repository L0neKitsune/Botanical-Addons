package ninja.shadowfox.shadowfox_botany.common.blocks.base

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper


abstract class BlockContainerMod<T : TileEntity>(material: Material) : BlockContainer(material) {
    var originalLight: Int = 0
    open val registerInCreative: Boolean = true

    init {
        if (registerInCreative) {
            setCreativeTab(ShadowFoxCreativeTab)
        }
        if (FMLLaunchHandler.side().isClient && this.isInterpolated())
            MinecraftForge.EVENT_BUS.register(this)
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
        if (!isInterpolated())
            blockIcon = IconHelper.forBlock(par1IconRegister, this)
    }

    open fun isInterpolated(): Boolean = false

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    open fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 0 && this.isInterpolated())
            blockIcon = InterpolatedIconHelper.forBlock(event.map, this)
    }

    abstract override fun createNewTileEntity(var1: World, var2: Int): T
}
