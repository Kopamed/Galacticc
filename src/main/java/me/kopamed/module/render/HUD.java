package me.kopamed.module.render;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD extends Module {
    private boolean watermark, background;

    public HUD() {
        super("HUD", "Draws the module list on your screen", false, false, Category.RENDER);

        Setting waterMark = new Setting("Watermark", this, true);
        Setting background = new Setting("Background", this, true);

        Galacticc.instance.settingsManager.rSetting(waterMark);
        Galacticc.instance.settingsManager.rSetting(background);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (Galacticc.instance.destructed){
            return;
        }
        if (!egoe.type.equals(egoe.type.ALL)) {
            return;
        }

        watermark = Galacticc.instance.settingsManager.getSettingByName(this, "Watermark").getValBoolean();
        background = Galacticc.instance.settingsManager.getSettingByName(this, "Background").getValBoolean();
        // Renders active modules
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y = 4;

        if(watermark) {
            String waterMarkText = Galacticc.MODID + Galacticc.VERSION.toUpperCase();
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            fr.drawString(waterMarkText, sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - 1, y, 0xFF4500);
            y += fr.FONT_HEIGHT + 4;

            //if(background) {
              //  Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(waterMarkText) - 4, y, sr.getScaledWidth(), y + fr.FONT_HEIGHT, 0x90000000);
            //}
        }

        for (Module mod : Galacticc.instance.moduleManager.getModulesList()) {
            if (mod.visible && mod.isToggled()) {
                FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                fr.drawString(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 1, y, 0xFF4500);
                y += fr.FONT_HEIGHT;

                //if(background) {
                  //  Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 4, 4, sr.getScaledWidth(), 4 + fr.FONT_HEIGHT, 0x90000000);
                //}
            }
        }
    }
}
