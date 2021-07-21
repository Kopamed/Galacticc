package me.kopamed.galacticc.module.combat;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Reach extends Module {
    private double blockReachDistance, minReach, maxReach;
    private float partialTick;

    public Reach() {
        super("Reach", "You have a good gaming chait", true, false, Category.COMBAT);

        Setting minReach = new Setting("Min Reach", this, 3, 3, 12, false);
        Setting maxReach = new Setting("Max Reach", this, 3.2, 3, 12, false);

        Setting blockReachDistance = new Setting("brd", this, 3, 0, 6, false);
        Setting partialTicks = new Setting("pt", this, 1, 0, 20, false);

        Galacticc.instance.settingsManager.rSetting(minReach);
        Galacticc.instance.settingsManager.rSetting(maxReach);

        Galacticc.instance.settingsManager.rSetting(blockReachDistance);
        Galacticc.instance.settingsManager.rSetting(partialTicks);
    }

    @SubscribeEvent
    public void onRenderGameOverLay(RenderLivingEvent e) {
        //if (e.entity. instanceof  mc.thePlayer)
        //HO=LY SHIT RENDERLIVINGEVENT IS AN ACTUAL GOLDMINE
        if (e.entity instanceof EntityPlayer) {
            System.out.println("Pog, we are rendering " + e.entity.getName() + " " + e.entity.getCustomNameTag() + " with health " + e.entity.getHealth());
        }
    }
}
