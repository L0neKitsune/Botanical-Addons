package ninja.shadowfox.shadowfox_stuff.common.block

import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.FMLLaunchHandler
import ninja.shadowfox.shadowfox_stuff.common.core.CreativeTab
import ninja.shadowfox.shadowfox_stuff.common.item.ItemBlockMod
import vazkii.botania.common.core.BotaniaCreativeTab

/**
 * 1.8 mod
 * created on 2/24/16
 */
abstract class BlockModContainer<T : TileEntity> protected constructor(par2Material: Material) : BlockContainer(par2Material) {
    var originalLight: Int = 0

    init {
        if (this.registerInCreative()) {
            this.setCreativeTab(CreativeTab)
        }

    }

    override fun getRenderType(): Int {
        return 3
    }

    override fun setUnlocalizedName(par1Str: String): Block {
        if (this.shouldRegisterInNameSet()) {
            GameRegistry.registerBlock(this, vazkii.botania.common.item.block.ItemBlockMod::class.java, par1Str)
        }

        return super.setUnlocalizedName(par1Str)
    }

    protected open fun shouldRegisterInNameSet(): Boolean {
        return true
    }

    override fun setLightLevel(p_149715_1_: Float): Block {
        this.originalLight = (p_149715_1_ * 15.0f).toInt()
        return super.setLightLevel(p_149715_1_)
    }

    open fun registerInCreative(): Boolean {
        return true
    }

    abstract override fun createNewTileEntity(var1: World, var2: Int): T
}

