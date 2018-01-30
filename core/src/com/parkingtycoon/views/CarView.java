package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.helpers.pathfinding.PathFinder;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;

import java.util.List;


/**
 * This Class is responsible for showing an animated sprite of a drivable car.
 */
public class CarView extends AnimatedSpriteView {

    private List<PathFinder.Node> path;
    private Color pathColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
    private float extra = Random.randomInt(0, 10) / 100f;
    private AABB aabb;
    private boolean waiting, inQueue, disappearing;

    public CarView(float appearX, float appearY) {
        super("sprites/cars/pontiac", true);
        spritePosition.set(appearX, appearY);
    }

    @Override
    public void start() {
        super.start();
        sprite.setOrigin(.5f, .5f);
        sprite.setPosition(spritePosition.x, spritePosition.y);
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof CarModel) {

            CarModel car = (CarModel) model;

            path = car.getCurrentPath();
            aabb = car.aabb;
            waiting = car.waitingOn != null;
            inQueue = car.waitingInQueue;
            disappearing = car.isDisappeared();

            spritePosition.set(car.position);
            IsometricConverter.normalToIsometric(spritePosition);

            if (spriteModel != null && !car.direction.isZero()) {

                int frame = 33 - (int) ((car.direction.angle() / 360.01f) * 33) - 1;

                setRegion(spriteModel.frames[frame]);
            }

        }

    }

    @Override
    public void preRender() {
        if (spriteModel.speedMultiplier <= 3) // smooth transition
            sprite.setOriginBasedPosition(
                    (spritePosition.x + sprite.getX() * 3) / 4f,
                    (spritePosition.y + sprite.getY() * 3) / 4f
            );
        else
            sprite.setOriginBasedPosition(spritePosition.x - 1.5f, spritePosition.y - 1.5f);

        if (disappearing) {
            float alpha = Math.max(0, sprite.getColor().a - .05f * spriteModel.speedMultiplier);
            sprite.setColor(1, 1, 1, alpha);
            if (alpha == 0)
                hide();
        }
    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.RED);

        if (aabb != null)
            shapeRenderer.rect(aabb.center.x - aabb.halfSize.x, aabb.center.y - aabb.halfSize.y, aabb.halfSize.x * 2, aabb.halfSize.y * 2);

        if (path != null) {
            shapeRenderer.setColor(pathColor);
            PathFinder.Node prevN = null;
            Vector2 prevIso = new Vector2();
            Vector2 iso = new Vector2();

            for (PathFinder.Node n : path) {
                iso.set(n.actualX, n.actualY);
                IsometricConverter.normalToIsometric(iso);
                if (prevN != null) {
                    shapeRenderer.line(prevN.actualX + extra, prevN.actualY + extra, n.actualX + extra, n.actualY + extra);

                    shapeRenderer.line(prevIso.x + extra, prevIso.y + extra, iso.x + extra, iso.y + extra);

                    shapeRenderer.line(n.actualX - .1f, n.actualY - .1f, n.actualX + .1f, n.actualY + .1f);
                    shapeRenderer.line(n.actualX - .1f, n.actualY + .1f, n.actualX + .1f, n.actualY - .1f);
                }
                prevN = n;
                prevIso.set(iso);
            }

        }

        shapeRenderer.setColor(waiting ? Color.RED : Color.GREEN);
        shapeRenderer.line(sprite.getX(), sprite.getY(), sprite.getX(), sprite.getY() + sprite.getHeight());
        shapeRenderer.setColor(inQueue ? Color.BLACK : Color.WHITE);
        shapeRenderer.line(sprite.getX() + sprite.getWidth(), sprite.getY(), sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());

//        super.debugRender(shapeRenderer);
    }
}

