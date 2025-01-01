package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {

    private static RectangleRenderable rectangleRenderable;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        rectangleRenderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        //night.renderer().setOpaqueness(1);
        night.setTag("night");
        return night;
    }
}
