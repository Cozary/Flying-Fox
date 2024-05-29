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