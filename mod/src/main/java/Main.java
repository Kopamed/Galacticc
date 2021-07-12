import me.kopamed.Galacticc;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Galacticc.MODID, version = Galacticc.VERSION)
public class Main {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        Galacticc.instance = new Galacticc();
        Galacticc.instance.init();
    }
}
