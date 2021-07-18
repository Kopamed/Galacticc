// also no bypass. hypixel watchdog is too cool for this shit
// i hate dogs btw
package me.kopamed.module.player;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "You take zero fall damage", true, false, Category.PLAYER);
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("Vanilla");
        modes.add("Waterbucket");
        Galacticc.instance.settingsManager.rSetting(new Setting("Mode", this, "Vanilla", modes));;
    }

    @SubscribeEvent
    public void TickEvent(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}

        if (e.phase == TickEvent.Phase.START) {
            if (mc.thePlayer.fallDistance > 3) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
