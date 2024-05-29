package com.cozary.flying_fox.register;

import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public final class EntityRegister implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricDefaultAttributeRegistry.register(ModEntityTypes.FLYING_FOX.get(), FlyingFoxEntity.createAttributes());
    }
}
