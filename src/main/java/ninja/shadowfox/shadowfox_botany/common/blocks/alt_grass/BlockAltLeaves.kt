package ninja.shadowfox.shadowfox_botany.common.blocks.alt_grass

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxLeaves
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemUniqueSubtypedBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import ninja.shadowfox.shadowfox_botany.lib.ALT_TYPES
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*


class BlockAltLeaves(): ShadowFoxLeaves() {

    protected var icon_norm: Array<IIcon> = emptyArray()
    protected var icon_opaque: Array<IIcon> = emptyArray()

    init {
        setBlockName("altLeaves")
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
    }

    @SideOnly(Side.CLIENT) override fun getBlockColor(): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun getRenderColor(meta: Int): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int = 0xFFFFFF

    override fun register(name: String) {
        GameRegistry.registerBlock(this, ItemUniqueSubtypedBlockMod::class.java, name)
    }

    override fun registerBlockIcons(iconRegister: IIconRegister) {
        icon_norm = Array(6, { i -> IconHelper.forBlock(iconRegister, this, "${ALT_TYPES[i]}") })
        icon_opaque = Array(6, { i -> IconHelper.forBlock(iconRegister, this, "${ALT_TYPES[i]}_opaque") })
    }

    override fun getIcon(side: Int, meta: Int): IIcon {
        this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics)
        return if (this.field_150121_P) icon_norm[meta and decayBit().inv()] else icon_opaque[meta and decayBit().inv()]
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(60) == 0) 1 else 0
    }

    override fun func_150125_e(): Array<out String>? {
        return arrayOf("altLeaves")
    }

    override fun getSubBlocks(item: Item?, tab: CreativeTabs?, list: MutableList<Any?>?) {
        if (list != null && item != null)
            for (i in 0..5) {
                list.add(ItemStack(item, 1, i))
            }
    }

    override fun decayBit(): Int = 0x8

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}
