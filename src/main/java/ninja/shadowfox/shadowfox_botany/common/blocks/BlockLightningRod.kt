package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxRotatedPillar
import ninja.shadowfox.shadowfox_botany.common.entity.EntityLightningRod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.lexicon.LexiconEntry

public class BlockLightningRod() : ShadowFoxRotatedPillar(Material.wood){

    init {
        setBlockName("lightningRodBlock")
    }

    override fun hasTileEntity(metadata: Int): Boolean = true
    override fun createTileEntity(world: World?, metadata: Int): TileEntity? = EntityLightningRod()

    override  fun register(par1Str: String) {
        GameRegistry.registerBlock(this, ShadowFoxItemBlockMod::class.java, par1Str)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        iconSide = IconHelper.forBlock(par1IconRegister, this)
        iconTop = IconHelper.forBlock(par1IconRegister, this)
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return null
    }
}
