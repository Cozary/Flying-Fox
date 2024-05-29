package com.cozary.flying_fox;

import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import com.cozary.flying_fox.init.ModSpawnEggs;
import com.cozary.flying_fox.init.ModTabs;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FlyingFox.MOD_ID)
public class FlyingFoxForge {

    public FlyingFoxForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FlyingFox.LOG.info("Hello Forge world!");
        eventBus.addListener(this::setupCommon);
        FlyingFox.init();
        ModTabs.CREATIVE_MODE_TAB.register(eventBus);
        ModSpawnEggs.loadClass();
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
                    SpawnPlacements.register(ModEntityTypes.FLYING_FOX.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingFoxEntity::canFlyingFoxSpawn);
                }
        );
    }
}