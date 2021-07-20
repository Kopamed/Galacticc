package me.kopamed.galacticc.module.combat;

import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

// copied from raven b3
// @author Blowsy
public class DelayRemover extends Module {
    private Field cap = null;

    public DelayRemover() {
        super("Delay Remover", "Does not cap cps", false, false, Category.COMBAT);
    }

    @Override
    public void onEnabled() {
        try {
            this.cap = mc.getClass().getDeclaredField("field_71429_W");
        } catch (Exception var4) {
            try {
                this.cap = mc.getClass().getDeclaredField("leftClickCounter");
            } catch (Exception var3) {
            }
        }

        if (this.cap != null) {
            this.cap.setAccessible(true);
        } else {
            this.toggle();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }

            try {
                this.cap.set(mc, 0);
            } catch (Exception what3) {
                what3.printStackTrace();
            }
        }
    }
}
