package ninja.shadowfox.shadowfox_botany.api.item;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class ThrowableCollidingItem {
    String key;
    OnImpactEvent event;

    public ThrowableCollidingItem(String key, OnImpactEvent event) {
        this.key = key;
        this.event = event;
    }

    public void onImpact(EntityThrowable throwable, ItemStack stack, MovingObjectPosition movingObject) {
        event.onImpact(throwable, stack, movingObject);
    }

    public String getKey() {
        return this.key;
    }

    public interface OnImpactEvent {
        void onImpact(EntityThrowable throwable, ItemStack stack, MovingObjectPosition movingObject);
    }
}
