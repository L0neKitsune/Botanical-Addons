package ninja.shadowfox.shadowfox_botany.common.utils.helper

import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.item.Item
import net.minecraft.util.IIcon
import vazkii.botania.client.render.block.InterpolatedIcon

object InterpolatedIconHelper {

    fun forName(map: TextureMap, name: String): IIcon? {
        var localIcon = InterpolatedIcon("shadowfox_botany:" + name)
        if (map.setTextureEntry("shadowfox_botany:" + name, localIcon)) {
            return localIcon
        }
        return null
    }

    fun forName(map: TextureMap, name: String, dir: String): IIcon? {
        return forName(map, "$dir/$name")
    }

    fun forBlock(map: TextureMap, block: Block): IIcon? {
        return forName(map, block.unlocalizedName.replace("tile\\.".toRegex(), ""))
    }

    fun forBlock(map: TextureMap, block: Block, i: Int): IIcon? {
        return forBlock(map, block, Integer.toString(i))
    }

    fun forBlock(map: TextureMap, block: Block, i: Int, dir: String): IIcon? {
        return forBlock(map, block, Integer.toString(i), dir)
    }

    fun forBlock(map: TextureMap, block: Block, s: String): IIcon? {
        return forName(map, block.unlocalizedName.replace("tile\\.".toRegex(), "") + s)
    }

    fun forBlock(map: TextureMap, block: Block, s: String, dir: String): IIcon? {
        return forName(map, block.unlocalizedName.replace("tile\\.".toRegex(), "") + s, dir)
    }

    fun forItem(map: TextureMap, item: Item): IIcon? {
        return forName(map, item.unlocalizedName.replace("item\\.".toRegex(), ""))
    }

    fun forItem(map: TextureMap, item: Item, i: Int): IIcon? {
        return forItem(map, item, Integer.toString(i))
    }

    fun forItem(map: TextureMap, item: Item, i: Int, dir: String): IIcon? {
        return forItem(map, item, Integer.toString(i), dir)
    }

    fun forItem(map: TextureMap, item: Item, s: String): IIcon? {
        return forName(map, item.unlocalizedName.replace("item\\.".toRegex(), "") + s)
    }

    fun forItem(map: TextureMap, item: Item, s: String, dir: String): IIcon? {
        return forName(map, item.unlocalizedName.replace("item\\.".toRegex(), "") + s, dir)
    }
}
