package ninja.shadowfox.shadowfox_botany.common.item.blocks

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.blocks.rainbow.BlockRainbowDoubleGrass
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import java.awt.Color
import kotlin.properties.Delegates


class ShadowFoxGrassItemBlock(par2Block: Block) : ShadowFoxMetaItemBlock(par2Block) {
    override fun getColorFromItemStack(par1ItemStack : ItemStack, pass : Int) : Int {
        if(par1ItemStack.itemDamage >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[par1ItemStack.itemDamage]
        return Color(color[0], color[1], color[2]).rgb
    }
}

open class ShadowFoxDoubleGrassItemBlock0(par2Block: Block) : ShadowFoxMetaItemBlock(par2Block) {

	open val colorSet = 0
    var topIcon: IIcon by Delegates.notNull()

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.topIcon = IconHelper.forName(par1IconRegister, "irisDoubleGrassTop")
    }

    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return this.topIcon
    }

    override fun getColorFromItemStack(par1ItemStack : ItemStack, pass : Int) : Int {
        if(par1ItemStack.itemDamage >= EntitySheep.fleeceColorTable.size)
            return 0xFFFFFF

        var color = EntitySheep.fleeceColorTable[par1ItemStack.itemDamage+colorSet*8]
        return Color(color[0], color[1], color[2]).rgb
    }
    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>?, par4: Boolean) {
        if(par1ItemStack == null) return
        addStringToTooltip("&7"+StatCollector.translateToLocal("misc.shadowfox_botany.color." + (par1ItemStack.itemDamage+(colorSet*8)))+"&r", par3List)
    } 
}

open class ShadowFoxRainbowDoubleGrassItemBlock(var par2Block: Block) : ShadowFoxRainbowItemBlock(par2Block) {

    override fun getIcon(stack: ItemStack, pass: Int): IIcon {
        return (par2Block as BlockRainbowDoubleGrass).topIcon
    }
}

open class ShadowFoxDoubleGrassItemBlock1(par2Block: Block) : ShadowFoxDoubleGrassItemBlock0(par2Block) {
	override val colorSet = 1
}
