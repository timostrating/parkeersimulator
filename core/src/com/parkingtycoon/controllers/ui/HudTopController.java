package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdatableController;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.views.ui.HudTopView;

/**
 * This class is responsible for the UI that changes the game.
 */
public class HudTopController extends UpdatableController {

    public HudTopView view;
    private final CompositionRoot root;
    private int oldFloorIndex = -999;  // trigger a update directly


    public HudTopController(Stage stage) {
        root = CompositionRoot.getInstance();
        HudBuildController hudBuildController = new HudBuildController(stage);
        HudCarsController hudCarsController = new HudCarsController(stage);
        HudSettingsController hudSettingsController = new HudSettingsController(stage);

        view = new HudTopView(stage);

        // LEFT
        view.button.addListener((ClickListener) (event, actor) -> root.simulationController.togglePaused());
        view.speedSlider.addListener(event -> {
            root.simulationController.setUpdatesPerSecond((int)(view.speedSlider.getValue()));
            return true;
        });
        view.floorUp.addListener((ClickListener) (event, actor) -> root.floorsController.moveUpOneFloor());
        view.floorDown.addListener((ClickListener) (event, actor) -> root.floorsController.moveDownOneFloor());

        // RIGHT
        view.deletedButton.addListener((ClickListener) (event, actor) -> root.bluePrintsController.toggleDemolishMode() );
        view.buildButton.addListener((ClickListener) (event, actor) -> hudBuildController.show(stage));
        view.carsButton.addListener((ClickListener) (event, actor) -> hudCarsController.show(stage));
        view.settings.addListener((ClickListener) (event, actor) -> hudSettingsController.show(stage));
    }

    @Override
    public void update() {
        if (oldFloorIndex != root.floorsController.getCurrentFloor()) {
            view.floorTitle.setText("Floor: " + root.floorsController.getCurrentFloor());
            oldFloorIndex = root.floorsController.getCurrentFloor();
        }
    }

}