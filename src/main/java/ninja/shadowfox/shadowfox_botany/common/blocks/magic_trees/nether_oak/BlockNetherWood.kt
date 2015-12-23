package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.nether_oak

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxRotatedPillar
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileLightningRod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.client.render.block.InterpolatedIcon
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.item.lens.ItemLens
import java.util.*

public class BlockNetherWood() : ShadowFoxRotatedPillar(Material.wood), ILexiconable {

    init {
        setBlockName("netherWood")
        isBlockContainer = true

        blockHardness = 2F

        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
    }

    override fun canSustainLeaves(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = true
    override fun isWood(world: IBlockAccess?, x: Int, y: Int, z: Int): Boolean = true

    override fun breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, fortune: Int) {
        var b0: Byte = 4
        var i1: Int = b0 + 1

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (j1 in -b0..b0) for (k1 in -b0..b0)
                for (l1 in -b0..b0) {
                    var blockInWorld: Block = world.getBlock(x + j1, y + k1, z + l1)
                    if (blockInWorld.isLeaves(world, x + j1, y + k1, z + l1)) {
                        blockInWorld.beginLeavesDecay(world, x + j1, y + k1, z + l1)
                    }
                }
        }
        super.breakBlock(world, x, y, z, block, fortune)
    }

    public fun isHeartWood(meta: Int): Boolean {
        return meta and 3 == 1
    }

    override fun isFireSource(world: World?, x: Int, y: Int, z: Int, side: ForgeDirection?): Boolean = true
    override fun isFlammable(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Boolean = false
    override fun getFireSpreadSpeed(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Int = 0

    override fun damageDropped(meta: Int): Int = 0
    override fun quantityDropped(random: Random): Int = 1

    override fun register(par1Str: String) {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, par1Str)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
        iconTop = IconHelper.forName(par1IconRegister, "netherOak_top")
        iconSide = IconHelper.forName(par1IconRegister, "netherOak")
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.lightningSapling
    }
}
