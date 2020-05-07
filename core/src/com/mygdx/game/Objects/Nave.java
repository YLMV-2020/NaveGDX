package com.mygdx.game.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Nave {

    SpriteBatch batch;
    Texture texture;
    boolean state;
    Rectangle rectangle;
    Vector2 position;
    ShapeRenderer renderer;
    Viewport viewport;

    public Nave(Viewport viewport)
    {
        this.viewport = viewport;
        batch = new SpriteBatch();
        texture = new Texture("nave.png");
        state = true;
        rectangle = new Rectangle(10,10, texture.getWidth(), texture.getHeight());
        position = new Vector2(10,10);
        renderer = new ShapeRenderer();
    }

    public void checkPosition()
    {
        float width = MyGdxGame.WIDTH - texture.getWidth();
        float height = MyGdxGame.HEIGHT - texture.getHeight();

        if(position.x > width ) position.x = width;
        if(position.x < 0) position.x = 0;
        if(position.y > height) position.y = height;
        if(position.y < 0) position.y = 0;
    }


    public void update(float delta)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x += delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) position.y += delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= delta *180f;

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
        renderer.setColor(new Color(0, 1, 0, 0.5f));
        renderer.rect(getRectangle().x, getRectangle().y, getRectangle().width, getRectangle().height);
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isState() {
        return state;
    }


}
