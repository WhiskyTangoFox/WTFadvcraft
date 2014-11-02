package wtfadvcraft.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GravMethods {
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
		if (blockOneAbove == Blocks.cobblestone || blockOneAbove == Blocks.mossy_cobblestone || blockOneAbove == WTFconfig.MetamorphicCobblestone || blockOneAbove == WTFconfig.IgneousCobblestone)
		{
			dropblock(world, x, y+1, z);
		}	
		
		//checks if block above is dirt
		if (blockOneAbove instanceof BlockDirt || blockOneAbove instanceof BlockGrass)
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
}
