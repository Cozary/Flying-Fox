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

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * Represents a lazy wrapper for registry object.
 *
 * @param <T> the type of the object
 */
public interface RegistryObject<T> extends Supplier<T> {

    /**
     * Gets the {@link ResourceKey} of the registry of the object wrapped.
     *
     * @return the {@link ResourceKey} of the registry
     */
    ResourceKey<T> getResourceKey();

    /**
     * Gets the id of the object.
     *
     * @return the id of the object
     */
    ResourceLocation getId();

    /**
     * Gets the object behind this wrapper. Calling this method too early
     * might result in crashes.
     *
     * @return the object behind this wrapper
     */
    @Override
    T get();

    /**
     * Gets this object wrapped in a vanilla {@link Holder}.
     *
     * @return the holder
     */
    Holder<T> asHolder();

}
