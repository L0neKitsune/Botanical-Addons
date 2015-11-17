package ninja.shadowfox.shadowfox_botany.common.lexicon


import net.minecraft.util.ResourceLocation

class ShadowFoxLexiconCategory(unlocalizedName: String, priority: Int) : vazkii.botania.api.lexicon.LexiconCategory("shadowfox_botany.category." + unlocalizedName) {

    init {
        setIcon(ResourceLocation("shadowfox_botany:textures/gui/categories/$unlocalizedName.png"))
        setPriority(priority)
    }

}
