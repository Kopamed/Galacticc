package me.kopamed.module;

import me.kopamed.module.combat.AutoClicker;
import me.kopamed.module.combat.Velocity;
import me.kopamed.module.movement.Fly;
import me.kopamed.module.movement.Sprint;
import me.kopamed.module.render.ClickGUI;
import me.kopamed.module.render.HUD;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules;

    public ModuleManager(){
        (modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGUI());
        this.modules.add(new HUD());
        this.modules.add(new Sprint());
        this.modules.add(new AutoClicker());
        this.modules.add(new Velocity());
        this.modules.add(new Fly());
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
