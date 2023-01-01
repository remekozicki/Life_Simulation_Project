package Project;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
//         ?
//        launch();






        ArrayList<Grass> grasses = new ArrayList<>();
        Grass g1 = new Grass(new Vector2d(1, 2), 12);
        Grass g2 = new Grass(new Vector2d(1, 3), 12);

        grasses.add(g1);
        grasses.add(g2);

        Grass g3 = new Grass(new Vector2d(1, 2), 12);

        System.out.println(grasses);
        // maybe something like this
        grasses.remove(g3);

        System.out.println(2+2);
    }


}