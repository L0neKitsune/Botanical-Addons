package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import java.util.*


class BlockColoredPlanks() : ShadowFoxBlockMod(Material.wood) {

    private val name = "irisPlanks"
    private val TYPES = 16
    protected var icons : Array<IIcon> = emptyArray()

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = Block.soundTypeWood

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

    override fun quantityDropped(p_149745_1_: Random): Int { return 1 }

    override fun getItemDropped(p_149650_1_: Int, p_149650_2_: Random, p_149650_3_: Int): Item
    {
        return Item.getItemFromBlock(this);
    }


    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon
    {
        return icons[meta]
    }

    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxMetaItemBlock::class.java, name)
    }


    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
            icons = Array(TYPES, {j -> IconHelper.forBlock(par1IconRegister, this, j)})

    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i));
            }
    }
}