package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.item.CrystalItem;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item CRYSTAL_ITEM = registerItems("crystal_item", new Item(new Item.Properties()));


    public static Item registerItems(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.parse(OreDoubling.MOD_ID + ":" + id), item);
    }

    public static void registerModItems() {
        OreDoubling.LOGGER.info("Registering ModItems");
    }
}