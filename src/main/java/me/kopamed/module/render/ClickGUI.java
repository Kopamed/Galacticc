package me.kopamed.module.render;

import me.kopamed.Galacticc;
import me.kopamed.module.Category;
import me.kopamed.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI(){
        super("ClickGUI", "Allows you to manage modules", false, true, Category.RENDER);
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        mc.displayGuiScreen(Galacticc.instance.clickGui);
        this.setToggled(false);
    }
}
