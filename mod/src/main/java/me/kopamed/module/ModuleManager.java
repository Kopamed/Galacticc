package me.kopamed.module;

import me.kopamed.module.render.ClickGUI;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules;

    public ModuleManager(){
        (modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGUI());
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesList() {
        return this.modules;
    }

    public ArrayList<Module> getModulesInCategory(Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : this.modules) {
            if(m.getCategory() == c){
                mods.add(m);
            }
        }
        return mods;
    }

    public void addModule(Module m){
        this.modules.add(m);
    }
}
