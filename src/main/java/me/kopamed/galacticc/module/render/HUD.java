package me.kopamed.galacticc.module.render;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import me.kopamed.galacticc.utils.arch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
    private boolean watermark, background, textShadow, active;
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

        Setting margin = new Setting("Module padding", this, 2, 0, 10, true);
        Setting waterMarkMargin = new Setting("Watermark padding", this, 3, 0, 10, true);
        Setting miniboxWidth = new Setting("Mini box width", this, 1, 0, 10, true);
        Setting topOffSet = new Setting("HUD top offset", this, 4, 0, 10, true);
        Setting rightOffSet = new Setting("HUD right offset", this, 4, 0, 10, true);

        Galacticc.instance.settingsManager.rSetting(arrayListSort);
        Galacticc.instance.settingsManager.rSetting(waterMark);
        Galacticc.instance.settingsManager.rSetting(background);
        Galacticc.instance.settingsManager.rSetting(textShadow);

        Galacticc.instance.settingsManager.rSetting(margin);
        Galacticc.instance.settingsManager.rSetting(waterMarkMargin);
        Galacticc.instance.settingsManager.rSetting(miniboxWidth);
        Galacticc.instance.settingsManager.rSetting(topOffSet);
        Galacticc.instance.settingsManager.rSetting(rightOffSet);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (Galacticc.instance.destructed){
            return;
        }

        if (egoe.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS || !active) {
            return;
        }

        sortMode = Galacticc.instance.settingsManager.getSettingByName(this, "Arraylist sort").getValString();
        watermark = Galacticc.instance.settingsManager.getSettingByName(this, "Watermark").getValBoolean();
        background = Galacticc.instance.settingsManager.getSettingByName(this, "Background").getValBoolean();
        textShadow = Galacticc.instance.settingsManager.getSettingByName(this, "Text Shadow").getValBoolean();
        // Renders active modules
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());


        margin = (int) Galacticc.instance.settingsManager.getSettingByName(this, "Module padding").getValDouble();
        waterMarkMargin = (int) Galacticc.instance.settingsManager.getSettingByName(this, "Watermark padding").getValDouble();
        topOffSet = (int) Galacticc.instance.settingsManager.getSettingByName(this, "HUD top offset").getValDouble();;
        rightOffSet = (int) Galacticc.instance.settingsManager.getSettingByName(this, "HUD right offset").getValDouble();;
        miniboxWidth = (int) Galacticc.instance.settingsManager.getSettingByName(this, "Mini box width").getValDouble();;

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

    @Override
    public void onEnabled() {
        super.onEnabled();
        this.active = true;
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        this.active = false;
    }
}
