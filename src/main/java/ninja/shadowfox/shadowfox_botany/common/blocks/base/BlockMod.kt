package ninja.shadowfox.shadowfox_botany.common.blocks.base

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IKitsuneBlock
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.item.base.ItemBlockMod
import java.util.*

/**
 * 1.8 mod
 * created on 2/24/16
 */
open class BlockMod(val name: String, materialIn: Material, vararg srt_variants: String): Block(materialIn), IKitsuneBlock {
    var variant: Array<String> = arrayOf()

    init {
        variant = arrayOf(*srt_variants)

        if (srt_variants.size == 0)
            variant = arrayOf(name)

        unlocalizedName = name
        setCreativeTab(ShadowFoxCreativeTab)
    }

    open val itemClass: Class<out ItemBlock> = ItemBlockMod::class.java

    override fun setUnlocalizedName(name: String): Block
    {
        super.setUnlocalizedName(name);
        registryName = name;
        GameRegistry.registerBlock(this, ItemBlockMod::class.java);
        return this;
    }

    override fun getVariants(): Array<String> = variant

    override fun customMeshDefinition(): ItemMeshDefinition? = null

    override val bareName: String = name
    override val ignoredProperties: List<IProperty<*>> = arrayListOf()

    override fun getRenderType() = 3
    override fun getBlockRarity(stack: ItemStack) = EnumRarity.COMMON
}
