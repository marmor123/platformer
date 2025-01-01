package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;

import java.util.List;

public class PepseGameManager extends GameManager {

    private static final int SEED = 232; //change
    private static final int CYCLE_LENGTH = 30;
    private static final Float INITIAL_OPACITY = 0f;
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        System.out.println("initialize game");

        Vector2 windowDimensions = windowController.getWindowDimensions();

        GameObject sky = new Sky().create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        Terrain terrain = new Terrain(windowDimensions, SEED);
        System.out.println("terrain created");
        List<Block> blockList = terrain.createInRange(0,(int)windowDimensions.x());

        System.out.println("block list created");
        System.out.println(blockList.size());
        for (Block b : blockList) {
            gameObjects().addGameObject(b, Layer.STATIC_OBJECTS);
        }

        GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        Transition transition = new Transition<Float>(night, night.renderer()::setOpaqueness,
                INITIAL_OPACITY, MIDNIGHT_OPACITY, Transition.CUBIC_INTERPOLATOR_FLOAT);
    }
}
