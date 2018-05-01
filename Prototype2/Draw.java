import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Color;
import java.util.List;

public class Draw{

    public static void drawSpaceship(Graphics2D g, Spaceship s){
        s.draw(g);
    }

    public static void drawSpaceships(Graphics2D g, List<Spaceship> s){
        for(Spaceship ship : s){
            ship.draw(g);
        }
    }

    public static void drawSpaceshipChain(Graphics2D g, LinkedDS<Spaceship> ships){
        Node<Spaceship> n = ships.end;
        while(n != null){
            n.num.draw(g);
            n = n.previous;
        }
    }

}
