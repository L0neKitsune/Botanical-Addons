package ninja.shadowfox.botania_addon.common.utils.helper

import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item
import net.minecraft.util.IIcon

/**
 * Created by l0nekitsune on 10/17/15.
 */
object IconHelper {

    fun forName(ir: IIconRegister, name: String): IIcon {
        return ir.registerIcon("botania_addon:" + name)
    }

    fun forName(ir: IIconRegister, name: String, dir: String): IIcon {
        return ir.registerIcon("botania_addon:$dir/$name")
    }

    fun forBlock(ir: IIconRegister, block: Block): IIcon {
        return forName(ir, block.unlocalizedName.replaceAll("tile\\.", ""))
    }

    fun forBlock(ir: IIconRegister, block: Block, i: Int): IIcon {
        return forBlock(ir, block, Integer.toString(i))
    }

    fun forBlock(ir: IIconRegister, block: Block, i: Int, dir: String): IIcon {
        return forBlock(ir, block, Integer.toString(i), dir)
    }

    fun forBlock(ir: IIconRegister, block: Block, s: String): IIcon {
        return forName(ir, block.unlocalizedName.replaceAll("tile\\.", "") + s)
    }

    fun forBlock(ir: IIconRegister, block: Block, s: String, dir: String): IIcon {
        return forName(ir, block.unlocalizedName.replaceAll("tile\\.", "") + s, dir)
    }

    fun forItem(ir: IIconRegister, item: Item): IIcon {
        return forName(ir, item.unlocalizedName.replaceAll("item\\.", ""))
    }

    fun forItem(ir: IIconRegister, item: Item, i: Int): IIcon {
        return forItem(ir, item, Integer.toString(i))
    }

    fun forItem(ir: IIconRegister, item: Item, s: String): IIcon {
        return forName(ir, item.unlocalizedName.replaceAll("item\\.", "") + s)
    }
}
