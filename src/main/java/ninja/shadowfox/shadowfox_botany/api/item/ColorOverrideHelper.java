package ninja.shadowfox.shadowfox_botany.api.item;

import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.item.ICosmeticAttachable;

import java.awt.*;

public class ColorOverrideHelper {
    public static int getColor(EntityPlayer player) {
        int r = 0;
        int b = 0;
        int g = 0;
        int colors = 0;
        if (player != null) {
            IInventory baubles = PlayerHandler.getPlayerBaubles(player);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = baubles.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof IPriestColorOverride) {
                    int stackColor = ((IPriestColorOverride) stack.getItem()).colorOverride(stack);
                    if (stackColor != -1) {
                        Color color = new Color(stackColor);
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        colors++;
                    }
                } else if (stack != null && stack.getItem() instanceof ICosmeticAttachable && ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack) != null) {
                    ItemStack cosmeticStack = ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack);
                    if (cosmeticStack != null && cosmeticStack.getItem() instanceof IPriestColorOverride) {
                        int stackColor = ((IPriestColorOverride) cosmeticStack.getItem()).colorOverride(cosmeticStack);
                        if (stackColor != -1) {
                            Color color = new Color(stackColor);
                            r += color.getRed();
                            g += color.getGreen();
                            b += color.getBlue();
                            colors++;
                        }
                    }
                }
            }
        }
        return colors == 0 ? -1 : new Color(r / colors, g / colors, b / colors).getRGB();
    }

    public static int getColor(EntityPlayer player, int fallback) {
        int color = getColor(player);
        if (color != -1)
            return color;
        return fallback;
    }
}
