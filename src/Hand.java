import java.util.*;

/**
 * Created by nikhil on 3/9/18.
 */

public class Hand {
    private List<Card> cards;
    private boolean hasAce;
    private int handValue;
    private boolean canSplit;

    public Hand(){
        cards = new ArrayList<>();
        handValue = 0;
        canSplit = false;
        hasAce = false;
    }

    public Hand(Card card){
        cards = new ArrayList<>();
        cards.add(card);
    }


    public boolean canSplit(){
        return (cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue());
    }




    public int getHandValue(){
        return handValue;
    }

    public void clear(){
        cards.clear();
        handValue = 0;
        canSplit = false;
        hasAce = false;
    }

    public void addCard(Card card){
        int cardValue = card.getValue();
        handValue += cardValue;
        cards.add(card);
        if (cardValue == 11){
            hasAce = true;
        }
        if(handValue > 21 && hasAce){
            handValue -= 10;
        }
    }

    public Card removeCard(int index){
        return cards.remove(index);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Card c : cards){
            s.append(c.toString() + "\n");
        }
        s.append("Hand value = "+ handValue);
        return s.toString();

    }

    public String firstCardString(){
        return cards.get(0).toString();
    }

    public static void main (String [] args){
        MultiDeck d = new MultiDeck(2);
        d.shuffle();
        Hand h = new Hand();

//        Card a = new Card(SuitEnum.CLOVES, 1);
//        Card b = new Card(SuitEnum.SPADES, 1);
        h.addCard(new Card(SuitEnum.CLOVES, 1));
        h.addCard(new Card(SuitEnum.SPADES, 1));
        h.addCard(new Card(SuitEnum.SPADES, 1));
        h.addCard(new Card(SuitEnum.CLOVES, 1));

        System.out.println(h.toString());
    }


}
