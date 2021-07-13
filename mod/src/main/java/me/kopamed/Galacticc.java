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
        // following 5 lines must be in this order or java has a stroke and dies
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        clickGui = new ClickGui();
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        Minecraft mc = Minecraft.getMinecraft();
        //make sure that we are in a game
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        //Basically get the key id, go through the list of modules
        //If module key is equal to the one pressed than we toggle the modules
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
            //In case java shits itself ft. my code
        } catch (Exception q) {
            q.printStackTrace();
        }
    }
}
