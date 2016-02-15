package ninja.shadowfox.shadowfox_botany.lib

object LibOreDict {
    val TWIG_THUNDERWOOD = "twigThunderwood"
    val TWIG_NETHERWOOD = "twigNetherwood"

    val SPLINTERS_THUNDERWOOD = "splinterThunderwood"
    val SPLINTERS_NETHERWOOD = "splinterNetherwood"

    val COAL_NETHERWOOD = "coalFlame"

    val HOLY_PENDANT = "holyPendant"

    val COLORS = arrayOf("White", "Orange", "Magenta", "LightBlue",
            "Yellow", "Lime", "Pink", "Gray",
            "LightGray", "Cyan", "Purple", "Blue",
            "Brown", "Green", "Red", "Black",
            "Rainbow")

    val IRIS_WOOD = "irisWood"
    val IRIS_LEAVES = "irisLeaves"
    val IRIS_DIRT = "irisDirt"

    val WOOD = Array(COLORS.size, { i -> "$IRIS_WOOD${COLORS[i]}" })
    val LEAVES = Array(COLORS.size, { i -> "$IRIS_LEAVES${COLORS[i]}" })
    val DIRT = Array(COLORS.size, { i -> "$IRIS_DIRT${COLORS[i]}" })

    val DYES = Array(COLORS.size, { i -> "dye${COLORS[i]}" })
    val FLORAL_POWDER = "dyeFloralPowder"
    val PETAL = "petalMystic"

    val RAINBOW_PETAL = "petalRainbow"
    val RAINBOW_FLOWER = "mysticFlowerRainbow"
    val RAINBOW_DOUBLE_FLOWER = "${RAINBOW_FLOWER}Double"
}
