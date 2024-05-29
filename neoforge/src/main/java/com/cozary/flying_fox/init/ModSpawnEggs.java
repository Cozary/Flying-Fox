package com.cozary.flying_fox.init;

import com.cozary.flying_fox.FlyingFox;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModSpawnEggs {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, FlyingFox.MOD_ID);

    public static LinkedHashSet<RegistryObject<Item>> SPAWNEGGS_TAB = Sets.newLinkedHashSet();

    public static final Supplier<Item> FLYING_FOX_EGG = registerWithTab("flying_fox_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.FLYING_FOX, 0xb6812f, 0xace2ea, new Item.Properties()));


    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<? extends Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        SPAWNEGGS_TAB.add(item);
        return item;
    }

    public static void loadClass() {
    }
}
