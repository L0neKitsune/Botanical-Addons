package ninja.shadowfox.shadowfox_botany.common.blocks.material

import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import vazkii.botania.common.item.equipment.tool.ToolCommons
import java.util.*

class MaterialCustomSmeltingWood : Material(MapColor.woodColor) {
    companion object {
        val instance = MaterialCustomSmeltingWood()

        init {
            ToolCommons.materialsAxe.append(instance)
        }

        fun <T> Array<T>.append(element: T): Array<T> {
            val N = this.size
            val newarr = Arrays.copyOf(this, N + 1)
            newarr[N] = element
            return newarr
        }
    }

    init {
        this.setBurning()
    }
}
