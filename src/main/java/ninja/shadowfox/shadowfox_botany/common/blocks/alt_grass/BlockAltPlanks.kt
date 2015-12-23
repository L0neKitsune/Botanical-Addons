package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass

import cpw.mods.fml.common.IFuelHandler
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.material.MaterialCustomSmeltingWood
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxBlockMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.lib.ALT_TYPES
import vazkii.botania.api.lexicon.ILexiconable
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*


public class BlockAltPlanks(): ShadowFoxBlockMod(MaterialCustomSmeltingWood.material), ILexiconable, IFuelHandler {

    private val name = "altPlanks"
    protected var icons: Array<IIcon> = emptyArray()

    init {
        blockHardness = 2F
        setLightLevel(0f)
        stepSound = soundTypeWood

        setBlockName(this.name)
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
        GameRegistry.registerFuelHandler(this)
    }

    override fun registerBlockIcons(iconRegister: IIconRegister) {
        icons = Array(6, { i -> IconHelper.forBlock(iconRegister, this, "${ALT_TYPES[i]}") })
    }

    override fun getIcon(side: Int, meta: Int): IIcon {
        return icons[meta]
    }

    override fun isToolEffective(type: String?, metadata: Int): Boolean {
        return (type != null && type.equals("axe", true))
    }

    override fun getHarvestTool(metadata : Int): String {
        return "axe"
    }

    override fun shouldRegisterInNameSet(): Boolean {
        return false
    }

    override fun damageDropped(par1: Int): Int {
        return par1
    }

    override fun setBlockName(par1Str: String): Block {
        register(par1Str)
        return super.setBlockName(par1Str)
    }

    override fun quantityDropped(random: Random): Int { return 1 }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(this)
    }

    override fun isFlammable(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Boolean = false
    override fun getFireSpreadSpeed(world: IBlockAccess?, x: Int, y: Int, z: Int, face: ForgeDirection?): Int = 0

    override fun isWood(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean { return true }

    internal fun register(name: String) {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
        Blocks.netherrack
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..5) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override fun getPickBlock(target: MovingObjectPosition?, world: World, x: Int, y: Int, z: Int): ItemStack {
        val meta = world.getBlockMetadata(x, y, z)
        return ItemStack(this, 1, meta)
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.lightningSapling
    }

    override fun getBurnTime(fuel: ItemStack): Int {
        return if (fuel.item == Item.getItemFromBlock(this)) 600 else 0
    }
}
