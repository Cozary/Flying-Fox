package com.cozary.flying_fox;

import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import com.cozary.flying_fox.init.ModSpawnEggs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;

public class FlyingFoxFabric implements ModInitializer {

    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(FlyingFox.MOD_ID, "flying_fox"));


    @Override
    public void onInitialize() {

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.flying_fox"))
                .icon(() -> new ItemStack(ModSpawnEggs.FLYING_FOX_EGG.get()))
                .displayItems((parameters, output) -> ModSpawnEggs.SPAWNEGGS_TAB.forEach((item) -> output.accept(item.get())))
                .build()
        );

        FlyingFox.LOG.info("Hello Fabric world!");
        FlyingFox.init();
        register();
        ModSpawnEggs.loadClass();
    }

    public void register() {

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, ModEntityTypes.FLYING_FOX.get(), 5, 1, 5);

        SpawnPlacements.register(ModEntityTypes.FLYING_FOX.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingFoxEntity::canFlyingFoxSpawn);

    }
}
