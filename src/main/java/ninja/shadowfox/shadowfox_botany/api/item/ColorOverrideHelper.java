package ninja.shadowfox.shadowfox_botany.api.item;

import baubles.common.lib.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.item.ICosmeticAttachable;

import java.awt.*;

public class ColorOverrideHelper {
    public static int getColor(EntityPlayer player) {
        Color color = null;
        if (player != null) {
            IInventory baubles = PlayerHandler.getPlayerBaubles(player);
            for (int i = 0; i < 4; i++) {
                ItemStack stack = baubles.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof IPriestColorOverride) {
                    int stackColor = ((IPriestColorOverride) stack.getItem()).colorOverride(stack);
                    if (stackColor != -1) {
                        if (color == null)
                            color = new Color(stackColor);
                        else
                            color = mixColors(color, new Color(stackColor));
                    }
                } else if (stack != null && stack.getItem() instanceof ICosmeticAttachable && ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack) != null) {
                    ItemStack cosmeticStack = ((ICosmeticAttachable) stack.getItem()).getCosmeticItem(stack);
                    if (cosmeticStack != null && cosmeticStack.getItem() instanceof IPriestColorOverride) {
                        int stackColor = ((IPriestColorOverride) cosmeticStack.getItem()).colorOverride(cosmeticStack);
                        if (stackColor != -1) {
                            if (color == null)
                                color = new Color(stackColor);
                            else
                                color = mixColors(color, new Color(stackColor));
                        }
                    }
                }
            }
        }
        return color == null ? -1 : color.getRGB();
    }

    private static Color mixColors(Color c1, Color c2) {
        int r = (c1.getRed() + c2.getRed()) / 2;
        int g = (c1.getGreen() + c2.getGreen()) / 2;
        int b = (c1.getBlue() + c2.getBlue()) / 2;

        return new Color(r, g, b);
    }

    public static int getColor(EntityPlayer player, int fallback) {
        int color = getColor(player);
        if (color != -1)
            return color;
        return fallback;
    }
}
