package com.cozary.flying_fox;


import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import com.cozary.flying_fox.init.ModSpawnEggs;
import com.cozary.flying_fox.init.ModTabs;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(FlyingFox.MOD_ID)
public class FlyingFoxNeoForge {

    public FlyingFoxNeoForge(IEventBus eventBus) {
        eventBus.addListener(this::setupCommon);

        FlyingFox.LOG.info("Hello NeoForge world!");
        FlyingFox.init();
        ModTabs.init(eventBus);
        ModSpawnEggs.loadClass();
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
                    SpawnPlacements.register(ModEntityTypes.FLYING_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlyingFoxEntity::canFlyingFoxSpawn);
                }
        );
    }
}