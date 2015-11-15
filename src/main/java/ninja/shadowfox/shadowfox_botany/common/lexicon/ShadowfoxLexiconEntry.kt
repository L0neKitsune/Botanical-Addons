package ninja.shadowfox.shadowfox_botany.common.lexicon

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.*

fun ShadowfoxLexiconEntry(unlocalizedName: String, category: LexiconCategory, block: Block) = ShadowfoxLexiconEntry(unlocalizedName, category, ItemStack(block))
fun ShadowfoxLexiconEntry(unlocalizedName: String, category: LexiconCategory, item: Item) = ShadowfoxLexiconEntry(unlocalizedName, category, ItemStack(item))

public class ShadowfoxLexiconEntry(unlocalizedName: String, category: LexiconCategory, var stack: ItemStack? = null) : LexiconEntry(unlocalizedName, category), IAddonEntry {
    init {
        if (stack != null) icon = stack
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

    override fun getTagline(): String {
        return "shadowfox_botany.tagline." + super.getUnlocalizedName()
    }

    override fun getSubtitle(): String? {
        return "[Botanical Addons]"
    }

    fun getLazyUnlocalizedName(): String {
        return super.getUnlocalizedName()
    }
}
