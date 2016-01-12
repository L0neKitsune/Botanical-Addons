package ninja.shadowfox.shadowfox_botany.common.blocks.material

import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import vazkii.botania.common.item.equipment.tool.ToolCommons
import java.util.*

class MaterialCustomSmeltingWood : Material(MapColor.woodColor) {
    companion object {
        val instance = MaterialCustomSmeltingWood()

        init {
            ToolCommons.materialsAxe = append(ToolCommons.materialsAxe, instance)
        }

        fun <T> append(arr: Array<T>, element: T): Array<T> {
            val N = arr.size
            val newarr = Arrays.copyOf(arr, N + 1)
            newarr[N] = element
            return newarr
        }
    }

    init {
        this.setBurning()
    }
}
