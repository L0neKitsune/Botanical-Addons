package ninja.shadowfox.shadowfox_botany.common.lexicon

import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.*


public class ShadowfoxLexiconEntry(unlocalizedName: String, category: LexiconCategory) : LexiconEntry(unlocalizedName, category), IAddonEntry
{
    init {
        BotaniaAPI.addEntry(this, category)
    }

    override fun setLexiconPages(vararg pages: LexiconPage): LexiconEntry {
        for (page in pages) {
            page.unlocalizedName = "shadowfox_botany.page." + getLazyUnlocalizedName() + page.unlocalizedName
            if (page is ITwoNamedPage) {
                page.secondUnlocalizedName = "shadowfox_botany.page." + getLazyUnlocalizedName() + page.secondUnlocalizedName
            }
        }

        return super.setLexiconPages(*pages)
    }
    override fun getUnlocalizedName(): String {
        return "shadowfox_botany.entry." + super.getUnlocalizedName()
    }

    override fun getSubtitle(): String? {
        return "[Botanical Add-ons]"
    }

    fun getLazyUnlocalizedName(): String {
        return super.getUnlocalizedName()
    }
}

