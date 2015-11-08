package ninja.shadowfox.shadowfox_botany.common.brew

import net.minecraft.potion.Potion
import ninja.shadowfox.shadowfox_botany.common.brew.potion.PotionManaVoid


object ShadowFoxPotions {
    public val manaVoid: Potion

    init {
        manaVoid = PotionManaVoid()
    }
}