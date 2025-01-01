package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final float GROUND_HEIGHT = (float) 3/4;
    private static final double NOISE_FACTOR = 100.0;
    private static int NOISE_START_POINT = 0;
    private NoiseGenerator noiseGenerator;

    private float groundHeightAtX0;
    private final int seed;

    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = GROUND_HEIGHT *windowDimensions.y();
        this.seed = seed;
        noiseGenerator = new NoiseGenerator(seed, (int)groundHeightAtX0);

    }

    public float groundHeightAt(float x) {

        double noise = noiseGenerator.noise(x,NOISE_FACTOR);
        //System.out.println("noise: " + noise);
        //System.out.println("groundHeightAtX0: " + groundHeightAtX0);
        return groundHeightAtX0 + (float) noise;
    }

    public List<Block> createInRange(int minX, int maxX) {

        List<Block> blocks = new ArrayList<>();

        double newMin =  ( Math.floor((float)minX / Block.SIZE) * Block.SIZE);
        double newMax = ( Math.floor((float)maxX / Block.SIZE) * Block.SIZE);
        if((maxX % Block.SIZE) == 0){
            newMax-=Block.SIZE;
        }

        System.out.println(String.format("newMin: %4.3f, newMax: %4.3f", newMin, newMax));

        for (double x = newMin; x <= newMax ; x+=Block.SIZE) {

            //System.out.println(" groundHeightAt((float) x) = "+groundHeightAt((float) x));
            float y =(float) Math.floor(groundHeightAt((float) x) / Block.SIZE) * Block.SIZE;
            System.out.println(String.format("x: %4.3f, y: %4.3f", x, y));

            for (int i = 0; i < Math.floor(groundHeightAt((float) x) / Block.SIZE); i++) {

                Vector2 topLeftCorner = new Vector2((float) x,y);
                RectangleRenderable rectangleRenderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(topLeftCorner, rectangleRenderable);
                block.setTag("ground");
                blocks.add(block);

                y+=Block.SIZE;
            }

        }
        System.out.println(blocks.size());

        return blocks;
    }
}
