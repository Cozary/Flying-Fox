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
import com.google.common.collect.Sets;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class ModSpawnEggs {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, FlyingFox.MOD_ID);

    public static LinkedHashSet<RegistryObject<Item>> SPAWNEGGS_TAB = Sets.newLinkedHashSet();

    public static final Supplier<Item> FLYING_FOX_EGG = registerWithTab("flying_fox_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.FLYING_FOX, 0xb6812f, 0xace2ea, new Item.Properties()));


    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<? extends Item> supplier) {
        RegistryObject<Item> item = ITEMS.register(name, supplier);
        SPAWNEGGS_TAB.add(item);
        return item;
    }

    public static void loadClass() {
    }
}
