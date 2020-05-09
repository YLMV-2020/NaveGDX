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

    private static float power[] = {5.0f, 10.0f, 15.0f, 20.0f, 30.0f, 40.0f, 90.f};
    public static int cantidad[] = {10, 0, 0, 0, 0, 0, 0};

    public static ArrayList<Bala> balas;
    public static final int TAM = 6;

    private long startTime = 0;

    public C_Bala()
    {
        balas = new ArrayList<>();
        startTime = TimeUtils.millis();
    }

    public static String texture(Integer index)
    {
        String out;
        if(index < 10)
            out =  "bullet\\bullet_0" + index.toString() + ".png";
        else
            out =  "bullet\\bullet_" + index.toString() + ".png";

        return out;
    }

    public static float power(Integer index)
    {
        return power[index];
    }

    public void addBala(Viewport viewport, Vector2 position)
    {
        if(TimeUtils.timeSinceMillis(startTime) > 100)
        {
            startTime = TimeUtils.millis();
            balas.add(new Bala(viewport, position));
        }
    }

    public static int sort()
    {
        int index = 0;
        for(int i = 0; i <= TAM; i++)
        {
            if(cantidad[i] > 0 && power[i] > power[index])
                index = i;
        }
        return index;
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
        }
    }
}
