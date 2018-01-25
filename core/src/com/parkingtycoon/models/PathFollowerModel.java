package com.parkingtycoon.models;

import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.pathfinding.PathFinder;

import java.util.List;

/**
 * This Class is responsible for storing all data that is necessary to follow a path.
 */
public abstract class PathFollowerModel extends BaseModel {

    public Vector2 position = new Vector2();
    public List<PathFinder.Node> path;
    public float velocity = 0.1f; // in meters per process

}
