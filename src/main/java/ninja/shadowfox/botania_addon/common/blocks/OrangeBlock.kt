package ninja.shadowfox.botania_addon.common.blocks

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material


class OrangeBlock : BlockMod(Material.rock) {
    private val name = "OrangeBlock"


    init {
        blockHardness = 2.0f
        setLightLevel(6f)
        blockResistance = 10.0f
        stepSound = Block.soundTypeStone
        setBlockName(name)

        GameRegistry.registerBlock(this, name)

    }
}
