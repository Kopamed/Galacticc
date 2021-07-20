package me.kopamed.galacticc.module.misc;

import me.kopamed.galacticc.Galacticc;
import me.kopamed.galacticc.module.Category;
import me.kopamed.galacticc.module.Module;
import org.lwjgl.input.Keyboard;

public class SelfDestruct extends Module {
    public SelfDestruct() {
        super("Self destruct", "Mc stays here, client goes back to mumbai. Babai", false, false, Category.MISC);
        this.setKey(Keyboard.KEY_BACK);
    }

    @Override
    public void onEnabled() {
        Galacticc.instance.onDestruct();
    }
}
