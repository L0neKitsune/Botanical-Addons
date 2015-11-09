package ninja.shadowfox.shadowfox_botany.common.item.rods

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions
import ninja.shadowfox.shadowfox_botany.common.item.StandardItem
/**
 * Basically to test shit
 */
public open class UnlocalizedThoughtRod(name: String = "unlocalizedThoughtRod") : StandardItem(name) {

    init {
        setMaxStackSize(1);
    }

    override fun onItemUse(par1ItemStack : ItemStack, par2EntityPlayer : EntityPlayer, par3World : World,
                           par4 : Int, par5 : Int, par6 : Int, par7 : Int, par8 : Float, par9 : Float, par10 : Float) : Boolean {
        return true
    }

    override fun itemInteractionForEntity(p_111207_1_: ItemStack?, p_111207_2_: EntityPlayer?, p_111207_3_: EntityLivingBase?): Boolean {
        p_111207_2_!!.addChatMessage((ChatComponentText(p_111207_3_.toString())).setChatStyle((ChatStyle()).setColor(EnumChatFormatting.RED)))
        return true
    }

    override fun onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer) : ItemStack {

        player.addPotionEffect(PotionEffect(ShadowFoxPotions.manaVoid.id, 1800, 0))

        if(!world.isRemote && player.isSneaking) {
            if (stack.itemDamage >= 15) stack.itemDamage = 0 else stack.itemDamage++
            world.playSoundAtEntity(player, "botania:ding", 0.1F, 1F);
        }

        return stack;
    }

    override fun isFull3D() : Boolean {
        return true;
    }
}
