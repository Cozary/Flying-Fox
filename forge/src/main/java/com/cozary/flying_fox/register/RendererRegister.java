package com.cozary.flying_fox.register;

import com.cozary.flying_fox.FlyingFox;
import com.cozary.flying_fox.client.model.FlyingFoxEntityModel;
import com.cozary.flying_fox.client.render.FlyingFoxEntityRenderer;
import com.cozary.flying_fox.init.ModEntityTypes;
import com.cozary.flying_fox.util.ClientEventBusSubscriber;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlyingFox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegister {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.FLYING_FOX.get(), FlyingFoxEntityRenderer::new);

    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ClientEventBusSubscriber.FLYING_FOX, FlyingFoxEntityModel::createBodyLayer);
    }

}
