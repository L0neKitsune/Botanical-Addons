package ninja.shadowfox.shadowfox_botany.client.core.handler

import net.minecraft.block.Block
import net.minecraft.client.renderer.block.statemap.StateMap
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.IStringSerializable
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameData
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IKitsuneBlock
import ninja.shadowfox.shadowfox_botany.common.blocks.base.IVariantArrayHolder
import ninja.shadowfox.shadowfox_botany.common.blocks.base.Property
import ninja.shadowfox.shadowfox_botany.common.item.base.IExtraVariantHolder
import ninja.shadowfox.shadowfox_botany.common.item.base.IVariantHolder
import ninja.shadowfox.shadowfox_botany.common.item.base.ItemMod
import ninja.shadowfox.shadowfox_botany.lib.Constants

import java.util.HashMap

/**
 * kitsune
 * created on 3/7/16
 */
object ModelHandler {

    var resourceLocations: HashMap<String, ModelResourceLocation> = HashMap()

    init {
        for (holder in ItemMod.variantHolders)
            registerModels(holder)
    }

    fun registerModels(holder: IVariantHolder) {
        val def = holder.customMeshDefinition()
        if (def != null)
            ModelLoader.setCustomMeshDefinition(holder as Item, def)
        else {
            val i = holder as Item
            registerModels(i, holder.getVariants(), false)
            if (holder is IExtraVariantHolder) {
                registerModels(i, holder.getExtraVariants(), true)
            }
        }
    }

    fun registerModels(item: Item, variants: Array<String>, extra: Boolean) {
        if (item is ItemBlock && item.getBlock() is IKitsuneBlock) {
            val block = item.getBlock() as IKitsuneBlock

            val ignored = block.ignoredProperties
            if (ignored != null && ignored.size > 0) {
                val builder = StateMap.Builder()
                for (p in ignored)
                    builder.ignore(p)

                ModelLoader.setCustomStateMapper(block as Block, builder.build())
            }

            print(block)
            if(block is IVariantArrayHolder) {
                registerVariantsDefaulted(item, block as Block, block.varientArray, IVariantArrayHolder.Companion.HEADER)
                return;
            }
        }

        for (i in variants.indices) {
            val name = Constants.PREFIX_MOD + variants[i]
            val loc = ModelResourceLocation(name, "inventory")
            if (!extra) {
                ModelLoader.setCustomModelResourceLocation(item, i, loc)
                resourceLocations.put(getKey(item, i), loc)
            } else {
                ModelBakery.registerItemVariants(item, loc)
                resourceLocations.put(variants[i], loc)
            }
        }
    }

    private fun registerVariantsDefaulted(item: Item, b: Block, p: Array<Property>, variantHeader: String) {
        val baseName = GameData.getBlockRegistry().getNameForObject(b).toString()
        for (e in p) {
            val variantName = variantHeader + "=" + (e as IStringSerializable).name
            val loc = ModelResourceLocation(baseName, variantName)
            val i = e.meta

            ModelLoader.setCustomModelResourceLocation(item, i, loc)
            resourceLocations.put(getKey(item, i), loc)
        }
    }

    fun getModelLocation(stack: ItemStack?): ModelResourceLocation? {
        if (stack == null)
            return null

        return getModelLocation(stack.item, stack.itemDamage)
    }

    fun getModelLocation(item: Item, meta: Int): ModelResourceLocation? {
        val key = getKey(item, meta)
        if (resourceLocations.containsKey(key))
            return resourceLocations[key]

        return null
    }

    private fun getKey(item: Item, meta: Int): String {
        return "i_" + item.registryName + "@" + meta
    }
}
