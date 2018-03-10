import java.util.*;

/**
 * Created by nikhil on 3/9/18.
 */

public class Hand {
    private List<Card> cards = new ArrayList<Card>();
    boolean hasSplit = false;
    boolean hasAce = false;
    private int handValue;

    public Hand(List<Card> cards){
        this.cards = cards;
        handValue = 0;
        for (Card c: cards){
            int value = c.getValue();
            if (value == 1){
                hasAce = true;
            }
            handValue += value;
        }

    }

    public void addCard(Card card){
        handValue += card.getValue();
        cards.add(card);
    }

    public int getHandSize(){
        return cards.size();
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Card c : cards){
            s.append(c.toString() + "\n");
        }
        s.append("Hand value = "+ handValue);
        return s.toString();

    }


}
