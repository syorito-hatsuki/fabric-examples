package dev.syoritohatsuki.itemcomponenttimer.mixin.item;

import dev.syoritohatsuki.itemcomponenttimer.component.DataComponentTypes;
import dev.syoritohatsuki.itemcomponenttimer.component.type.TimerComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.component.MergedComponentMap;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract <T extends TooltipAppender> void appendComponentTooltip(ComponentType<T> componentType, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type);

    @Shadow @Final MergedComponentMap components;

    @Shadow @Nullable public abstract <T> T set(ComponentType<T> type, @Nullable T value);

    @Inject(method = "appendTooltip", at = @At(value = "TAIL"))
    private void appendTooltip(Item.TooltipContext context, TooltipDisplayComponent displayComponent, @Nullable PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
        appendComponentTooltip(DataComponentTypes.TIMER, context, displayComponent, textConsumer, type);
    }

    @Inject(method = "inventoryTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;inventoryTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/EquipmentSlot;)V"))
    private void tickTimer(World world, Entity entity, EquipmentSlot slot, CallbackInfo ci) {
        var timer = components.get(DataComponentTypes.TIMER);
        if (timer != null) {
            var time = timer.time();
            if (time > 0) {
                var lastTime = time - 1;
                set(DataComponentTypes.TIMER, new TimerComponent(lastTime));
                return;
            }

            if (entity instanceof PlayerEntity playerEntity) {
                playerEntity.sendMessage(Text.literal("Time end"), false);
            }
        }
    }
}
