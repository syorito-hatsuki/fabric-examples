package dev.syoritohatsuki.itemcomponenttimer.component.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.Consumer;

public record TimerComponent(int time) implements TooltipAppender {
    // 20 ticks, 20 seconds
    public final static TimerComponent DEFAULT = new TimerComponent(20 * 20);
    public final static Codec<TimerComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codecs.NON_NEGATIVE_INT.fieldOf("time").forGetter(TimerComponent::time)).apply(instance, TimerComponent::new));
    public final static PacketCodec<RegistryByteBuf, TimerComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            TimerComponent::time,
            TimerComponent::new
    );

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.literal("Time left: %d".formatted(time)));
    }
}
