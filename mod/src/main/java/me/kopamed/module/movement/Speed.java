package me.kopamed.module.movement;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Speed extends Module {
    private float speed;
    public Speed() {
        super("Speed", "Allows you to go fast like lightning McQueen (kachaow)", true, false, Category.MOVEMENT);
        Galacticc.instance.settingsManager.rSetting(new Setting("Multiplier", this, 1.1, 0.01, 10, false));
    }

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent e) {
        if (e.player.onGround) {
            speed = (float) Galacticc.instance.settingsManager.getSettingByName(this, "Multiplier").getValDouble() / 10;
            e.player.capabilities.setPlayerWalkSpeed(speed);
        }
    }
}
