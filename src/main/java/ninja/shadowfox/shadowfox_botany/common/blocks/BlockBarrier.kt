package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.init.Blocks
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockBarrier: ShadowFoxBlockMod(Material.cake) {

    override val registerInCreative = false

    init {
        this.setBlockUnbreakable()
        this.setResistance(6000001.0F)
        this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F)
        this.disableStats()
        this.setLightOpacity(0)
        this.setBlockName("barrier")
    }

    override fun getIcon(side: Int, meta: Int): IIcon {
        return Blocks.bedrock.getIcon(side, meta)
    }

    override fun getRenderType(): Int = -1

    override fun isOpaqueCube(): Boolean = false

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    @SideOnly(Side.CLIENT)
    override fun getAmbientOcclusionLightValue(): Float = 1.0F

    override fun dropBlockAsItemWithChance(worldIn: World, x: Int, y: Int, z: Int, meta: Int, chance: Float, fortune: Int) {}
}