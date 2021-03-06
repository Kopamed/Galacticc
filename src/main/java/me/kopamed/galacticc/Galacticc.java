package me.kopamed.galacticc;


import me.kopamed.galacticc.command.CommandManager;
import me.kopamed.galacticc.config.SaveLoad;
import me.kopamed.galacticc.clickgui.ClickGui;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.module.ModuleManager;
import me.kopamed.galacticc.module.misc.SafeSettings;
import me.kopamed.galacticc.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Galacticc
{
    public static final String MODID = "Galacticc";
    public static final String VERSION = "r1";
    public static String prefix = ".";

    public static Galacticc instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    public SaveLoad saveLoad;
    public CommandManager commandManager;
    public ArrayList<EntityPlayer> bots = new ArrayList<EntityPlayer>();

    public boolean destructed = false;

    public void init()
    {
        // following 5 lines must be in this order or java has a stroke and dies
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        moduleManager.addModule(new SafeSettings());
        saveLoad = new SaveLoad();
        clickGui = new ClickGui();
        commandManager = new CommandManager();
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

    public ArrayList<EntityPlayer> getBots() {
        return this.bots;
    }

    public void updateBots(List<EntityPlayer> e) {
        this.bots = new ArrayList<EntityPlayer>();
        for (EntityPlayer entityPlayer : e) {
            this.bots.add(entityPlayer);
        }
    }
}
