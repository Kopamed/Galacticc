package me.kopamed.module.render;

import me.kopamed.module.Category;
import me.kopamed.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI(){
        super("ClickGUI", "Allows you to manage modules", false, false, Category.RENDER);
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
    }
}
