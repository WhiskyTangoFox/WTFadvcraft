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
	public static boolean replaceCreepers;
	
	public static boolean dirtFall;
	public static boolean cobbleFall;
	public static boolean oreFractures;
	
	
	
	public static void customConfig() {
	Configuration config = new Configuration(new File("config/WTFadvcraft.cfg"));
	
	config.load();
	
	nitreSpawnRate = config.get("SpawnRate", "Nitre spawn rate", 9).getInt();
	sulfurSpawnRate = config.get("SpawnRate", "Sulfur spawn rate", 9).getInt();
	
	stoneBreakSpeed = (float)config.get("HarderMining", "Stone break speed % modifier", 20).getInt()/100;
	
	replaceExplosives = config.get("Explosives", "TNT drops all blocks", true).getBoolean();
	replaceCreepers = config.get("Explosives", "Creepers drop all blocks", true).getBoolean();
	
	dirtFall = config.get("Gravity", "Dirt falls when disturbed", true).getBoolean();
	cobbleFall = config.get("Gravity", "Cobblestone falls when disturbed", true).getBoolean();
	oreFractures = config.get("Ore Fractures", "Ores fracture adjacent blocks when mined", true).getBoolean();
	
	config.save();
	

	
	}


}
