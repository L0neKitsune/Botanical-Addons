package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.lightning_oak

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.FMLLaunchHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.ShadowFoxLeaves
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.LexiconEntry
import vazkii.botania.client.render.block.InterpolatedIcon
import java.util.*


class BlockLightningLeaves() : ShadowFoxLeaves() {
    init {
        setBlockName("lightningLeaves")
        if (FMLLaunchHandler.side().isClient)
            MinecraftForge.EVENT_BUS.register(this)
    }

    @SideOnly(Side.CLIENT)
    override fun registerBlockIcons(iconRegister: IIconRegister) {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun loadTextures(event: TextureStitchEvent.Pre) {
        if (event.map.textureType == 0) {
            var success = true
            var icon = InterpolatedIcon("shadowfox_botany:lightningLeaves")
            if (!event.map.setTextureEntry("shadowfox_botany:lightningLeaves", icon))
                success = false
            var iconOpaque = InterpolatedIcon("shadowfox_botany:lightningLeaves_opaque")
            if (!event.map.setTextureEntry("shadowfox_botany:lightningLeaves_opaque", iconOpaque))
                success = false
            if (success) icons = arrayOf(icon, iconOpaque)
        }
    }

    @SideOnly(Side.CLIENT) override fun getBlockColor(): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun getRenderColor(meta: Int): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int = 0xFFFFFF

    override fun register(name: String) {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.lightningSapling)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(60) == 0) 1 else 0
    }

    override fun func_150125_e(): Array<out String>? {
        return arrayOf("lightningLeaves")
    }

    override fun decayBit(): Int = 0x1

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return LexiconRegistry.lightningSapling
    }
}
