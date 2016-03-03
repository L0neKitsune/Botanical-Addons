package ninja.shadowfox.shadowfox_stuff.common.block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_stuff.common.core.CreativeTab
import ninja.shadowfox.shadowfox_stuff.common.item.ItemBlockMod

/**
 * 1.8 mod
 * created on 2/24/16
 */
open class BlockMod(par2Material: Material) : Block(par2Material) {
    var originalLight: Int = 0

    open val registerInCreative: Boolean = true

    init {
        if (registerInCreative) {
            setCreativeTab(CreativeTab)
        }
    }

    override fun setUnlocalizedName(name: String?): Block? {
        if (shouldRegisterInNameSet())
            GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
        return super.setUnlocalizedName(name)
    }

    protected open fun shouldRegisterInNameSet(): Boolean = true

    override fun setLightLevel(level: Float): Block {
        originalLight = (level * 15).toInt()
        return super.setLightLevel(level)
    }
}
