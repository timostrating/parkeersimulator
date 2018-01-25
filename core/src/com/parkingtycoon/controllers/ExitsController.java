package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;

import java.util.ArrayList;

/**
 * This class is responsible for providing a Queue that processes the Cars that would like to leave the park.
 */
public class ExitsController extends CarQueuesController {

    private static final int POP_INTERVAL = 20;

    private ArrayList<CarQueueModel> exits = new ArrayList<CarQueueModel>() {{
        add(new CarQueueModel());
    }};

    @Override
    public boolean addToQueue(CarModel car) {
        if (exits.size() == 0)
            return false;

        // add car to random exit queue
        return Random.choice(exits).cars.add(car);
    }

    @Override
    public void update() {
        for (CarQueueModel exit : exits) {
            if (exit.cars.size() == 0)
                continue;

            if (exit.popTimer++ >= POP_INTERVAL)
                exit.cars.remove(0); // goodbye car
        }

    }
}
