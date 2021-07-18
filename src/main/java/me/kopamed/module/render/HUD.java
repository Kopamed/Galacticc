package me.kopamed.module.render;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import me.kopamed.utils.arch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
    private boolean watermark, background, textShadow;
    private int margin;
    private int waterMarkMargin, topOffSet, rightOffSet, miniboxWidth;
    private int modColor = 0xFFFFFF;
    private int wmColor = 0xFF4500;
    private String sortMode;
    public ArrayList<Module> modList;

    public HUD() {
        super("HUD", "Draws the module list on your screen", false, false, Category.RENDER);

        ArrayList<String> sort = new ArrayList<String>();
        sort.add("Length long > short");
        sort.add("Length short > long");
        sort.add("Alphabet");
        sort.add("idfc");

        Setting waterMark = new Setting("Watermark", this, true);
        Setting background = new Setting("Background", this, false);
        Setting textShadow = new Setting("Text Shadow", this, true);
        Setting arrayListSort = new Setting("Arraylist sort", this, "Length long > short", sort);

        Galacticc.instance.settingsManager.rSetting(arrayListSort);
        Galacticc.instance.settingsManager.rSetting(waterMark);
        Galacticc.instance.settingsManager.rSetting(background);
        Galacticc.instance.settingsManager.rSetting(textShadow);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (Galacticc.instance.destructed){
            return;
        }
        if (!egoe.type.equals(egoe.type.ALL)) {
            return;
        }

        sortMode = Galacticc.instance.settingsManager.getSettingByName(this, "Arraylist sort").getValString();
        watermark = Galacticc.instance.settingsManager.getSettingByName(this, "Watermark").getValBoolean();
        background = Galacticc.instance.settingsManager.getSettingByName(this, "Background").getValBoolean();
        textShadow = Galacticc.instance.settingsManager.getSettingByName(this, "Text Shadow").getValBoolean();
        // Renders active modules
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());


        margin = 2;
        waterMarkMargin = 3;
        topOffSet = 4;
        rightOffSet = 4;
        miniboxWidth = 1;


        if(watermark) {
            String waterMarkText = Galacticc.MODID + Galacticc.VERSION.toUpperCase();
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            if(background) {
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin*2 - rightOffSet, topOffSet,sr.getScaledWidth() - rightOffSet, topOffSet + waterMarkMargin * 2 + fr.FONT_HEIGHT, 0x90000000);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin * 2 - rightOffSet - miniboxWidth, topOffSet,sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - waterMarkMargin * 2 - rightOffSet, topOffSet + waterMarkMargin * 2 + fr.FONT_HEIGHT, 0xffff4500);
            }
            if (textShadow) {
                fr.drawStringWithShadow(waterMarkText, sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - rightOffSet - waterMarkMargin, topOffSet  + waterMarkMargin, wmColor);
            } else {
                fr.drawString(waterMarkText, sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - rightOffSet - waterMarkMargin, topOffSet  + waterMarkMargin, wmColor);
            }

            topOffSet += fr.FONT_HEIGHT + waterMarkMargin*2;
        }


        modList = Galacticc.instance.moduleManager.getModulesList();

        if (sortMode.equalsIgnoreCase("Length long > short")) {
            modList = arch.longShort();
        } else if (sortMode.equalsIgnoreCase("Length short > long")) {
            modList = arch.shortLong();
        } else if (sortMode.equalsIgnoreCase("Alphabet")) {
            modList = arch.abcList();
        }


        for (Module mod : modList) {
            if (mod.visible && mod.isToggled()) {
                FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                if(background) {
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet, topOffSet,sr.getScaledWidth() - rightOffSet, topOffSet + margin * 2 + fr.FONT_HEIGHT, 0x90000000);
                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet - miniboxWidth, topOffSet,sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - margin * 2 - rightOffSet, topOffSet + margin * 2 + fr.FONT_HEIGHT, 0xffff4500);
                }
                if (textShadow) {
                    fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - rightOffSet - margin, topOffSet  + margin, modColor);
                } else {
                    fr.drawString(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - rightOffSet - margin, topOffSet  + margin, modColor);
                }

                topOffSet += fr.FONT_HEIGHT + margin*2;
            }
        }
    }
}
