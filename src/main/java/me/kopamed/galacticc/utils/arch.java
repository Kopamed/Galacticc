package me.kopamed.galacticc.utils;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class arch {
    public static ArrayList<Module> cp (ArrayList<Module> parent) {
        ArrayList<Module> finalArray = new ArrayList<Module>();
        for (Module m : parent) {
            finalArray.add(m);
        }
        return finalArray;
    }

    public static ArrayList<Module> longShort () {;
        ArrayList<Module> hackModules = cp(Galacticc.instance.moduleManager.modules);
        ArrayList<Module> finalList = new ArrayList<Module>();
        while (hackModules.size() != 0) {
            int longestIndex = 0;
            int longestLength = 0;
            for (int k = 0; k < hackModules.size(); k++) {
                Module m = hackModules.get(k);
                if (m.getName().length() > longestLength) {
                    longestIndex = k;
                    longestLength = m.getName().length();
                }
            }
            finalList.add(hackModules.get(longestIndex));
            hackModules.remove(longestIndex);
        }


        return finalList;
    }

    public static ArrayList<Module> abcList() {
        // boy i love bloat
        // im like microsoft, but on a budget because I don't add malware to my software
        ArrayList<Module> finalList = new ArrayList<Module>();
        ArrayList<String> modNames = modNamesPLS();
        Collections.sort(modNames, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        for (String str : modNames) {
            for (Module mod : Galacticc.instance.moduleManager.getModulesList()) {
                if (mod.getName().equalsIgnoreCase(str)) {
                    finalList.add(mod);
                }
            }
        }

        return finalList;
    }

    public static ArrayList<String> modNamesPLS () {
        ArrayList<String> shitMyHeadHurst = new ArrayList<String>();
        // sorry for the bloat and bad code i feel bad shit shit shit
        for (Module m : Galacticc.instance.moduleManager.getModulesList()) {
            shitMyHeadHurst.add(m.getName());
        }
        return shitMyHeadHurst;
    }

    public static ArrayList<Module> shortLong () {
        ArrayList<Module> longShort = longShort();
        ArrayList<Module> finalList = new ArrayList<Module>();
        for(int i=longShort.size()-1;i>=0;i--) {
            finalList.add(longShort.get(i));
        }
        return finalList;
    }
}
