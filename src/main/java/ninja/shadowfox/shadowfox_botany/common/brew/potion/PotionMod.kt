package ninja.shadowfox.shadowfox_botany.common.brew.potion


import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


open class PotionMod(name: String, badEffect: Boolean, color: Int, iconIndex: Int) : Potion(resource, badEffect, color) {

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
