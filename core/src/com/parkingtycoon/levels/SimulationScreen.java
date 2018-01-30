package com.parkingtycoon.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.InputController;
import com.parkingtycoon.controllers.ui.HudController;
import com.parkingtycoon.controllers.SimulationController;


/**
 * This Class is responsible for setting up the 
 */
public class SimulationScreen implements Screen {

    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private HudController hud;

    private InputController.ScrollListener zoomer = amount -> {
        worldCamera.zoom = Math.max(1, Math.min(18, worldCamera.zoom + amount * worldCamera.zoom / 16f));
        return true;
    };

    public SimulationScreen(Game game) {

        root = CompositionRoot.getInstance();
        root.simulationController.startSimulation();

        this.game = game;

        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);

        root = CompositionRoot.getInstance();
        root.renderController.setMainCamera(worldCamera);

        hud = root.hudController;

        CompositionRoot.getInstance().inputController.scrollListeners.add(zoomer);

    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {

        root.renderController.preRender();      // preRender views
        game.batch.setProjectionMatrix(worldCamera.combined);
        game.shapeRenderer.setProjectionMatrix(worldCamera.combined);
        root.renderController.render();         // render views
        hud.render();                           // render UI

        SimulationController simulationController = CompositionRoot.getInstance().simulationController;

        Gdx.graphics.setTitle("Parking Tycoon (fps: " + Gdx.graphics.getFramesPerSecond() + ") (updates/sec: " + simulationController.getUpdatesPerSecond() + ") (real updates/sec: " + simulationController.realUpdatesPerSecond + ")");
    }

    @Override
    public void resize(int width, int height) {
        worldCamera.setToOrtho(false, 10f * width / height, 10);
        hud.resize(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        hud.dispose();
    }
  
}