package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controllers.C_Asteroide;
import com.mygdx.game.Controllers.C_Bala;
import com.mygdx.game.Controllers.C_Potion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Objects.Asteroide;
import com.mygdx.game.Objects.Nave;

import javax.swing.Renderer;

public class PlayScreen implements Screen {

    private MyGdxGame game;
    private SpriteBatch batch;

    private Texture texture;
    private ShapeRenderer renderer;

    private OrthographicCamera camera;
    private Viewport viewport;

    private BitmapFont texto;
    private Nave nave;

    private C_Asteroide asteroides;
    private C_Potion potions;

    public static int puntos;
    private long startTime = 0;

    public PlayScreen(MyGdxGame game)
    {
        this.game = game;
        batch = new SpriteBatch();

        texture = new Texture("nebula.jpg");
        renderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);

        viewport = new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, camera);
        texto = new BitmapFont();

        texto.getData().setScale(3);
        texto.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        nave = new Nave(viewport);
        asteroides = new C_Asteroide(viewport);

        potions = new C_Potion(viewport);
        puntos = 0;

        startTime = TimeUtils.millis();

    }
    @Override
    public void show() {

    }

    private void update()
    {
        if(!nave.isState())
        {
            Nave.vidas = 3;
            puntos = 0;
            game.setScreen(new PlayScreen(new MyGdxGame()));
        }

        if (TimeUtils.timeSinceMillis(startTime) > 20000) {
            System.out.println("SIII");
            startTime = TimeUtils.millis();

            if(Asteroide.velocity < 500)
                Asteroide.velocity += 50;

            if(C_Asteroide.time > 1000)
                C_Asteroide.time -= 500;

            System.out.println("vel: " + Asteroide.velocity);
            System.out.println("time: " + C_Asteroide.time);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        viewport.apply();
        camera.update();
        update();

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(texture,0,0);

        texto.draw(batch,"VIDAS: " + nave.getVidas() ,10, MyGdxGame.HEIGHT - 10);
        texto.draw(batch,"BALAS: " + C_Bala.cantidad[Nave.idBala] ,10, MyGdxGame.HEIGHT - 80);
        texto.draw(batch,"PUNTOS: " + puntos ,MyGdxGame.WIDTH - 290, MyGdxGame.HEIGHT - 10);

        batch.end();

        nave.render(delta);
        asteroides.render(delta, viewport, nave);
        potions.render(delta, viewport, nave);



    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
