/*
 *
 *  *
 *  *  * Copyright (c) 2024 Cozary
 *  *  *
 *  *  * This file is part of Flying Fox, a mod made for Minecraft.
 *  *  *
 *  *  * Flying Fox is free software: you can redistribute it and/or modify it
 *  *  * under the terms of the GNU General Public License as published
 *  *  * by the Free Software Foundation, either version 3 of the License, or
 *  *  * (at your option) any later version.
 *  *  *
 *  *  * Flying Fox is distributed in the hope that it will be useful, but
 *  *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 *  *  * GNU General Public License for more details.
 *  *  *
 *  *  * You should have received a copy of the GNU General Public License
 *  *  * License along with Flying Fox.  If not, see <https://www.gnu.org/licenses/>.
 *  *
 *
 */

package com.cozary.flying_fox.client.render;

import com.cozary.flying_fox.FlyingFox;
import com.cozary.flying_fox.client.model.FlyingFoxEntityModel;
import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.cozary.flying_fox.util.ClientEventBusSubscriber;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FlyingFoxEntityRenderer extends MobRenderer<FlyingFoxEntity, FlyingFoxEntityModel<FlyingFoxEntity>> {

    private static final Map<FlyingFoxEntity.FoxType, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (p_115516_) -> {
        p_115516_.put(FlyingFoxEntity.FoxType.BROWN, new ResourceLocation("textures/entity/fox/fox.png"));
        p_115516_.put(FlyingFoxEntity.FoxType.YELLOW, new ResourceLocation(FlyingFox.MOD_ID, "textures/entity/tails.png"));
    });

    public FlyingFoxEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FlyingFoxEntityModel<>(context.bakeLayer(ClientEventBusSubscriber.FLYING_FOX)), 0.5F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull FlyingFoxEntity flyingFoxEntity) {
        return TEXTURES.get(flyingFoxEntity.getFoxType());
    }

}
