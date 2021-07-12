package me.kopamed;


import me.kopamed.clickgui.ClickGui;
import me.kopamed.module.Module;
import me.kopamed.module.ModuleManager;
import me.kopamed.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class Galacticc
{
    public static final String MODID = "Galacticc";
    public static final String VERSION = "r1";

    public static Galacticc instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;

    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        moduleManager = new ModuleManager();
        settingsManager = new SettingsManager();
        clickGui = new ClickGui();
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null)
            return;
        try{
            if (Keyboard.isCreated()) {
                if (Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if (keyCode <= 0)
                        return;
                    for (Module m : moduleManager.getModulesList()) {
                        if (m.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                        }
                    }
                }
            }
        } catch (Exception q) {
            q.printStackTrace();
        }
    }
}
