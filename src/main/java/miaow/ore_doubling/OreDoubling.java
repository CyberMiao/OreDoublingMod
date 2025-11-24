// src/main/java/miaow/ore_doubling/OreDoubling.java
package miaow.ore_doubling;

import miaow.ore_doubling.registry.*;
import miaow.ore_doubling.world.gen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OreDoubling implements ModInitializer {
    public static final String MOD_ID = "ore_doubling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        register();
        LOGGER.info("OreDoubling has been initialized!");
    }

    private void register() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroup();
        ModBlocks.registerBlocks();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
        ModRecipes.registerRecipes();
        ModWorldGen.generateModWorldGen();
    }

}