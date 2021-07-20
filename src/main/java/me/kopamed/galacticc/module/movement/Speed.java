//Work in proggress

package me.kopamed.galacticc.module.movement;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Speed extends Module {
    private float speed;
    private float defSpeed;
    public Speed() {
        super("Speed", "Allows you to go fast like lightning McQueen (kachaow)", true, false, Category.MOVEMENT);
        Galacticc.instance.settingsManager.rSetting(new Setting("Multiplier", this, 1.1, 0.01, 10, false));
    }

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}
        if (e.player.onGround) {
            speed = (float) Galacticc.instance.settingsManager.getSettingByName(this, "Multiplier").getValDouble() / 10;
            //fuck
        }
    }

    @Override
    public void onEnabled() {
        mc.thePlayer.setVelocity(speed, 1, speed);
        super.onEnabled();
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        mc.thePlayer.setVelocity(1, 1, 1);
    }
}
