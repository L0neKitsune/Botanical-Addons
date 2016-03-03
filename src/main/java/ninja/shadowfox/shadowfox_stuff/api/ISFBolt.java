package ninja.shadowfox.shadowfox_stuff.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ninja.shadowfox.shadowfox_stuff.common.entity.EntityBolt;

/**
 * 1.8 mod
 * created on 2/20/16
 */
public interface ISFBolt {
    EntityBolt makeBolt(World world, EntityPlayer player, Float velocity);
}
