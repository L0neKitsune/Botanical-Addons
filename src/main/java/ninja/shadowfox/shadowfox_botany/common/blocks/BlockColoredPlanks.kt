package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxMetaItemBlock
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.awt.Color
import java.util.*
import kotlin.properties.Delegates


class BlockColoredPlanks() : ShadowFoxBlockMod(Material.wood), ILexiconable {

    private val name = "irisPlanks"
    private val TYPES = 16
    protected var icons : IIcon by Delegates.notNull()

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = Block.soundTypeWood

        setBlockName(this.name)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockColor(): Int {
        return 0xFFFFFF
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderColor(meta: Int): Int {
        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
    }

    @SideOnly(Side.CLIENT)
    override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int {
        val meta = world!!.getBlockMetadata(x, y, z)

        if (meta >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[meta]
        return Color(color[0], color[1], color[2]).rgb
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

    override fun quantityDropped(random: Random): Int { return 1 }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(this)
    }


    @SideOnly(Side.CLIENT)
    override fun getIcon(side: Int, meta: Int): IIcon {
        return icons
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
            icons = IconHelper.forBlock(par1IconRegister, this)

    }

    override fun getSubBlocks(item : Item?, tab : CreativeTabs?, list : MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..(TYPES - 1)) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
