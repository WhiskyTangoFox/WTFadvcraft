package wtfadvcraft.util;

import java.util.Random;

import wtfadvcraft.WTFadvcraft;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WTFadvcraftWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		
		switch(world.provider.dimensionId)
		{
		case 0 :
			// generate surface world
			generateSurface(world, random, chunkX*16, chunkZ*16);
		}
	}

	private void generateSurface(World world, Random random, int x, int z) {
		//this.addOreSpawn.WTFadvcraft.oreNitreOre, world, random, I, J, 16, 16, max vein size 4+random.nextInt(6), chance to spawn = 25, min Y 38, max Y 100);
		this.addOreSpawn(WTFadvcraft.oreNitreOre, world, random, x, z, 16, 16, 4+random.nextInt(6), WTFconfig.nitreSpawnRate, 38, 100);
		this.addOreSpawn(WTFadvcraft.oreSulfurOre, world, random, x, z, 16, 16, 1+random.nextInt(6), WTFconfig.sulfurSpawnRate, 38, 100);
	}

	private void addOreSpawn(Block block, World world, Random random, int x, int z, int maxx, int maxz, int maxveinsize, int chancetospawn, int miny, int maxy) {
		for(int i = 0; i < chancetospawn; i++)
		{
			int posX = x + random.nextInt(maxx);
			int posY = miny + random.nextInt(maxy - miny);
			int posZ = z + random.nextInt(maxz);
			(new WorldGenMinable(block, maxveinsize)).generate(world, random, posX, posY, posZ);
		}
		
	}


	}


