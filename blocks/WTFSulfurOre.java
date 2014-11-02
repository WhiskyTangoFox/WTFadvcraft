package wtfadvcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import wtfadvcraft.WTFadvcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WTFSulfurOre extends BlockOre {
		public WTFSulfurOre() 
		{
			super ();
			this.setHardness(3.0F);
			this.setResistance(5.0F);
			//this.setCreativeTab(getCreativeTabToDisplayOn().tabBlock);
		}

		@SideOnly(Side.CLIENT)
		public void registerBlockIcons(IIconRegister iconRegister)
		{
			this.blockIcon = iconRegister.registerIcon(WTFadvcraft.modid + ":" + this.getUnlocalizedName().substring(5));
		}
	    @Override
	    public Item getItemDropped(int metadata, Random random, int fortune) {
	        return WTFadvcraft.itemUnrefinedSulfur;
	    }
		@Override
		public int quantityDropped(Random par1Random)
		    {
		        return par1Random.nextInt(3) + 1;
		    }
}
