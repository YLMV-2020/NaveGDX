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
import com.mygdx.game.Controllers.C_Asteroide;
import com.mygdx.game.Controllers.C_Bala;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Nave {

    private SpriteBatch batch;
    private Texture texture;

    private boolean state;
    public static int vidas;

    private Rectangle rectangle;
    private Vector2 position;

    private ShapeRenderer renderer;
    private Viewport viewport;

    private C_Bala balas;
    public static float velocity;

    public static int idBala;

    public Nave(Viewport viewport)
    {
        this.viewport = viewport;
        batch = new SpriteBatch();

        texture = new Texture("nave.png");
        state = true;

        vidas = 3;
        rectangle = new Rectangle(10,10, texture.getWidth(), texture.getHeight());

        position = new Vector2(10,10);
        renderer = new ShapeRenderer();

        velocity = 180.0f;
        balas = new C_Bala();

        idBala = 0;
    }

    private void checkPosition()
    {
        float width = MyGdxGame.WIDTH - texture.getWidth();
        float height = MyGdxGame.HEIGHT - texture.getHeight();

        if(position.x > width ) position.x = width;
        if(position.x < 0) position.x = 0;
        if(position.y > height) position.y = height;
        if(position.y < 0) position.y = 0;
    }

    private void keyPressed()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && C_Bala.cantidad[idBala] > 0)
        {
            C_Bala.cantidad[idBala]--;
            balas.addBala(viewport, position);
            Nave.idBala = C_Bala.sort();
        }
    }


    private void update(float delta)
    {
        if(vidas <= 0) {
            state = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= delta * velocity;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) position.x += delta * velocity;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) position.y += delta * velocity;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= delta * velocity;

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

        balas.render(delta, viewport);
        keyPressed();
    }

    public void dispose(){
        texture.dispose();
        batch.dispose();
        renderer.dispose();
    }

    public int getVidas() { return vidas; }
    public Rectangle getRectangle() { return rectangle; }
    public Texture getTexture() { return texture; }
    public Vector2 getPosition() { return position; }
    public boolean isState() { return state; }

    public void setVidas(int vidas) { this.vidas = vidas; }

}
