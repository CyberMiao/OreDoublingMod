package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.block.CrusherBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block CRUSHER = registerBlock("crusher", new CrusherBlock(
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(4.0F).requiresTool()
    ));

    private static Block registerBlock(String id, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(OreDoubling.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, Identifier.of(OreDoubling.MOD_ID, id), block);

    }

    public static void registerBlocks() {
        OreDoubling.LOGGER.info("Registering OreDoubling Blocks");
    }

}
