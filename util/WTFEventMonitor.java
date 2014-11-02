package wtfadvcraft.util;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import scala.Int;
import wtfadvcraft.WTFadvcraft;
import wtfadvcraft.entities.WTFcreeper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.oredict.OreDictionary;

public class WTFEventMonitor {
	
	//@SubscribeEvent
	
	public void SpawnReplacer (LivingSpawnEvent event)
	{
		if (WTFconfig.replaceExplosives == true && !event.world.isRemote && event.entityLiving instanceof EntityCreeper)
		{
				EntityCreeper vCreeper = (EntityCreeper) event.entityLiving;  
	    		if (vCreeper.getCanSpawnHere())
	    		{
	    			WTFcreeper newCreeper = new WTFcreeper(event.world);
	        		newCreeper.setLocationAndAngles(vCreeper.posX, vCreeper.posY, vCreeper.posZ, vCreeper.rotationYaw, vCreeper.rotationPitch);
	        		event.world.spawnEntityInWorld(newCreeper);
	        		vCreeper.setDead();
	    		}
		}
	}
		
	
	//slows mining of stone
	@SubscribeEvent
	public void StoneBreakSpeed (BreakSpeed event) 
	{
		if (event.block == Blocks.stone || event.block == WTFconfig.IgneousStone || event.block == WTFconfig.MetamorphicStone || event.block == WTFconfig.SedimentaryStone)
		{
		event.newSpeed = WTFconfig.stoneBreakSpeed;
		
		}

	}
	

	
	//Deals with whenever a player places a block
	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event) 
	{
		Block block = event.block;
		if (block == Blocks.dirt || block == Blocks.cobblestone || block == WTFconfig.MetamorphicStone || block == WTFconfig.MetamorphicCobblestone)
		{
			GravMethods.dropblock(event.world, event.x, event.y, event.z);
		}
		if (WTFconfig.replaceExplosives == true && event.block == Blocks.tnt)
		{
			event.world.setBlock(event.x, event.y, event.z, WTFadvcraft.blockWTFtnt);
		}
		if (WTFconfig.oreFractures == true && CheckIfOre(block)){
			event.setCanceled (true); 
		}
	}
	
	@SubscribeEvent
	public void BlockBreakEvent(BreakEvent event)
	{
		int x = event.x;
		int y = event.y;
		int z = event.z;
		World world = event.world;
		Block block = event.block;
		
		if (event.block == Blocks.stone || event.block == WTFconfig.IgneousStone || event.block == WTFconfig.MetamorphicStone)
		{
			event.setCanceled (true);
			Fracture(x, y, z, event.world);	
		}
		if (CheckIfOre(block))	
		{	
			Fracture(x+1, y, z, event.world);
			Fracture(x-1, y, z, event.world);
			Fracture(x, y+1, z, event.world);
			Fracture(x, y-1, z, event.world);
			Fracture(x, y, z+1, event.world);
			Fracture(x, y, z-1, event.world);
		}
		
		//checks for fall of the block above the block thats been broken
		GravMethods.fallcheck(event.world, x, y, z);
	}
	
	
	//turns blocks at the given location from stone to cobblestone
		public static void Fracture(int x, int y, int z, World world) 
		{
			Block blockToFracture = world.getBlock(x,  y,  z);
			int blockmeta = world.getBlockMetadata(x, y, z);
			
			//fractures stone that has been broken
			if (WTFconfig.oreFractures == true && blockToFracture == Blocks.stone){
				world.setBlock(x, y, z, Blocks.cobblestone);				
				GravMethods.dropblock(world, x, y, z);
			}
			
			//fractures igneous stone

			if (WTFconfig.oreFractures == true && blockToFracture == WTFconfig.IgneousStone){{
				 if (CheckIfOre(blockToFracture)){
					 }else{
					 world.setBlock(x, y, z, WTFconfig.IgneousCobblestone, blockmeta, 2);			
				GravMethods.dropblock(world, x, y, z);
					 }
				 }
			}
			//fractures metamorphic stone
			if (WTFconfig.oreFractures == true && blockToFracture == WTFconfig.MetamorphicStone){
				 if (CheckIfOre(blockToFracture)){
				 }else{
				world.setBlock(x, y, z, WTFconfig.MetamorphicCobblestone, blockmeta, 2);
				GravMethods.dropblock(world, x, y, z);
				 }
			}
	}

		public static boolean CheckIfOre(Block block){
			
			if (block instanceof BlockOre || block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore)// || block instanceof BlockUBOre)
			{
				return true;
		}else {
			return false;
			}
		}
		
}