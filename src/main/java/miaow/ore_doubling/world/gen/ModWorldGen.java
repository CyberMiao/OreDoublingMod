package miaow.ore_doubling.world.gen;

import miaow.ore_doubling.OreDoubling;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModWorldGen {
    // 这里的 ID 必须和下面 JSON 文件的文件名一致！
    public static final RegistryKey<PlacedFeature> CRYSTAL_ORE_PLACED_KEY = RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier.of(OreDoubling.MOD_ID, "ore_crystal"));

    public static void generateModWorldGen() {
        // 将矿物添加到主世界的所有生物群系
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, // 生成阶段：地下矿物
                CRYSTAL_ORE_PLACED_KEY
        );
    }
}