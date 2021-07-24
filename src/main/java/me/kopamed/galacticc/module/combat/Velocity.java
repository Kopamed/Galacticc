package me.kopamed.galacticc.module.combat;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Changes the amount of knockback you take", false, false, Category.COMBAT);
        Galacticc.instance.settingsManager.rSetting(new Setting("Horizontal", this, 92, 0, 200, true));
        Galacticc.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 200, true));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (Galacticc.instance.destructed) {return;}
        if (mc.thePlayer == null) {
            return;
        }
        float horizontal = (float)Galacticc.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        float vertical = (float)Galacticc.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();

        if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
            mc.thePlayer.motionX *= horizontal / 100;
            mc.thePlayer.motionY *= vertical / 100;
            mc.thePlayer.motionZ *= horizontal / 100;
        }
    }
}
