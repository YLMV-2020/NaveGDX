package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.Asteroide;
import com.mygdx.game.Objects.Bala;
import com.mygdx.game.Objects.Nave;
import com.mygdx.game.Screens.PlayScreen;

import java.util.ArrayList;


public class C_Asteroide {

    private static String path[] = {"asteroid","asteroid2","asteroid3"};
    private static Integer max[] = {32, 16, 30};
    private static float power[] = {15.0f, 40.0f, 90.0f};
    private static Integer point[] = {3, 5, 10};

    private static ArrayList<Asteroide> asteroides;
    public static final int TAM = 2;

    private long startTime = 0;

    public C_Asteroide(Viewport viewport)
    {
        asteroides = new ArrayList<>();

        for(int i = 0; i < TAM; i++)
        {
            asteroides.add(new Asteroide(viewport));
        }

        startTime = TimeUtils.millis();
    }

    public static String texture(int index)
    {
        Integer aleatory = MathUtils.random(max[index] - 1) + 1;

        String out;

        if(aleatory < 10)
            out = path[index] + "\\" + path[index] +  "_0" + aleatory.toString() + ".png";
        else
            out = path[index] + "\\" + path[index] +  "_" + aleatory.toString() + ".png";
        return out;
    }

    public static float power(int index)
    {
        return power[index];
    }

    public void checkCollision(Asteroide asteroide, Bala bala)
    {
        if(bala.getRectangle().overlaps(asteroide.getRectangle()) && TimeUtils.timeSinceMillis(startTime) > 500)
        {
            float powerA = asteroide.getPower();
            float powerB = bala.getPower();

            powerA = powerA - powerB;

            System.out.println("R: " + powerA);

            if(powerA <= 0)
            {
                asteroide.setDestroyed(true);
            }

            startTime = TimeUtils.millis();
            asteroide.setPower(powerA);
            bala.setState(false);
        }
    }

    public void checkCollision(Nave nave, Asteroide asteroide)
    {
        if(nave.getRectangle().overlaps(asteroide.getRectangle()) && TimeUtils.timeSinceMillis(startTime) > 1500)
        {
            startTime = TimeUtils.millis();
            asteroide.setState(false);
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
            {
                asteroides.remove(i);
            }
            else if(asteroides.get(i).isDestroyed())
            {
                PlayScreen.puntos+= point[asteroides.get(i).getIndex()];
                asteroides.remove(i);
            }
        }
    }

    public void render(float delta, Viewport viewport, Nave nave)
    {
        if (TimeUtils.timeSinceMillis(startTime) > 2000) {
            startTime = TimeUtils.millis();
            addAsteroid(viewport);
        }

        update();

        for (Asteroide a : asteroides) {
            a.render(delta);
            checkCollision(nave, a);
            for (Bala b: C_Bala.balas)
            {
                checkCollision(a, b);
            }
        }
    }

}
