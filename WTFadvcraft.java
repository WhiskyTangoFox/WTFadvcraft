package wtfadvcraft;

import java.io.File;

import tconstruct.world.TinkerWorld;
import wtfadvcraft.blocks.WTFNitreOre;
import wtfadvcraft.blocks.WTFSulfurOre;
import wtfadvcraft.blocks.WTFtnt;
import wtfadvcraft.entities.EntityHandler;
import wtfadvcraft.entities.WTFcreeper;
import wtfadvcraft.entities.WTFprimedTNT;
import wtfadvcraft.items.WTFItems;
import wtfadvcraft.proxy.CommonProxy;
import wtfadvcraft.util.WTFEventMonitor;
import wtfadvcraft.util.WTFUBblocks;
import wtfadvcraft.util.WTFadvcraftWorldGen;
import wtfadvcraft.util.WTFconfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;

@Mod(modid = WTFadvcraft.modid, name = "AdventureCraft", version = "1.01")//, dependencies = "after:undergroundbiomes")
public class WTFadvcraft {
	public static  final String modid = "WTFAdvcraft";
	
	@Instance(modid)
	public static WTFadvcraft instance;
	
	WTFadvcraftWorldGen eventWorldGen = new WTFadvcraftWorldGen();
	
	//items
	public static Item itemUnrefinedNitre;
	public static Item itemUnrefinedSulfur;
	
	//blocks
	public static Block oreNitreOre;
	public static Block oreSulfurOre;
	public static Block blockWTFtnt;
		
	@SidedProxy(clientSide="wtfadvcraft.proxy.ClientProxy", serverSide="wtfadvcraft.proxy.CommonProxy")
	public static CommonProxy proxy;
		
	@EventHandler
	public void PreInit(FMLPreInitializationEvent preEvent)
	{

		WTFconfig.customConfig();
		
		//items		
		itemUnrefinedSulfur = new WTFItems().setUnlocalizedName("UnrefinedSulfur");
		GameRegistry.registerItem(itemUnrefinedSulfur, "UnrefinedSulfur");
				
		itemUnrefinedNitre = new WTFItems().setUnlocalizedName("UnrefinedNitre");				
		GameRegistry.registerItem(itemUnrefinedNitre, "UnrefinedNitre");
				
		//Blocks
		oreNitreOre = new WTFNitreOre().setBlockName("Nitre");
		GameRegistry.registerBlock(oreNitreOre, "Nitre");
		
		oreSulfurOre = new WTFSulfurOre().setBlockName("SulfurOre");
		GameRegistry.registerBlock(oreSulfurOre, "SulfurOre");
		
		blockWTFtnt = new WTFtnt().setBlockName("WTFtnt");
		GameRegistry.registerBlock(blockWTFtnt, "WTFtnt");

		EntityHandler.RegisterEntityList();
		
		//spawns
		GameRegistry.registerWorldGenerator(eventWorldGen, 0);

		proxy.registerRenderers();
		
	//UBify Ores---------------------------	
	if(Loader.isModLoaded("UndergroundBiomes") == true && Loader.isModLoaded("TConstruct") == true){
	try {
		UBAPIHook.ubAPIHook.ubOreTexturizer.
		setupUBOre(TinkerWorld.oreSlag,  3, "undergroundbiomes:copper_overlay", "Copper Ore", preEvent);
		}
	catch (Exception e) 
		{//do nothing; Underground Biomes not installed
		}
	try {
		UBAPIHook.ubAPIHook.ubOreTexturizer.
		setupUBOre(TinkerWorld.oreSlag, 4, "undergroundbiomes:tin_overlay", "Tin Ore", preEvent);
		}
	catch (Exception e) 
		{
		//do nothing; Underground Biomes not installed
		}	
	try {
		UBAPIHook.ubAPIHook.ubOreTexturizer.
		setupUBOre(TinkerWorld.oreSlag, 5, "wtfadvcraft:aluminum_overlay", "aluminum Ore", preEvent);
		}
	catch (Exception e) 
		{
		//do nothing; Underground Biomes not installed
		}
	
	
	try {
		UBAPIHook.ubAPIHook.ubOreTexturizer.
		setupUBOre(WTFadvcraft.oreSulfurOre, 0, "WTFadvcraft:sulfuroverlay", " Sulfur Ore", preEvent);
		}
	catch (Exception e) 
		{
		//do nothing; Underground Biomes not installed
		}
	
	try {
		UBAPIHook.ubAPIHook.ubOreTexturizer.
		setupUBOre(WTFadvcraft.oreNitreOre, 0, "wtfadvcraft:niteroverlay", "Nitre Ore", preEvent);
		}
	catch (Exception e) 
		{
		//do nothing; Underground Biomes not installed
		}
	}
	//------------------------------END UBIFY ORES
	}
	@EventHandler public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new WTFEventMonitor());
	
	//recipes
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder), new Object[] {WTFadvcraft.itemUnrefinedSulfur, WTFadvcraft.itemUnrefinedNitre, WTFadvcraft.itemUnrefinedNitre, WTFadvcraft.itemUnrefinedNitre, new ItemStack(Items.coal, 1, 1)});
	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent postEvent){

	}
}

