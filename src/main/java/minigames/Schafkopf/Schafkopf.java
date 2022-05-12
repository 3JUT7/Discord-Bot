package minigames.Schafkopf;


import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;

import java.util.*;

public class Schafkopf {
    Member[] player;
    int[][] cards = new int[4][8];

    public static List<Schafkopf> schafkopfGames = new ArrayList<>();


    public Schafkopf(Member p0, Member p1, Member p2, Member p3) {

        //the card numbering works like this: the tens place describes the type so if it's Eichel, Gras, Herz or Schell
        //and the Last number says wich number it is so 0 is seven 1 is eigth 2 is nine 3 is Unter and so on

        player = new Member[]{p0, p1, p2, p3};

        ArrayList<Integer> cardNumbers = new ArrayList<>();

        Random rand = new Random();
        ArrayList<Integer> availableCards = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 27, 30, 31, 32, 33, 34, 35, 36, 37));

        Collections.shuffle(availableCards);

        for (int i = 0; i < 32; i++) {

            cards[i/8][i - (i/8)*8] = availableCards.get(i);

        }





        for (int[] c : cards) {
            System.out.println(Arrays.toString(c));
        }


        schafkopfGames.add(this);
    }
}
