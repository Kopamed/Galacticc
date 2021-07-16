package me.kopamed.module.render;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD extends Module {

    public HUD() {
        super("HUD", "Draws the module list on your screen", false, false, Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (Galacticc.instance.destructed){
            return;
        }
        if (!egoe.type.equals(egoe.type.CROSSHAIRS)) {
            return;
        }


        // Renders active modules
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y = 2;
        for (Module mod : Galacticc.instance.moduleManager.getModulesList()) {
            if (mod.visible && mod.isToggled()) {
                FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                fr.drawString(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 1, y, 0xFF4500);
                y += fr.FONT_HEIGHT;
            }
        }
    }
}
