package ninja.shadowfox.shadowfox_stuff.common.item

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.ArrowLooseEvent
import net.minecraftforge.event.entity.player.ArrowNockEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import ninja.shadowfox.shadowfox_stuff.api.ISFBolt
import ninja.shadowfox.shadowfox_stuff.common.core.CreativeTab
import ninja.shadowfox.shadowfox_stuff.common.entity.EntityBolt

/**
 * 1.8 mod
 * created on 2/20/16
 */
class ItemLongBow: ItemBow {
    constructor() : this("longBow")

    constructor(name: String): super() {
        creativeTab = CreativeTab
        unlocalizedName = name
        maxDamage = 500
        setFull3D()
    }

    override fun setUnlocalizedName(par1Str: String): Item {
        GameRegistry.registerItem(this, par1Str)
        return super.setUnlocalizedName(par1Str)
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item.", "item.shadowfox_stuff");
    }

    override fun onItemRightClick(itemStackIn: ItemStack?, worldIn: World?, playerIn: EntityPlayer?): ItemStack? {
        if (itemStackIn != null && worldIn != null && playerIn != null) {
            var event = ArrowNockEvent(playerIn, itemStackIn);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled) return event.result

            if (getBolt(itemStackIn, worldIn, playerIn) != null)
                playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));

            return itemStackIn
        }
        return itemStackIn
    }

    override fun onPlayerStoppedUsing(stack: ItemStack?, worldIn: World?, playerIn: EntityPlayer?, timeLeft: Int) {
        if (stack != null && worldIn != null && playerIn != null) {
            var j = ((getMaxItemUseDuration(stack) - timeLeft) * chargeVelocityMultiplier()).toInt()

            val event = ArrowLooseEvent(playerIn, stack, j);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled) return

            j = event.charge

            var bolt = getBolt(stack, worldIn, playerIn);
            var infinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            if (bolt != null) {
                var  f = j / 20.0F;
                f = (f * f + f * 2.0F) / 3.0F;

                if (f < 0.1) return

                if (f > 1.0F) f = 1.0F

                val entitybolt = (bolt as ISFBolt).makeBolt(worldIn, playerIn, f * 2.0f);

                if (f == 1.0F) entitybolt.setIsCritical(true);

                var k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

                if (k > 0)
                    entitybolt.damage = (entitybolt.damage + k * 0.5 + 0.5)

                var l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

                if (l > 0) entitybolt.knockbackStrength = l

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
                    entitybolt.setFire(100);

                stack.damageItem(1, playerIn)

                worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                onFire(playerIn, bolt, infinity, entitybolt);

                if (!worldIn.isRemote)
                    worldIn.spawnEntityInWorld(entitybolt);
            }
        }
    }

    fun chargeVelocityMultiplier(): Float = 1F

    fun getBolt(stack: ItemStack, world: World, player: EntityPlayer): Item? {
//        if (player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0)
//            return ModItems.stdArrow
//        else
            return player.getBolt()
    }

    fun EntityPlayer.getBolt(): Item? {
        for (item in this.inventory.mainInventory)
            if (item != null && item.item is ISFBolt) return item.item

        return null
    }

    fun onFire(player: EntityPlayer, bolt: Item, infinity: Boolean, arrow: EntityBolt) {
        if(infinity)
            arrow.canBePickedUp = 2;
        else player.inventory.consumeInventoryItem(bolt);
    }
}
