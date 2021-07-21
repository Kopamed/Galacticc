package me.kopamed.galacticc.module.render;

/// NOT FINUSHesd


import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class PlayerESP extends Module {

    public PlayerESP() {
        super("Player ESP", "Renders players in a dif way", false, false, Category.RENDER);
    }

    @Override
    public void onEnabled() {
        for(int k = 0; k < mc.theWorld.playerEntities.size(); k++) {
            EntityPlayer ent = mc.theWorld.playerEntities.get(k);
            if (!ent.getName().equalsIgnoreCase(mc.thePlayer.getName())) {
                for (EntityPlayer bot : Galacticc.instance.getBots()) {
                    if (ent.getName().equalsIgnoreCase(bot.getName())) {
                        //System.out.println("Rendering BOT "  + ent.getDisplayNameString() + " with cutoms nametag " + ent.getCustomNameTag() + " with health " + ent.getHealth());
                        ent.setCustomNameTag(ent.getCustomNameTag() + "[" + ent.getHealth() + "]" + "[BOT}");
                    } else {
                        ent.setCustomNameTag(ent.getCustomNameTag() + "[" + ent.getHealth() + "]");
                    }
                }
            }
        }
    }
}
