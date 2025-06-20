package dev.syoritohatsuki.itemcomponenttimer;

import com.mojang.logging.LogUtils;
import dev.syoritohatsuki.itemcomponenttimer.register.ItemsRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public final class ItemComponentTimer implements ModInitializer {

    public static final String MOD_ID = "item-component-timer";
    public static final Logger serverLogger = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        serverLogger.info("{} initialized with mod-id {}", getClass().getSimpleName(), MOD_ID);

        ItemsRegistry.init();
    }
}