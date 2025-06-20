package dev.syoritohatsuki.itemcomponenttimer.component;

import dev.syoritohatsuki.itemcomponenttimer.component.type.TimerComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

import static dev.syoritohatsuki.itemcomponenttimer.ItemComponentTimer.MOD_ID;

public final class DataComponentTypes {
    public static ComponentType<TimerComponent> TIMER = register("timer", builder ->
            builder.codec(TimerComponent.CODEC)
                    .packetCodec(TimerComponent.PACKET_CODEC)
                    .cache()
    );

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(MOD_ID, id),
                builderOperator.apply(ComponentType.builder()).build()
        );
    }
}
