package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
        import com.badlogic.gdx.utils.viewport.Viewport;
        import com.mygdx.game.Objects.Asteroide;
        import com.mygdx.game.Objects.Bala;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;

public class C_Bala {

    private static String path[] = {"bullet2","asteroid2","asteroid3"};
    private static Integer max[] = {7, 16, 30};
    private static float power[] = {10.0f, 30.0f, 50.0f};

    private ArrayList<Bala> balas;
    public static final int TAM = 0;

    private long startTime = 0;

    public C_Bala()
    {
        balas = new ArrayList<>();
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
        if(bala.getRectangle().overlaps(asteroide.getRectangle()) && TimeUtils.timeSinceMillis(startTime) > 1500)
        {
            startTime = TimeUtils.millis();
        }
    }

    public void addBala(Viewport viewport, Vector2 position, Vector2 centro)
    {
        if(TimeUtils.timeSinceMillis(startTime) > 500)
        {
            startTime = TimeUtils.millis();
            balas.add(new Bala(viewport, position, centro));
        }

    }

    private void update()
    {
        for(int i = 0; i < balas.size(); i++)
        {
            if(balas.get(i).isState() == false)
                balas.remove(i);
        }
    }

    public void render(float delta, Viewport viewport)
    {
        update();

        for (Bala b : balas) {
            b.render(delta);
            //checkCollision(asteroide, b);
        }
    }
}
