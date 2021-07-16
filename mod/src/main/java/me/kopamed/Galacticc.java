package me.kopamed;


import me.kopamed.autosave.SaveLoad;
import me.kopamed.clickgui.ClickGui;
import me.kopamed.module.Module;
import me.kopamed.module.ModuleManager;
import me.kopamed.module.misc.SafeSettings;
import me.kopamed.settings.Setting;
import me.kopamed.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
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
    public SaveLoad saveLoad;

    public boolean destructed = false;

    public void init()
    {
        // following 5 lines must be in this order or java has a stroke and dies
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        moduleManager.addModule(new SafeSettings());
        clickGui = new ClickGui();
        saveLoad = new SaveLoad();
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

    public void onDestruct() {
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        }
        destructed = true;
        MinecraftForge.EVENT_BUS.unregister(this);
        for (int k = 0; k < this.moduleManager.modules.size(); k++) {
            Module m = this.moduleManager.modules.get(k);
            MinecraftForge.EVENT_BUS.unregister(m);
            this.moduleManager.getModulesList().remove(m);
        }
        this.moduleManager = null;
        this.clickGui = null;
    }
}
