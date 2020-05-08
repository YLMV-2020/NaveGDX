package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;


public class C_Asteroide {

    private static String path[] = {"asteroid","asteroid2","asteroid3"};
    private static Integer max[] = {32, 16, 30};

    public C_Asteroide()
    {

    }

    public static String textureRandom()
    {
        int index = MathUtils.random(2);
        Integer aleatory = MathUtils.random(max[index] - 1) + 1;
        String out;
        System.out.println(path[index] + "\\" + path[index] +  "_0" + aleatory.toString() + ".png");

        if(aleatory < 10)
            out = path[index] + "\\" + path[index] +  "_0" + aleatory.toString() + ".png";
        else
            out = path[index] + "\\" + path[index] +  "_" + aleatory.toString() + ".png";
        return out;
    }


}
