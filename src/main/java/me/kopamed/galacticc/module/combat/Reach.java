package me.kopamed.galacticc.module.combat;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Reach extends Module {
    public Reach() {
        super("Reach", "You have a good gaming chait", true, false, Category.COMBAT);

        Setting minReach = new Setting("Min Reach", this, 3, 3, 12, false);
        Setting maxReach = new Setting("Max Reach", this, 3.2, 3, 12, false);

        Galacticc.instance.settingsManager.rSetting(minReach);
        Galacticc.instance.settingsManager.rSetting(maxReach);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        
    }
}
