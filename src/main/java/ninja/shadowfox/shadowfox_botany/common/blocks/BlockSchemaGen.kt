package ninja.shadowfox.shadowfox_botany.common.blocks

import codechicken.core.CommonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import vazkii.botania.api.wand.IWandable
import java.io.File


class BlockSchemaGen() : ShadowFoxBlockMod(Material.wood), IWandable {
    lateinit var icon1: IIcon
    lateinit var icon2: IIcon
    lateinit var icon3: IIcon

    init {
        setBlockName("schemaBlockGen")
        isBlockContainer = true
    }

    override fun getIcon(side: Int, meta: Int): IIcon? {
        when (side) {
            0, 1 -> return icon1
            2, 3 -> return icon2
            else -> return icon3
        }
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        icon1 = IconHelper.forName(par1IconRegister, "schema1")
        icon2 = IconHelper.forName(par1IconRegister, "schema2")
        icon3 = IconHelper.forName(par1IconRegister, "schema3")
    }

    override fun onUsedByWand(p0: EntityPlayer?, p1: ItemStack?, p2: World?, p3: Int, p4: Int, p5: Int, p6: Int): Boolean {

        if (p2 != null) {

            val schemaText = getSchema()

            if (schemaText != null) {
                val type = object : TypeToken<List<BlockElement>>() {}.type

                var arr = Gson().fromJson<List<BlockElement>>(schemaText, type)

                for (ele in arr) {
                    for (loc in ele.location) {
                        p2.setBlock(p3 + loc.x, p4 + loc.y, p5 + loc.z, Block.getBlockFromName(ele.block), loc.meta, 3)
                    }
                }
            } else {

            }
        }

        return true
    }

    fun getSchema(meta: Int = 0): String? {
        val e = File(CommonUtils.getMinecraftDir(), "config/BotanicalAddons/schema_$meta.txt")
        if (!e.parentFile.exists()) e.parentFile.mkdirs()

        if (e.exists()) {
            return e.readText()
        } else return null
    }

    class BlockElement(val block: String, val location: List<LocationElement>)
    class LocationElement(val x: Int, val y: Int, val z: Int, val meta: Int)
}
