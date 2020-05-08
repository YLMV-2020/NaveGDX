package com.mygdx.game.Controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Objects.Asteroide;
import com.mygdx.game.Objects.Bala;
import com.mygdx.game.Objects.Nave;
import com.mygdx.game.Objects.Potion;
import com.mygdx.game.Screens.PlayScreen;

import java.util.ArrayList;

public class C_Potion {

    private static ArrayList<Potion> potions;
    public static final int TAM = 49;

    private long startTime = 0;

    public C_Potion(Viewport viewport)
    {
        potions = new ArrayList<>();
        startTime = TimeUtils.millis();
    }

    public static String texture(Integer index)
    {
        String out;
        if(index < 10)
            out =  "potion\\potion_0" + index.toString() + ".png";
        else
            out =  "potion\\potion_" + index.toString() + ".png";

        return out;
    }



    public void checkCollision(Nave nave, Potion potion)
    {
        if(nave.getRectangle().overlaps(potion.getRectangle()) && TimeUtils.timeSinceMillis(startTime) > 1500)
        {
            startTime = TimeUtils.millis();
            potion.setCollision(true);
            //nave.setVidas(nave.getVidas() - 1);
        }
    }

    public void addPotion(Viewport viewport)
    {
        potions.add(new Potion(viewport));
    }

    private void update()
    {
        for(int i = 0; i < potions.size(); i++)
        {
            if(potions.get(i).isState() == false)
            {
                potions.remove(i);
            }
            else if(potions.get(i).isCollision())
            {
                //PlayScreen.puntos+= point[potions.get(i).getIndex()];
                potions.remove(i);
            }
        }
    }

    public void render(float delta, Viewport viewport, Nave nave)
    {
        if (TimeUtils.timeSinceMillis(startTime) > 10000) {
            startTime = TimeUtils.millis();
            addPotion(viewport);
        }

        update();

        for (Potion p : potions) {
            p.render(delta);
            checkCollision(nave, p);
        }
    }

}
