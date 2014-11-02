package wtfadvcraft.util;

import java.io.File;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WTFconfig {

	
	public static int nitreSpawnRate;
	public static int sulfurSpawnRate;
	
	public static float stoneBreakSpeed;
	
	public static boolean replaceExplosives;
	
	public static boolean dirtFall;
	public static boolean cobbleFall;
	public static boolean oreFractures;
	
	public static final Block IgneousStone = GameData.getBlockRegistry().getObject("UndergroundBiomes:igneousStone");
	public static final Block IgneousCobblestone = GameData.getBlockRegistry().getObject("UndergroundBiomes:igneousCobblestone");
	public static final Block MetamorphicStone = GameData.getBlockRegistry().getObject("UndergroundBiomes:metamorphicStone");
	public static final Block MetamorphicCobblestone = GameData.getBlockRegistry().getObject("UndergroundBiomes:metamorphicCobblestone");
	public static final Block SedimentaryStone = GameData.getBlockRegistry().getObject("UndergroundBiomes:sedimentaryStone");

	
	public static void customConfig() {
	Configuration config = new Configuration(new File("config/WTFadvcraft.cfg"));
	
	config.load();
	
	nitreSpawnRate = config.get("SpawnRate", "Nitre spawn rate", 9).getInt();
	sulfurSpawnRate = config.get("SpawnRate", "Sulfur spawn rate", 9).getInt();
	
	stoneBreakSpeed = (float)config.get("HarderMining", "Stone break speed % modifier", 20).getInt()/100;
	
	replaceExplosives = config.get("ReplaceExplosives", "Creepers and TNT drop all blocks", true).getBoolean();
	
	dirtFall = config.get("Gravity", "Dirt falls when disturbed", true).getBoolean();
	cobbleFall = config.get("Gravity", "Cobblestone falls when disturbed", true).getBoolean();
	oreFractures = config.get("Ore Fractures", "Ores fracture adjacent blocks when mined", true).getBoolean();
	
	config.save();
	

	
	}


}
