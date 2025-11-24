package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CRUSHED_IRON_DUST = registerItem("crushed_iron_dust", new Item(new Item.Settings()));
    public static final Item CRUSHED_COPPER_DUST = registerItem("crushed_copper_dust", new Item(new Item.Settings()));
    public static final Item CRYSTAL = registerItem("crystal", new Item(new Item.Settings()));

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(OreDoubling.MOD_ID, id), item);
    }

    public static void registerModItems() {
        OreDoubling.LOGGER.info("Registering ModItems");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CRUSHED_IRON_DUST);
            entries.add(CRUSHED_COPPER_DUST);
            entries.add(CRYSTAL);
        });
    }
}