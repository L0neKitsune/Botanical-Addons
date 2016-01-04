package ninja.shadowfox.shadowfox_botany.common.blocks.material

import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material

class MaterialCustomSmeltingWood: Material(MapColor.woodColor) {
    companion object {
        val material = MaterialCustomSmeltingWood()
    }

    init {
        this.setBurning()
    }
}
