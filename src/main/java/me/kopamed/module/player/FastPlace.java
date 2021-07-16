//still working on it

package me.kopamed.module.player;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.lang.reflect.Field;

public class FastPlace extends Module {
    private int delay;
    private boolean onlyBlocks;
    private Field delayTimer;

    public FastPlace() {
        super("FastPlace", "Places blocks faster", false, false, Category.PLAYER);

        Setting delay = new Setting("Delay", this, 2, 0, 20, true);
        Setting onlyBlocks = new Setting("Only Blocks", this, true);
        Galacticc.instance.settingsManager.rSetting(delay);
        Galacticc.instance.settingsManager.rSetting(onlyBlocks);

        try {
            delayTimer = mc.getClass().getDeclaredField("field_71467_ac");
        } catch (Exception exc1) {
            try {
                delayTimer = mc.getClass().getDeclaredField("rightClickDelayTimer");
            } catch (Exception exc2) {}
        }

        if (delayTimer != null) {
            delayTimer.setAccessible(true);
        }
    }

    @SubscribeEvent
    public void PlayerTickEvent(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}
        delay = (int) Galacticc.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
        onlyBlocks = Galacticc.instance.settingsManager.getSettingByName(this, "Only Blocks").getValBoolean();

        if (e.phase == TickEvent.Phase.END) {
            if (onlyBlocks) {
                ItemStack item = e.player.getHeldItem();
                if (item == null || !(item.getItem() instanceof ItemBlock)) {
                    return;
                }
            }

            try {
                if (delay == 0) {
                    delayTimer.set(mc, delay);
                } else {
                    if (delay == 4) {
                        return;
                    }

                    int currentDelay = delayTimer.getInt(mc);
                    if (currentDelay == 4) {
                        delayTimer.set(mc, delay);
                    }
                }
            } catch (Exception no) {
                //Well fuck
                System.out.println("No no fastplace for u LLLLL");
                this.toggle();
            }

        }
    }

    @Override
    public void onEnabled() {
        if (delayTimer == null) {
            this.toggle();
        }
        super.onEnabled();
    }
}
