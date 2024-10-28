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

package com.cozary.flying_fox.init;

import com.cozary.flying_fox.FlyingFox;
import com.cozary.flying_fox.entities.FlyingFoxEntity;
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModEntityTypes {

    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(Registries.ENTITY_TYPE, FlyingFox.MOD_ID);

    public static LinkedHashSet<RegistryObject<EntityType<?>>> ENTITY_LIST = Sets.newLinkedHashSet();

    public static final RegistryObject<EntityType<FlyingFoxEntity>> FLYING_FOX = registerEntitiesList("flying_fox", () ->
            EntityType.Builder.of(FlyingFoxEntity::new, MobCategory.CREATURE)
                    .sized(0.7F, 0.6F).clientTrackingRange(8)
                    .build(new ResourceLocation(FlyingFox.MOD_ID, "flying_fox").toString()));

    @SuppressWarnings("unchecked")
    public static <T extends EntityType<?>> RegistryObject<T> registerEntitiesList(final String name, final Supplier<? extends T> supplier) {
        RegistryObject<T> entity = ENTITY_TYPES.register(name, supplier);
        ENTITY_LIST.add((RegistryObject<EntityType<?>>) entity);
        return entity;
    }

    public static void loadClass() {
    }

}
