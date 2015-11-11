package ninja.shadowfox.shadowfox_botany.common.brew.potion

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.ResourceLocation

/**
 * Created by l0nekitsune on 11/6/15.
 */
open class PotionMod(id: Int, name: String, badEffect: Boolean, color: Int, iconIndex: Int) :
        Potion(id, badEffect, color)
{

    init {
        this.setPotionName("shadowfox_botany.potion." + name)
        this.setIconIndex(iconIndex % 8, iconIndex / 8)
    }

    @SideOnly(Side.CLIENT)
    override fun getStatusIconIndex(): Int {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource)
        return super.getStatusIconIndex()
    }

    fun hasEffect(entity: EntityLivingBase): Boolean {
        return this.hasEffect(entity, this)
    }

    fun hasEffect(entity: EntityLivingBase, potion: Potion): Boolean {
        return entity.getActivePotionEffect(potion) != null
    }

    companion object {
        private val resource = ResourceLocation("shadowfox_botany:textures/gui/potions.png")
    }
}
