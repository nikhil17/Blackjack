/**
 * Created by nikhil on 3/9/18.
 */
public class SingleDeck {
    private Card[] deck;
    private int cardsUsed;

    public SingleDeck(){
        deck = new Card [52];
        int cardIndex = 0;
        for (SuitEnum suit : SuitEnum.values()){
            for (int i = 1; i <= 13; i ++){
                deck[cardIndex++] = new Card(suit, i);
            }
        }
    }

    public int cardsLeft(){
        return 52 - cardsUsed;
    }

    public Card dealCard(){
        if (cardsUsed == 52){
            // shuffle
            shuffle();
        }
        return deck[0];
    }

    public void shuffle(){

    }
}
