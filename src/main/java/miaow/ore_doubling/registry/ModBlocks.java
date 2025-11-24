package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.block.CrusherBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block CRUSHER = registerBlock("crusher", new CrusherBlock(
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(4.0F).requiresTool()
    ));
    public static final Block CRYSTAL_ORE = registerBlock("crystal_ore", new ExperienceDroppingBlock(
                    UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.copy(Blocks.COAL_ORE).strength(3.0f).requiresTool()));

    public static final Block DEEPSLATE_CRYSTAL_ORE = registerBlock("deepslate_crystal_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.copy(Blocks.DEEPSLATE_COAL_ORE).strength(4.5f).requiresTool()));


    private static Block registerBlock(String id, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(OreDoubling.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, Identifier.of(OreDoubling.MOD_ID, id), block);

    }

    public static void registerBlocks() {
        OreDoubling.LOGGER.info("Registering OreDoubling Blocks");
    }

}
