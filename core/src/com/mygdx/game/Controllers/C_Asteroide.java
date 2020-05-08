package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class C_Asteroide {

    public static C_Asteroide shared;

    Texture texture;
    TextureRegion region[][];

    public C_Asteroide()
    {
        shared = this;
    }
    

}
