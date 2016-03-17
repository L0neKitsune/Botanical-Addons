package ninja.shadowfox.shadowfox_botany.api.item;

import net.minecraft.item.ItemStack;

/**
 * @author WireSegal
 *         Created at 8:44 PM on 1/11/16.
 *         <p>
 *         An Item interface that allows the mod author to blacklist a stack from entering the Botanist's Toolbelt.
 */
public interface IToolbeltBlacklisted {
    /**
     * @param stack The stack to check.
     * @return Whether the stack is allowed in the Botanist's Toolbelt.
     */
    boolean allowedInToolbelt(ItemStack stack);
}
