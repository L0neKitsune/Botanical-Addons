package ninja.shadowfox.shadowfox_botany.common.blocks.rainbow

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemShimmerSlabMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.common.block.decor.quartz.BlockSpecialQuartz
import vazkii.botania.common.block.decor.quartz.BlockSpecialQuartzSlab
import vazkii.botania.common.block.decor.quartz.BlockSpecialQuartzStairs

/**
 * @author WireSegal
 * Created at 7:59 PM on 2/13/16.
 */
class BlockShimmerQuartz : BlockSpecialQuartz("Shimmer") {

    init {
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
    }

    val iconNames = arrayOf("block${type}Quartz0", "chiseled${type}Quartz0", "pillar${type}Quartz0", null, null)

    override fun getNames(): Array<String> {
        return arrayOf("tile.shadowfox_botany:block${this.type}Quartz",
                "tile.shadowfox_botany:chiseled${this.type}Quartz",
                "tile.shadowfox_botany:pillar${this.type}Quartz")
    }

    var specialQuartzIcons: Array<IIcon?> = arrayOfNulls(this.iconNames.size)
    var specialQuartzTopIcon: IIcon? = null
    var chiseledSpecialQuartzIcon: IIcon? = null
    var pillarSpecialQuartzIcon: IIcon? = null

    @SubscribeEvent
    fun registerIcons(e: TextureStitchEvent.Pre) {
        if (e.map.textureType == 0) {
            this.specialQuartzIcons = arrayOfNulls<IIcon>(this.iconNames.size)

            for (i in this.specialQuartzIcons.indices) {
                if (this.iconNames[i] == null)
                    this.specialQuartzIcons[i] = this.specialQuartzIcons[i - 1]
                else
                    this.specialQuartzIcons[i] = InterpolatedIconHelper.forName(e.map, this.iconNames[i]!!)
            }

            this.specialQuartzTopIcon = InterpolatedIconHelper.forName(e.map, "block${this.type}Quartz1")
            this.chiseledSpecialQuartzIcon = InterpolatedIconHelper.forName(e.map, "chiseled${this.type}Quartz1")
            this.pillarSpecialQuartzIcon = InterpolatedIconHelper.forName(e.map, "pillar${this.type}Quartz1")
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(par1IconRegister: IIconRegister) {
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(par1: Int, par2: Int): IIcon? {
        var par3 = par2
        if (par3 != 2 && par3 != 3 && par3 != 4) {
            if (par1 != 1 && (par1 != 0 || par3 != 1)) {
                if (par1 == 0) {
                    return this.specialQuartzTopIcon
                } else {
                    if (par3 < 0 || par3 >= this.specialQuartzIcons.size) {
                        par3 = 0
                    }

                    return this.specialQuartzIcons[par3]
                }
            } else {
                return if (par3 == 1) this.chiseledSpecialQuartzIcon else this.specialQuartzTopIcon
            }
        } else {
            return if (par3 != 2 || par1 != 1 && par1 != 0) if (par3 != 3 || par1 != 5 && par1 != 4) if (par3 != 4 || par1 != 2 && par1 != 3) this.specialQuartzIcons[par3] else this.pillarSpecialQuartzIcon else this.pillarSpecialQuartzIcon else this.pillarSpecialQuartzIcon
        }
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry? {
        return LexiconRegistry.shimmer
    }
}

class BlockShimmerQuartzSlab(val block: BlockShimmerQuartz, val full: Boolean) : BlockSpecialQuartzSlab(block, full) {
    override fun getSingleBlock(): BlockSlab? {
        return ShadowFoxBlocks.shimmerQuartzSlab
    }

    override fun getFullBlock(): BlockSlab? {
        return ShadowFoxBlocks.shimmerQuartzSlabFull
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry? {
        return block.getEntry(world, x, y, z, player, lexicon)
    }

    override fun register() {
        GameRegistry.registerBlock(this, ItemShimmerSlabMod::class.java, "quartzSlab${block.type}${if (full) "Full" else "Half"}")
    }
}

class BlockShimmerQuartzStairs(val block: BlockShimmerQuartz) : BlockSpecialQuartzStairs(block) {
    lateinit var unlocName: String

    override fun setBlockName(par1Str: String): Block? {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, par1Str)
        this.unlocName = "tile.$par1Str"
        return this
    }

    override fun getUnlocalizedName(): String? {
        return unlocName
    }

    override fun getEntry(world: World?, x: Int, y: Int, z: Int, player: EntityPlayer?, lexicon: ItemStack?): LexiconEntry? {
        return if (block is ILexiconable) block.getEntry(world, x, y, z, player, lexicon) else null
    }
}


