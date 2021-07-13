//Noi bypass lol sry vbabay ur going back to mumbay
package me.kopamed.module.movement;

import me.kopamed.module.Category;
import me.kopamed.module.Module;

public class Fly extends Module {
    public Fly(){
    super("Fly", "nyooom", true, false, Category.MOVEMENT);
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        if (!mc.thePlayer.capabilities.isFlying) {
            mc.thePlayer.jump();
            mc.thePlayer.capabilities.allowFlying = true;
            mc.thePlayer.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        if (mc.thePlayer.capabilities.allowFlying) {
            mc.thePlayer.capabilities.allowFlying = false;
            mc.thePlayer.capabilities.isFlying = false;
        }
    }
}
