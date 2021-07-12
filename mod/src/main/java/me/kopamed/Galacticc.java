package me.kopamed;

import me.kopamed.module.ModuleManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Galacticc.MODID, version = Galacticc.VERSION)
public class Galacticc
{
    public static final String MODID = "Galacticc";
    public static final String VERSION = "1.0";

    public ModuleManager moduleManager;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("Loading Client --------------------------------------------------------");
        moduleManager = new ModuleManager();
    }
}
