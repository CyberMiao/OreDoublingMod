// src/main/java/miaow/ore_doubling/OreDoubling.java
package miaow.ore_doubling;

import miaow.ore_doubling.registry.*;
import net.fabricmc.api.ModInitializer;
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
    }

}