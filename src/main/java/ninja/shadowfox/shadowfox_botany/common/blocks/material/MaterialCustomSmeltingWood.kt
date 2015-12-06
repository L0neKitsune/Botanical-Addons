package ninja.shadowfox.shadowfox_botany.common.blocks.material

import net.minecraft.block.material.Material
import net.minecraft.block.material.MapColor

class MaterialCustomSmeltingWood: Material(MapColor.woodColor) {
    companion object {
        val material = MaterialCustomSmeltingWood()
    }

    init {
        this.setBurning()
    }
}
