package ninja.shadowfox.shadowfox_botany.common.blocks

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ShadowFoxRainbowItemBlock
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.util.*


public class BlockRainbowLeaves(): ShadowFoxLeaves() {
    lateinit protected var icon : IIcon
    lateinit protected var icon_opaque : IIcon

    init {
        setBlockName("rainbowLeaves")
        MinecraftForge.EVENT_BUS.register(this)
    }

    override fun register(name: String) {
        GameRegistry.registerBlock(this, ShadowFoxRainbowItemBlock::class.java, name)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(20) == 0) 1 else 0
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.irisSapling)
    }

    override fun func_150125_e(): Array<String> {
        return arrayOf("rainbowLeaves")
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if(event.map.textureType == 0) {
            var icon = InterpolatedIcon("shadowfox_botany:rainbowLeaves");
            if(event.map.setTextureEntry("shadowfox_botany:rainbowLeaves", icon))
                this.icon = icon

            var icon_opq = InterpolatedIcon("shadowfox_botany:rainbowLeaves_opaque");
            if(event.map.setTextureEntry("shadowfox_botany:rainbowLeaves_opaque", icon_opq))
                this.icon_opaque = icon_opq
        }
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {}

    @SideOnly(Side.CLIENT)
    override fun getIcon(meta: Int, pass: Int): IIcon {
        this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics)
        return if (this.field_150121_P) icon else icon_opaque
    }

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.irisSapling
    }
}