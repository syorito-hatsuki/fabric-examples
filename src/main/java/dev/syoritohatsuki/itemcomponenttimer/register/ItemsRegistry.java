package dev.syoritohatsuki.itemcomponenttimer.register;

import dev.syoritohatsuki.itemcomponenttimer.component.DataComponentTypes;
import dev.syoritohatsuki.itemcomponenttimer.component.type.TimerComponent;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static dev.syoritohatsuki.itemcomponenttimer.ItemComponentTimer.MOD_ID;

public final class ItemsRegistry {

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(TIMER_WATCH);
        });
    }

    public final static Item TIMER_WATCH = register(
            "timer_watch",
            new Item.Settings().component(DataComponentTypes.TIMER, TimerComponent.DEFAULT)
    );

    public static Item register(String id, Item.Settings settings) {
        return Items.register(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id)), Item::new, settings);
    }
}
