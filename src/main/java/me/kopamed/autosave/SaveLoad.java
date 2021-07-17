package me.kopamed.autosave;

import com.ibm.icu.text.ArabicShaping;
import me.kopamed.Galacticc;
import me.kopamed.module.Module;
import me.kopamed.settings.Setting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    private File dir;
    private File dataFile;

    public SaveLoad() {
        dir = new File(Minecraft.getMinecraft().mcDataDir, Galacticc.MODID.toLowerCase());
        if (!dir.exists()) {
            dir.mkdir();
        }
        dataFile = new File(dir, "data.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        shjhdhj.println("this.loading lol");
        this.load();
    }

    public void save() {
        if (Galacticc.instance.destructed) {
            return;
        }
        ArrayList<String> toSave = new ArrayList<String>();

        for (Module mod : Galacticc.instance.moduleManager.getModulesList()) {
            toSave.add(Galacticc.MODID + "MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.visible + ":" + mod.getKey());
        }

        for (Setting set : Galacticc.instance.settingsManager.getSettings()) {
            if (set.isCheck()) {
                toSave.add(Galacticc.MODID + "SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
            }
            if (set.isCombo()) {
                toSave.add(Galacticc.MODID + "SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
            }
            if (set.isSlider()) {
                toSave.add(Galacticc.MODID + "SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
            }
        }

        try {
            PrintWriter printWriter = new PrintWriter(this.dataFile);
            for (String str : toSave) {
                printWriter.println(str);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (Galacticc.instance.destructed) {
            return;
        }
        shjhdhj.println("started loading");
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        shjhdhj.println("added to lines");

        for (String s : lines) {
            String[] args = s.split(":");
            if (s.toLowerCase().startsWith(Galacticc.MODID.toLowerCase() + "mod:")) {
                shjhdhj.println("lkine starts with mod");
                Module m = Galacticc.instance.moduleManager.getModule(args[1]);
                if (m != null) {
                    m.setToggled(Boolean.parseBoolean(args[2]));
                    m.visible = Boolean.parseBoolean(args[3]);
                    m.setKey(Integer.parseInt(args[4]));
                } else {shjhdhj.println("m is null fuck line 82");}
            } else if (s.toLowerCase().startsWith(Galacticc.MODID.toLowerCase() + "set:")) {
                shjhdhj.println("line starts with set");
                Module m = Galacticc.instance.moduleManager.getModule(args[2]);
                if (m != null) {
                    Setting set = Galacticc.instance.settingsManager.getSettingByName(m, args[1]);
                    if (set != null) {
                        if (set.isCheck()) {
                            set.setValBoolean(Boolean.parseBoolean(args[3]));
                        }
                        if (set.isCombo()) {
                            set.setValString(args[3]);
                        }
                        if (set.isSlider()) {
                            set.setValDouble(Double.parseDouble(args[3]));
                        }
                    } else {shjhdhj.println("s is null fuck line 91");}
                }else {shjhdhj.println("m is null fuck line 89");}
            } else {
                shjhdhj.println("idfk how to read");
            }
        }
    }
}
