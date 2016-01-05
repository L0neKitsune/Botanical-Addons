package ninja.shadowfox.shadowfox_botany.api.item;

import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.item.ICosmeticAttachable;

public class ColorOverrideHelper {
    public static int getColor(EntityPlayer player) {
        if (player != null) {
            IInventory baubles = PlayerHandler.getPlayerBaubles(player);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = baubles.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof IPriestColorOverride)
                    return ((IPriestColorOverride) stack.getItem()).colorOverride(stack);
                else if (stack != null && stack.getItem() instanceof ICosmeticAttachable && ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack) != null) {
                    ItemStack cosmeticStack = ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack);
                    if (cosmeticStack != null && cosmeticStack.getItem() instanceof IPriestColorOverride)
                        return ((IPriestColorOverride) cosmeticStack.getItem()).colorOverride(cosmeticStack);
                }
            }
        }
        return -1;
    }

    public static int getColor(EntityPlayer player, int fallback) {
        int color = getColor(player);
        if (color == -1)
            return color;
        return fallback;
    }
}
