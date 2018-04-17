public class OtherShip extends Spaceship{
    boolean hasBeenCaptured = false;

    Pair fulcrum; //This is the point to which the front of the ship will attach (the end of the next ship in the line)

    public OtherShip(Pair[] vertices){
        super(vertices);
    }

    public void captured(){

    }
}