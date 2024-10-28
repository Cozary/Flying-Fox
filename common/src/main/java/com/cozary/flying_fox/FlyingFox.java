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

package com.cozary.flying_fox;

import com.cozary.flying_fox.init.ModEntityTypes;
import org.slf4j.LoggerFactory;

public class FlyingFox {

    public static final String MOD_ID = "flying_fox";
    public static final String MOD_NAME = "Flying Fox";
    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {

        ModEntityTypes.loadClass();

    }
}