public class MyShip extends Spaceship{

    public MyShip(Pair[] vertices){
        super(vertices);
    }

    public void rotate(double theta, double time){
        //Theta - how many radians you rotate per second
        AffineTransform transformation = new AffineTransform();
        //convert to radians
        theta = theta*Math.pi/180;
        Pair centroid = findCentroid(findVertices(spaceship));
        transformation.rotate(theta*time, centroid.x, centroid.y);
    }
}