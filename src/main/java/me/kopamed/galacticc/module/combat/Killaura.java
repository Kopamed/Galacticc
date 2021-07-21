package me.kopamed.galacticc.module.combat;

import akka.actor.dsl.Creators;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import me.kopamed.galacticc.utils.debian;
import me.kopamed.galacticc.utils.mint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class Killaura extends Module {
    private long cps;
    public Killaura() {
        super("Killaura", "Blatantly attacks enemies", true, false, Category.COMBAT);
        this.cps = 10;
    }

    @SubscribeEvent
    public void onMotion(TickEvent.PlayerTickEvent e) {
        if(e.phase != TickEvent.Phase.START) {
            return;
        }

        List<EntityLivingBase> targets = debian.getTargets(4);
        targets = debian.sortByRange(targets);

        if (targets.isEmpty()) {
            return;
        }

        EntityLivingBase target = targets.get(0);

        if(mint.hasTimeElapsed(1000 / cps, true)) {
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }
}
