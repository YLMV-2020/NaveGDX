package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controllers.C_Asteroide;
import com.mygdx.game.Controllers.C_Bala;
import com.mygdx.game.MyGdxGame;

public class Bala {

    private SpriteBatch batch;
    private Texture texture;

    private boolean state;
    private float power;

    private Rectangle rectangle;
    private Vector2 position;

    private ShapeRenderer renderer;
    private Viewport viewport;

    private float velocity;

    public Bala(Viewport viewport, Vector2 position, Vector2 centro)
    {
        this.viewport = viewport;
        batch = new SpriteBatch();

        int index = MathUtils.random(C_Bala.TAM);
        texture = new Texture(Gdx.files.internal(C_Bala.texture(index)));

        power = C_Bala.power(index);
        state = true;

        this.position = new Vector2(position.x + centro.x, position.y + centro.y);
        rectangle = new Rectangle(10,10, texture.getWidth(), texture.getHeight());

        renderer = new ShapeRenderer();
        velocity = 100f;
    }

    private void checkPosition()
    {
        if(position.y >= MyGdxGame.HEIGHT)
        {
            state = false;
        }
    }

    private void update(float delta)
    {
        position.y += delta * velocity;
        checkPosition();
    }

    public void render(float delta){

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        update(delta);
        rectangle.setPosition(position);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(new Color(0, 1, 0, 0));

        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();

        batch.begin();
        batch.draw(texture, position.x, position.y);

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    public void dispose(){
        texture.dispose();
        batch.dispose();
        renderer.dispose();
    }

    public boolean isState() { return state; }
    public Rectangle getRectangle() { return rectangle; }
}
