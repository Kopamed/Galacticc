package me.kopamed.module.misc;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class SafeSettings extends Module {
    boolean mode;
    boolean allow;
    public SafeSettings() {
        super("Safe Settings", "Makes sure that you don't enable a setting by accident", false, false, Category.MISC);

        ArrayList<String> options = new ArrayList<String>();
        options.add("tick = disallow");
        options.add("tick = allow");

        Setting allow = new Setting("Mode", this, "tick = disallow", options);
        Galacticc.instance.settingsManager.rSetting(allow);

        for (Module m : Galacticc.instance.moduleManager.getModulesList()) {
            if (m.isDetectable()) {
                Setting setting = new Setting(m.getName(), this, false);
                Galacticc.instance.settingsManager.rSetting(setting);
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        for (Module m : Galacticc.instance.moduleManager.getModulesList()) {
            if (m.isDetectable() && m.isToggled()) {
                if (Galacticc.instance.settingsManager.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("tick = disallow")){
                    mode = false;
                } else {
                    mode = true;
                }
                allow = Galacticc.instance.settingsManager.getSettingByName(this, m.getName()).getValBoolean() && mode;
                if (!allow) {
                    m.setToggled(false);
                }
            }
        }
    }
}
