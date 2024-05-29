package com.cozary.flying_fox.util;


import com.cozary.flying_fox.FlyingFox;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ClientEventBusSubscriber {

    public static ModelLayerLocation FLYING_FOX = new ModelLayerLocation(new ResourceLocation(FlyingFox.MOD_ID, "flying_fox"), "flying_fox");

}
