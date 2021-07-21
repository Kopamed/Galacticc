package me.kopamed.galacticc.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

public class debian {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static List<EntityLivingBase> getTargets(int reach) {
        List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();

        for (Entity ent : mc.theWorld.loadedEntityList) {
            if (ent instanceof EntityLivingBase) {
                if (ent != mc.thePlayer && ent.getDistanceToEntity(mc.thePlayer) < reach){
                    targets.add((EntityLivingBase) ent);
                }
            }
        }

        return targets;
    }

    public static List<EntityLivingBase> sortByRange(List<EntityLivingBase> entities) {
        List<EntityLivingBase> targetsSorted = new ArrayList<EntityLivingBase>();

        while (targetsSorted.size() < entities.size()) {
            int index = 0;
            double smallestDistance = -1;
            for (int ind = 0; ind <= entities.size()-1;ind++) {
                EntityLivingBase elb = entities.get(ind);
                double distanceToPlayer = elb.getDistanceToEntity(mc.thePlayer);
                if (smallestDistance == -1) {
                    smallestDistance = distanceToPlayer;
                    index = ind;
                } else {
                    if (distanceToPlayer < smallestDistance) {
                        smallestDistance = distanceToPlayer;
                        index = ind;
                    }
                }
            }

            targetsSorted.add(entities.get(index));
        }

        return targetsSorted;
    }
}
