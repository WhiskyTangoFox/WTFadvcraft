package wtfadvcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import wtfadvcraft.WTFadvcraft;
import wtfadvcraft.entities.WTFprimedTNT;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class WTFtnt extends BlockTNT{

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;	
	 @SideOnly(Side.CLIENT)
	 public void registerBlockIcons(IIconRegister iconRegister)
		{
			this.blockIcon = iconRegister.registerIcon("minecraft:tnt_side");
			this.iconTop = iconRegister.registerIcon("minecraft:tnt_top");
			this.iconBottom = iconRegister.registerIcon("minecraft:tnt_bottom");
		}
	    @SideOnly(Side.CLIENT)
	    public IIcon getIcon(int side, int p_149691_2_)
	    {
	        return side == 0 ? this.iconBottom : (side == 1 ? this.iconTop : this.blockIcon);
	    }
	 
	@Override
    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
    {
        if (!p_149723_1_.isRemote)
        {
            WTFprimedTNT entitytntprimed = new WTFprimedTNT(p_149723_1_, (double)((float)p_149723_2_ + 0.5F), (double)((float)p_149723_3_ + 0.5F), (double)((float)p_149723_4_ + 0.5F), p_149723_5_.getExplosivePlacedBy());
            entitytntprimed.fuse = p_149723_1_.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            p_149723_1_.spawnEntityInWorld(entitytntprimed);
        }
    }

	
	@Override
	public void func_150114_a(World p_150114_1_, int p_150114_2_, int p_150114_3_, int p_150114_4_, int p_150114_5_, EntityLivingBase p_150114_6_)
	    {
	        if (!p_150114_1_.isRemote)
	        {
	            if ((p_150114_5_ & 1) == 1)
	            {
	            	WTFprimedTNT entitytntprimed = new WTFprimedTNT(p_150114_1_, (double)((float)p_150114_2_ + 0.5F), (double)((float)p_150114_3_ + 0.5F), (double)((float)p_150114_4_ + 0.5F), p_150114_6_);
	                p_150114_1_.spawnEntityInWorld(entitytntprimed);
	                p_150114_1_.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
	            }
	        }
	    }
}
