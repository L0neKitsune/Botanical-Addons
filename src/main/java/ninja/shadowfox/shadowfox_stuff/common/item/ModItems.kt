package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.item.Item
import ninja.shadowfox.shadowfox_stuff.common.item.rods.ItemRainbowLightRod

/**
 * 1.8 mod
 * created on 2/20/16
 */
object ModItems {
//    val stdArrow: Item
//    val longBow: Item
    val rainbowLight: Item
    val itemDebugger: Item
//    val potionPaste: Item

    init {
//        stdArrow = ItemArrow()
//        longBow = ItemLongBow()
//        potionPaste = ItemPotionPaste()
        rainbowLight = ItemRainbowLightRod()
        itemDebugger = ItemDebugger()
    }
}
