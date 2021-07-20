//Noi bypass lol sry vbabay ur going back to mumbay
package me.kopamed.galacticc.module.movement;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;

public class Fly extends Module {
    public Fly(){
    super("Fly", "nyooom", true, false, Category.MOVEMENT);
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        if (mc.thePlayer != null) {
            if (!mc.thePlayer.capabilities.isFlying) {
                mc.thePlayer.jump();
                mc.thePlayer.capabilities.allowFlying = true;
                mc.thePlayer.capabilities.isFlying = true;
            }
        }
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        if (mc.thePlayer != null) {
            if (mc.thePlayer.capabilities.allowFlying) {
                mc.thePlayer.capabilities.allowFlying = false;
                mc.thePlayer.capabilities.isFlying = false;
            }
        }
    }

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}
        Set<Double> hash_Set = new HashSet<Double>();
        if (e.player.height == 0) {
            this.toggle();
        }
    }
}
