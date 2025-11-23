package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.block.entity.CrusherBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<CrusherBlockEntity> CRUSHER_BE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(OreDoubling.MOD_ID, "crusher_be"),
            BlockEntityType.Builder.create(CrusherBlockEntity::new, ModBlocks.CRUSHER).build(null)
    );

    public static void registerBlockEntities() {
        OreDoubling.LOGGER.info("Registering block entities");
    }
}