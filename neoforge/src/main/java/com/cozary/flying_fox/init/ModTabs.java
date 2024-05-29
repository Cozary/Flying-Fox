package com.cozary.flying_fox.init;

import com.cozary.flying_fox.FlyingFox;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlyingFox.MOD_ID);

    public static final Supplier<CreativeModeTab> FLYING_FOX_TAB = CREATIVE_MODE_TAB.register("flying_fox", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.flying_fox"))
            .icon(() -> new ItemStack(ModSpawnEggs.FLYING_FOX_EGG.get()))
            .displayItems((parameters, output) -> ModSpawnEggs.SPAWNEGGS_TAB.forEach((item) -> output.accept(item.get())))
            .build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}


