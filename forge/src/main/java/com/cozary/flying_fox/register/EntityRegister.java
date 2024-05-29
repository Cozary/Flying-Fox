package com.cozary.flying_fox.register;


import com.cozary.flying_fox.FlyingFox;
import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.init.ModEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlyingFox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {


    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.FLYING_FOX.get(), FlyingFoxEntity.createAttributes().build());
    }
}
