// src/main/java/miaow/ore_doubling/OreDoubling.java
package miaow.ore_doubling;

import miaow.ore_doubling.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.intellij.lang.annotations.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class OreDoubling implements ModInitializer {
    public static final String MOD_ID = "ore-doubling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        register();
        LOGGER.info("OreDoubling has been initialized!");
    }

    private void register() {
        ModItems.registerModItems();
    }

}