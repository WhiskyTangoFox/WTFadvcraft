package wtfadvcraft.items;

import wtfadvcraft.WTFadvcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class WTFItems extends Item {

	public WTFItems()
	{
		//this.setCreativeTab(getCreativeTab().tabMaterials);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
	this.itemIcon = iconRegister.registerIcon(WTFadvcraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}
}