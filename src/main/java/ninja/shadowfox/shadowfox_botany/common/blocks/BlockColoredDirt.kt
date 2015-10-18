package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.IIcon
import net.minecraft.item.ItemStack
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockWithMetadataAndName
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper

/**
 * Created by l0nekitsune on 10/16/15.
 */
class BlockColoredDirt() : ShadowFoxBlockMod(Material.ground) {

    private val name = "coloredDirt"
    private val TYPES = 16
    internal var icons: Array<IIcon> = emptyArray()

    init {
        blockHardness = 0.5F
        setLightLevel(0f)
        blockResistance = 1.0f
        stepSound = Block.soundTypeGravel

        setBlockName(this.name)
    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun damageDropped(par1: Int): Int {
        return par1
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(side : Int, meta : Int) : IIcon {
        return icons[Math.min(TYPES - 1, meta)]
    }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ItemBlockWithMetadataAndName::class.java, name)
    }


    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icons = Array(TYPES, {i -> IconHelper.forBlock(par1IconRegister, this, i)})
    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }

    override fun canSustainPlant(world: IBlockAccess?, x: Int, y: Int, z: Int, direction: ForgeDirection?, plantable: IPlantable?): Boolean {
        return true
    }
}

