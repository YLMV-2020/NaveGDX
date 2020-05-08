package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.Asteroide;
import com.mygdx.game.Objects.Nave;

import java.util.ArrayList;


public class C_Asteroide {

    private static String path[] = {"asteroid","asteroid2","asteroid3"};
    private static Integer max[] = {32, 16, 30};

    private ArrayList<Asteroide> asteroides;
    private final int TAM = 2;

    private long startTime = 0;
    private long collisionTime = 0;

    public C_Asteroide(Viewport viewport)
    {
        asteroides = new ArrayList<>();

        for(int i = 0; i < TAM; i++)
        {
            asteroides.add(new Asteroide(viewport));
        }

        startTime = TimeUtils.millis();
        collisionTime = TimeUtils.millis();
    }

    public static String textureRandom()
    {
        int index = MathUtils.random(2);
        Integer aleatory = MathUtils.random(max[index] - 1) + 1;

        String out;

        if(aleatory < 10)
            out = path[index] + "\\" + path[index] +  "_0" + aleatory.toString() + ".png";
        else
            out = path[index] + "\\" + path[index] +  "_" + aleatory.toString() + ".png";
        return out;
    }

    public void checkCollision(Nave nave, Asteroide asteroide)
    {
        if(nave.getRectangle().overlaps(asteroide.getRectangle()) && TimeUtils.timeSinceMillis(startTime) > 1500)
        {
            startTime = TimeUtils.millis();
            nave.setVidas(nave.getVidas() - 1);
        }
    }

    public void addAsteroid(Viewport viewport)
    {
        asteroides.add(new Asteroide(viewport));
    }

    private void update()
    {
        for(int i = 0; i < asteroides.size(); i++)
        {
            if(asteroides.get(i).isState() == false)
                asteroides.remove(i);
        }
    }

    public void render(float delta, Viewport viewport, Nave nave)
    {
        if (TimeUtils.timeSinceMillis(startTime) > 3000) {
            startTime = TimeUtils.millis();
            addAsteroid(viewport);
        }
        update();

        for (Asteroide a : asteroides) {
            a.render(delta);
            checkCollision(nave, a);
        }
    }

}
