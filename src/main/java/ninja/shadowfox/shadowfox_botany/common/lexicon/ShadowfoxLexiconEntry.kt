package ninja.shadowfox.shadowfox_botany.common.lexicon

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.lexicon.*

open class ShadowfoxLexiconEntry : LexiconEntry, IAddonEntry {

    constructor(unlocalizedName: String, category: LexiconCategory?, stack: ItemStack?) : super(unlocalizedName, category) {
        if (stack != null) icon = stack
        BotaniaAPI.addEntry(this, category)
    }

    constructor(unlocalizedName: String, category: LexiconCategory?, block: Block) : this(unlocalizedName, category, ItemStack(block)) {
    }

    constructor(unlocalizedName: String, category: LexiconCategory?, item: Item) : this(unlocalizedName, category, ItemStack(item)) {
    }

    constructor(unlocalizedName: String, category: LexiconCategory?) : this(unlocalizedName, category, null as ItemStack?) {
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
