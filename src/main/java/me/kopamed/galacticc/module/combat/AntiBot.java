package me.kopamed.galacticc.module.combat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Ordering;
import ibxm.Player;
import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class AntiBot extends Module {
    public static List<EntityPlayer> bots = new ArrayList<EntityPlayer>();
    public AntiBot() {
        super("AntiBot", "Stops you from hitting bots", false, false, Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (Galacticc.instance.destructed) {return;}
        for(int k = 0; k < mc.theWorld.playerEntities.size(); k++) {
            EntityPlayer ent = mc.theWorld.playerEntities.get(k);
            List<EntityPlayer> tabList = this.getTabPlayerList();
            if (!bots.contains(ent) && !tabList.contains(ent)){
                bots.add(ent);
                continue;
            } else if (bots.contains(ent) && tabList.contains(ent)) {
                bots.remove(ent);
            }
        }

        Galacticc.instance.updateBots(bots);
    }

    private List<EntityPlayer> getTabPlayerList() {
        final List<EntityPlayer> list;
        (list = new ArrayList<EntityPlayer>()).clear();
        Ordering<NetworkPlayerInfo> field_175252_a = field_175252_a();
        if  (field_175252_a == null) {
            return list;
        }
        final List players = field_175252_a.sortedCopy(mc.thePlayer.sendQueue.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {
                continue;
            }
            list.add(mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }

    private Ordering<NetworkPlayerInfo> field_175252_a() {
        try {
            final Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
            final Field f = c.getField("field_175252_a");
            f.setAccessible(true);
            return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);
        } catch (Exception e){return null;}
    }
}
