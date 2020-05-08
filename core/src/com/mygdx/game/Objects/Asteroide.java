package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Asteroide {

    public static final int U = 16;
    public static final int V = 2;

    SpriteBatch batch;
    Texture texture;

    private TextureRegion region[][];

    boolean state;
    float power;
    Rectangle rectangle;
    Vector2 position;
    ShapeRenderer renderer;
    Viewport viewport;

    public Asteroide(Viewport viewport)
    {
        this.viewport = viewport;
        batch = new SpriteBatch();
        texture = new Texture("nave.png");
        state = true;
        power = MathUtils.random(5, 100);

        float width = MyGdxGame.WIDTH - texture.getWidth()/U;
        float height = MyGdxGame.HEIGHT - texture.getHeight()/V;

        position = new Vector2(MathUtils.random(width), height);
        rectangle = new Rectangle(10,10, texture.getWidth(), texture.getHeight());

        renderer = new ShapeRenderer();

        texture = new Texture(Gdx.files.internal("asteroid.png"));
        region = new TextureRegion[V][U];
        region = TextureRegion.split(texture,texture.getWidth()/U,texture.getHeight()/V);

    }

    public void update(float delta)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x += delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) position.y += delta *180f;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= delta *180f;
    }

    public void render(float delta){

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        //update(delta);

        rectangle.setPosition(position);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(new Color(0, 1, 0, 0));
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();

        batch.begin();
        batch.draw(region[0][12], position.x, position.y);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    public void dispose(){
        texture.dispose();
        batch.dispose();
        renderer.dispose();
    }


}
