package ninja.shadowfox.shadowfox_botany.common.lexicon

import net.minecraft.util.ResourceLocation
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.LexiconCategory

class ShadowFoxLexiconCategory(unlocalizedName: String, priority: Int) : LexiconCategory("shadowfox_botany.category." + unlocalizedName) {

    init {
        setIcon(ResourceLocation("shadowfox_botany:textures/gui/categories/$unlocalizedName.png"))
        setPriority(priority)
        BotaniaAPI.addCategory(this)
    }

}
