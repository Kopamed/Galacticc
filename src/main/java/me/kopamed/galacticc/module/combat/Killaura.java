package me.kopamed.galacticc.module.combat;

import akka.actor.dsl.Creators;
import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.settings.Setting;
import me.kopamed.galacticc.utils.debian;
import me.kopamed.galacticc.utils.mint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Killaura extends Module {
    private long cps, reach;
    private boolean autoBlock;
    public Killaura() {
        super("Killaura", "Blatantly attacks enemies", true, false, Category.COMBAT);
        Setting cps = new Setting("CPS", this, 10, 0.1, 30, false);
        Setting reach = new Setting("Reach", this, 6, 1, 8, false);
        Setting autoBlock = new Setting("AutoBlock", this, false);

        Galacticc.instance.settingsManager.rSetting(cps);
        Galacticc.instance.settingsManager.rSetting(reach);
        Galacticc.instance.settingsManager.rSetting(autoBlock);
    }

    @SubscribeEvent
    public void onMotion(TickEvent.PlayerTickEvent e) {
        updateVals();
        if(e.phase != TickEvent.Phase.START || mc.thePlayer.isSpectator()) {
            return;
        }

        List<EntityLivingBase> targets = debian.getTargets(reach);
        targets = debian.sortByRange(targets);

        if (targets.isEmpty()) {
            return;
        }

        EntityLivingBase target = targets.get(0);
        ArrayList<EntityPlayer> players = debian.getPlayers(targets, reach);

        //e.player.setRotationYawHead(debian.getRotations(target)[0]);

        //e.player.rotationYaw = (debian.getRotations(target)[0]);
        //e.player.rotationPitch = (debian.getRotations(target)[1]);
        //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(debian.getRotations(target)[0], debian.getRotations(target)[1], mc.thePlayer.onGround));
                /*
        mc.thePlayer.rotationYaw = (debian.getRotations(target)[0]);
        mc.thePlayer.rotationPitch = (debian.getRotations(target)[1]);
        */
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ, debian.getRotations(target)[0], debian.getRotations(target)[1], mc.thePlayer.onGround));

        if(mint.hasTimeElapsed(1000 / cps, true) && !mc.thePlayer.isBlocking()) {
            mc.thePlayer.swingItem();
            if(autoBlock && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
                mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }

    public void updateVals() {
        this.cps = (long) Galacticc.instance.settingsManager.getSettingByName(this, "CPS") . getValDouble();
        this.reach = (long) Galacticc.instance.settingsManager.getSettingByName(this, "Reach") . getValDouble();
        this.autoBlock = Galacticc.instance.settingsManager.getSettingByName(this, "AutoBlock").getValBoolean();
    }
}
