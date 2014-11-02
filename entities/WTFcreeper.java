package wtfadvcraft.entities;

import wtfadvcraft.util.WTFexplosion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WTFcreeper extends EntityCreeper
{
	 /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;
    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;
    private int fuseTime = 30;
    /** Explosion radius for this creeper. */
    private int explosionRadius = 3;
    private static final String __OBFID = "CL_00001684";
	public WTFcreeper(World p_i1733_1_) {
		super(p_i1733_1_);
		// TODO Auto-generated constructor stub
	}

	@Override 
	public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.func_146078_ca())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("creeper.primed", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.func_146077_cc();
            }
        }

        super.onUpdate();
    }
	
	
	private void func_146077_cc()
	    {
		 /** Explosion radius for this creeper. */
		    int radius = 3;
		    float f = 1f;
		 if (!this.worldObj.isRemote)
	        {
	            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

	            if (this.getPowered())
	            {
	            	WTFexplosion.createExplosion(this, this.worldObj, posX, posY, posZ, (float)radius*2, f, true);
	            	
	            }
	            else
	            {
	            	WTFexplosion.createExplosion(this, this.worldObj, posX, posY, posZ, (float)radius, f, true);
	            }

	            this.setDead();
	        }
	    }	
	
   
}