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
