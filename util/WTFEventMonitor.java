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
	
	@SubscribeEvent
	public void SpawnReplacer (LivingSpawnEvent event)
	{
		if (WTFconfig.replaceCreepers == true && !event.world.isRemote && event.entityLiving instanceof EntityCreeper)
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
		if (WTFmethods.CheckIfStone(event.block, event.metadata)==true)
		{
					event.newSpeed = WTFconfig.stoneBreakSpeed;
		}

	}
	

	
	//Deals with whenever a player places a block
	@SubscribeEvent
	public void PlayerPlaceBlock (PlaceEvent event) 
	{
	if (event.player.capabilities.isCreativeMode == false){
		Block block = event.block;
		if ((WTFconfig.dirtFall == true && block == Blocks.dirt) ||
				(WTFconfig.cobbleFall == true && (block == Blocks.cobblestone || block == Blocks.mossy_cobblestone || block == WTFUBblocks.MetamorphicStone || block == WTFUBblocks.MetamorphicCobblestone))){
			WTFmethods.dropblock(event.world, event.x, event.y, event.z);
		}
		if (WTFconfig.replaceExplosives == true && event.block == Blocks.tnt)
		{
			event.world.setBlock(event.x, event.y, event.z, WTFadvcraft.blockWTFtnt);
		}
		if (WTFconfig.oreFractures == true && WTFmethods.CheckIfOre(block)){
			event.setCanceled (true); 
		}
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
		
		if (WTFmethods.CheckIfStone(block, event.blockMetadata)==true)
		{
			event.setCanceled (true);
			Fracture(x, y, z, event.world);	
		}
		if (WTFmethods.CheckIfOre(block))	
		{	
			Fracture(x+1, y, z, event.world);
			Fracture(x-1, y, z, event.world);
			Fracture(x, y+1, z, event.world);
			Fracture(x, y-1, z, event.world);
			Fracture(x, y, z+1, event.world);
			Fracture(x, y, z-1, event.world);
		}
		
		//checks for fall of the block above the block thats been broken
		WTFmethods.fallcheck(event.world, x, y, z);
	}
	
	
	//turns blocks at the given location from stone to cobblestone
		public static void Fracture(int x, int y, int z, World world) 
		{
			Block blockToFracture = world.getBlock(x,  y,  z);
			int blockmeta = world.getBlockMetadata(x, y, z);
			
			//fractures stone that has been broken
			if (WTFconfig.oreFractures == true && blockToFracture == Blocks.stone){
				world.setBlock(x, y, z, Blocks.cobblestone);				
				WTFmethods.dropblock(world, x, y, z);
			}
			
			//fractures igneous stone

			if (WTFconfig.oreFractures == true && blockToFracture == WTFUBblocks.IgneousStone){{
				 if (WTFmethods.CheckIfOre(blockToFracture)){
					 }else{
					 world.setBlock(x, y, z, WTFUBblocks.IgneousCobblestone, blockmeta, 2);			
				WTFmethods.dropblock(world, x, y, z);
					 }
				 }
			}
			//fractures metamorphic stone
			if (WTFconfig.oreFractures == true && blockToFracture == WTFUBblocks.MetamorphicStone){
				 if (WTFmethods.CheckIfOre(blockToFracture)){
				 }else{
				world.setBlock(x, y, z, WTFUBblocks.MetamorphicCobblestone, blockmeta, 2);
				WTFmethods.dropblock(world, x, y, z);
				 }
			}
			if (WTFconfig.oreFractures == true && blockToFracture == WTFUBblocks.SedimentaryStone){
				 if (WTFmethods.CheckIfOre(blockToFracture)){
				 }else{
					 //add turns to sand here
				 }
			}
	}


				
			
}
