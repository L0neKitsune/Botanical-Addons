package ninja.shadowfox.shadowfox_botany.common.blocks.subtile

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import vazkii.botania.api.subtile.signature.SubTileSignature

class ShadowFoxSignature(val name: String) : SubTileSignature() {

    var icon: IIcon? = null
    override fun registerIcons(reg: IIconRegister) {
        icon = reg.registerIcon("shadowfox_botany:$name")
    }

    override fun getIconForStack(item: ItemStack?): IIcon? = icon
    override fun getUnlocalizedNameForStack(item: ItemStack): String = "tile.shadowfox_botany:$name.name"
    override fun getUnlocalizedLoreTextForStack(item: ItemStack): String = "tile.shadowfox_botany:$name.lore"
}
