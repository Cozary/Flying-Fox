package com.cozary.flying_fox.register;

import com.cozary.flying_fox.client.model.FlyingFoxEntityModel;
import com.cozary.flying_fox.client.render.FlyingFoxEntityRenderer;
import com.cozary.flying_fox.init.ModEntityTypes;
import com.cozary.flying_fox.util.ClientEventBusSubscriber;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class RendererRegister implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(ClientEventBusSubscriber.FLYING_FOX, FlyingFoxEntityModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntityTypes.FLYING_FOX.get(), FlyingFoxEntityRenderer::new);

    }
}
