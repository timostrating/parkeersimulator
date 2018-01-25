package com.parkingtycoon.models;

import com.parkingtycoon.Game;
import com.parkingtycoon.pathfinding.NavMap;

import java.util.ArrayList;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorModel extends BaseModel {

    public enum FloorType {
        ROAD,
        PARKABLE,
        GRASS
    }

    public FloorType[][] tiles = new FloorType[Game.WORLD_WIDTH][];
    public ArrayList<CarModel> cars = new ArrayList<>();
    public CarModel[][] parkedCars = new CarModel[Game.WORLD_WIDTH][];
    public BuildableModel[][] buildings = new BuildableModel[Game.WORLD_WIDTH][];

    public NavMap carNavMap = new NavMap() {
        @Override
        public boolean open(int x, int y, boolean firstNode, boolean lastNode) {
            return tiles[x][y] == FloorType.ROAD || ((lastNode || firstNode) && tiles[x][y] == FloorType.PARKABLE);
        }
    };

    private boolean isCurrentFloor = false;

    public boolean isCurrentFloor() {
        return isCurrentFloor;
    }

    public void setCurrentFloor(boolean currentFloor) {
        isCurrentFloor = currentFloor;
        notifyViews();
    }

}