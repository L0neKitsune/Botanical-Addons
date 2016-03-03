package ninja.shadowfox.shadowfox_stuff.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class CollisionEffect {
    String key;
    OnImpactEvent event;

    public CollisionEffect(String key, OnImpactEvent event) {
        this.key = key;
        this.event = event;
    }

    public void onImpact(Entity throwable, ItemStack stack, MovingObjectPosition movingObject) {
        event.onImpact(throwable, stack, movingObject);
    }

    public String getKey() {
        return this.key;
    }

    public interface OnImpactEvent {
        void onImpact(Entity throwable, ItemStack stack, MovingObjectPosition movingObject);
    }
}
