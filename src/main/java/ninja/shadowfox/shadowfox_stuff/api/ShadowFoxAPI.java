package ninja.shadowfox.shadowfox_stuff.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShadowFoxAPI {

    public static Map<String, CollisionEffect> collidingItemHashMap = new LinkedHashMap<String, CollisionEffect>();
    public static CollisionEffect fallbackTcl = new CollisionEffect("shadowfox_fallback",
            new CollisionEffect.OnImpactEvent() {
                @Override
                public void onImpact(Entity throwable, ItemStack itemStack, MovingObjectPosition movingObject) {

                }
            });


    public static CollisionEffect registerThrowable(CollisionEffect tcl) {
        collidingItemHashMap.put(tcl.getKey(), tcl);
        return tcl;
    }

    public static CollisionEffect getCollisionEventFromKey(String key) {
        return collidingItemHashMap.containsKey(key) ? collidingItemHashMap.get(key) : fallbackTcl;
    }
}
