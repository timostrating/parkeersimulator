package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.parkingtycoon.interfaces.IUpdatable;

import java.util.ArrayList;

public class SimulationController extends BaseController {

    public final static int REAL_TIME_UPDATES_PER_SECOND = 20;

    private ArrayList<IUpdatable> updatables = new ArrayList<>();
    private int updatesPerSecond = REAL_TIME_UPDATES_PER_SECOND;
    private long updates;
    private float deltaTime;


    public boolean registerUpdatable(IUpdatable updatable) {
        return updatables.add(updatable);
    }

    public boolean unregisterUpdatable(IUpdatable updatable) {
        return updatables.remove(updatable);
    }

    public void update() {

        float timeStep = 1 / (float) updatesPerSecond;
        deltaTime += Math.min(Gdx.graphics.getDeltaTime(), .25f);

        while (deltaTime >= timeStep) {

            updates++;

            for (IUpdatable u : updatables)
                u.update();

            deltaTime -= timeStep;
        }
    }

    public int getUpdatesPerSecond() {
        return updatesPerSecond;
    }
}
