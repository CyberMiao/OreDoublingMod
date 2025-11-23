package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ORE_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(OreDoubling.MOD_ID, "ore_doubling_group"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CRUSHED_IRON_DUST))
            .displayName(Text.translatable("itemGroup.ore_doubling.ore_doubling_group"))
            .entries((stack, entries) ->{
                        entries.add(new ItemStack(ModItems.CRUSHED_COPPER_DUST));
                        entries.add(new ItemStack(ModItems.CRUSHED_IRON_DUST));
                        entries.add(new ItemStack(ModBlocks.CRUSHER));
                    }
            )
            .build());

    public static void registerItemGroup() {
        OreDoubling.LOGGER.info("Registering Ore Doubling Item Group");
    }
}
