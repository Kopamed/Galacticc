package me.kopamed.module.render;

import me.kopamed.module.Category;
import me.kopamed.module.Module;

public class Fullbright extends Module {
    private float defGamma;
    public Fullbright() {
        super("Fullbright", "Allows you to see in the dark", false, false, Category.RENDER);
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        defGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 10F;
    }

    @Override
    public void onDisabled() {
        super.onDisabled();
        mc.gameSettings.gammaSetting = defGamma;
    }
}
