package me.kopamed.module.movement;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.text.JTextComponent;

public class Sprint extends Module {
    public Sprint(){
        super("Sprint", "Always hold down the sprint key", false, false, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }
}
