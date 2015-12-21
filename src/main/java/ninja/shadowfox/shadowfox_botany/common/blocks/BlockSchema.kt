package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.FMLLog
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import org.apache.logging.log4j.Level
import vazkii.botania.api.wand.IWandable

/**
 * Created by l0nekitsune on 12/20/15.
 */
class BlockSchema() : ShadowFoxBlockMod(Material.wood), IWandable {
    init {
        setBlockName("schemaBlock")
    }

    override fun onUsedByWand(p0: EntityPlayer?, p1: ItemStack?, p2: World?, p3: Int, p4: Int, p5: Int, p6: Int): Boolean {
        for (dir in ForgeDirection.VALID_DIRECTIONS){
            for (i in 0..64) {
//                var block = p2!!.getBlock(p3 + dir.offsetX * i, p4 + dir.offsetY * i, p5 + dir.offsetZ * i)
//                if (block === ShadowFoxBlocks.schemaBlock)
//                    FMLLog.log(Level.DEBUG, "block: ${block.minX} ${block.minY} ${block.minZ}")
            }
        }

        return true
    }
}
