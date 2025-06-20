package dev.syoritohatsuki.itemcomponenttimer.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.syoritohatsuki.itemcomponenttimer.component.DataComponentTypes;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FabricItem.class)
public interface FabricItemMixin {
    @ModifyReturnValue(method = "allowComponentsUpdateAnimation", at = @At(value = "RETURN"))
    private boolean disablePlagueComponentAnimation(boolean original, PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        var isTimer = oldStack.getComponents().get(DataComponentTypes.TIMER);
        return isTimer == null;
    }
}