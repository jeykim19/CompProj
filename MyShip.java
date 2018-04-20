import java.lang.Math.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

public class MyShip extends Spaceship{
    int lives;

    public MyShip(Pair[] vertices){
        super(vertices);
        lives = 5;
    }

    public void rotate(double theta, double time){
        //Theta - how many radians you rotate per second
        AffineTransform transformation = new AffineTransform();
        //convert to radians
        theta = theta*Math.PI/180;
        Pair centroid = findCentroid(findVertices(spaceship));
        transformation.rotate(theta*time, centroid.x, centroid.y);
    }
}