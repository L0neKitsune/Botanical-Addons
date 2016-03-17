package ninja.shadowfox.shadowfox_botany.api.item;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class ThrowableCollidingItem {
    String key;
    ItemStack stack;
    OnImpactEvent event;

    public ThrowableCollidingItem(String key, ItemStack stack, OnImpactEvent event) {
        this.key = key;
        this.stack = stack;
        this.event = event;
    }

    public void onImpact(EntityThrowable throwable, MovingObjectPosition movingObject) {
        event.onImpact(throwable, movingObject);
    }

    public String getKey() {
        return this.key;
    }

    public ItemStack getItemStack() {
        return this.stack;
    }

    public interface OnImpactEvent {
        void onImpact(EntityThrowable throwable, MovingObjectPosition movingObject);
    }
}
