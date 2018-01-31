package com.parkingtycoon.controllers;

import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.CarQueueModel;
import com.parkingtycoon.views.queue.ArrowView;
import com.parkingtycoon.views.queue.BarrierView;
import com.parkingtycoon.views.queue.QueueSignView;

/**
 * This class is responsible for providing a Queue that processes the new Cars that would like to park.
 */
public class EntrancesController extends CarQueuesController {

    public EntrancesController() {
        popInterval = 50;
    }

    @Override
    protected boolean nextAction(CarModel car) {
        int fromX = car.queue.x + CoordinateRotater.rotate(0, 3, 1, 3, car.queue.angle);
        int fromY = car.queue.y + CoordinateRotater.rotate(1, 3, 0, 3, car.queue.angle);
        return CompositionRoot.getInstance().carsController.parkCar(car, fromX, fromY);
    }

    public CarQueueModel createEntrance(int x, int y, int angle, int floor) {
        CarQueueModel entrance = new CarQueueModel(x, y, angle, floor);
        QueueSignView queueSign = new QueueSignView("enter" + angle % 2);
        queueSign.show();
        entrance.registerView(queueSign);
        BarrierView barrier = new BarrierView();
        barrier.show();
        entrance.registerView(barrier);
        ArrowView arrow = new ArrowView();
        entrance.registerView(arrow);
        arrow.show();
        queues.add(entrance);
        return entrance;
    }

}
