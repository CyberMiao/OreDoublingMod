package miaow.ore_doubling.registry;

import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.screen.CrusherScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<CrusherScreenHandler> CRUSHER_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(OreDoubling.MOD_ID, "crushing"),
            new ExtendedScreenHandlerType<>(CrusherScreenHandler::new, BlockPos.PACKET_CODEC) // 需要在 Handler 里添加对应的构造函数
    );

    public static void registerScreenHandlers() {
        OreDoubling.LOGGER.info("Registering Screen Handlers");
    }
}
