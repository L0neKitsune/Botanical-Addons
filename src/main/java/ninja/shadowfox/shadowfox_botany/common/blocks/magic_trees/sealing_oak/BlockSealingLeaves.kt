package ninja.shadowfox.shadowfox_botany.common.blocks.magic_trees.sealing_oak

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.event.sound.PlaySoundEvent17
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.base.BlockLeavesMod
import ninja.shadowfox.shadowfox_botany.common.item.blocks.ItemBlockMod
import ninja.shadowfox.shadowfox_botany.common.lexicon.LexiconRegistry
import vazkii.botania.api.lexicon.LexiconEntry
import java.util.*

class BlockSealingLeaves() : BlockLeavesMod(), ISoundSilencer {
    init {
        setBlockName("sealingLeaves")
    }

    @SideOnly(Side.CLIENT) override fun getBlockColor(): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun getRenderColor(meta: Int): Int = 0xFFFFFF
    @SideOnly(Side.CLIENT) override fun colorMultiplier(world: IBlockAccess?, x: Int, y: Int, z: Int): Int = 0xFFFFFF

    override fun canSilence(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Boolean = dist <= 8
    override fun getVolumeMultiplier(world: World, x: Int, y: Int, z: Int, dist: Double, soundEvent: PlaySoundEvent17): Float = 0.5f

    override fun register(name: String) {
        GameRegistry.registerBlock(this, ItemBlockMod::class.java, name)
    }

    override fun getItemDropped(meta: Int, random: Random, fortune: Int): Item {
        return Item.getItemFromBlock(ShadowFoxBlocks.sealingSapling)
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(60) == 0) 1 else 0
    }

    override fun func_150125_e(): Array<out String>? {
        return arrayOf("sealingLeaves")
    }

    override fun decayBit(): Int = 0x1

    override fun getEntry(p0: World?, p1: Int, p2: Int, p3: Int, p4: EntityPlayer?, p5: ItemStack?): LexiconEntry? {
        return null
    }
}
