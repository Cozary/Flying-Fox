package com.cozary.flying_fox.register;

import com.cozary.flying_fox.FlyingFox;
import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod.EventBusSubscriber(modid = FlyingFox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {


    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FLYING_FOX.get(), FlyingFoxEntity.createAttributes().build());
    }
}
