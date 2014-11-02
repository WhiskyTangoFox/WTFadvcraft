package wtfadvcraft.proxy;


import wtfadvcraft.entities.WTFprimedTNT;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;

public class ClientProxy extends CommonProxy {

	
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(WTFprimedTNT.class, new RenderTNTPrimed());
	}
}