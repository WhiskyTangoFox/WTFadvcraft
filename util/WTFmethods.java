package wtfadvcraft.util;

import cpw.mods.fml.common.Loader;
import exterminatorJeff.undergroundBiomes.common.block.BlockUBOre;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WTFmethods {
	static void dropblock(World world, int x, int y, int z)
	{	
		Block targetBlock = world.getBlock(x,  y,  z); 
		
		if (targetBlock == Blocks.grass){world.setBlock(x, y, z, Blocks.dirt);}
		
		EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), world.getBlock(x,  y,  z), world.getBlockMetadata(x, y, z));			
		world.spawnEntityInWorld(entityfallingblock);		
		}
	

//Called to check if block should fall
	static void fallcheck(World world, int x, int y, int z)
	{
	Block blockZero = world.getBlock(x, y, z);
	Block blockOneAbove = world.getBlock(x, y+1, z);
	Block blockTwoAbove = world.getBlock(x, y+2, z);
		
		//checks if block above is cobblestone, and drops it
		if (WTFconfig.cobbleFall == true && (blockOneAbove == Blocks.cobblestone || blockOneAbove == Blocks.mossy_cobblestone || blockOneAbove == WTFUBblocks.MetamorphicCobblestone || blockOneAbove == WTFUBblocks.IgneousCobblestone))
		{
			dropblock(world, x, y+1, z);
		}	
		
		//checks if block above is dirt
		if (WTFconfig.dirtFall == true && (blockOneAbove instanceof BlockDirt || blockOneAbove instanceof BlockGrass))
		{
			if (blockTwoAbove instanceof BlockDirt || blockTwoAbove instanceof BlockGrass){
				//do nothing- OR add a random chance to drop the block
			} else 
				{
				dropblock(world, x, y+1, z);
				}
		}
		//to do- check all other adjacent dirt?
	}
	
	public static boolean CheckIfOre(Block block){
		
		if(Loader.isModLoaded("UndergroundBiomes") == true)
		{
			if (block instanceof BlockOre || block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore || block instanceof BlockUBOre)
			{
				return true;
			}
			else
			{
				return false;
			}
		}else{
			if (block instanceof BlockOre || block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore)
		{
			return true;
		}else{
			return false;
		}
		
		}
	
	}	
	
	public static boolean CheckIfStone(Block block, int metadata){
		if (block == Blocks.stone || metadata < 8 && (block == WTFUBblocks.IgneousStone || block == WTFUBblocks.MetamorphicStone))// || block == WTFUBblocks.SedimentaryStone)){
		{
			return true;
		}else{
			return false;
		}
	}
}
