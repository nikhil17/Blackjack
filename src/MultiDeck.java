/**
 * Created by nikhil on 3/9/18.
 */
public class MultiDeck {
    private int noOfDecks;
    private int size;
    private Card [] deck;
    private int cardsUsed;

    public MultiDeck(int noOfDecks){
        this.noOfDecks = noOfDecks;
        this.size = noOfDecks * 52;
        this.deck = new Card[size];
        int cardIndex = 0;
        for(int i = 0; i < noOfDecks; i++){
            for (SuitEnum suit : SuitEnum.values()){
                for (int j = 1; j <= 13; j ++){
                    deck[cardIndex++] = new Card(suit, j);
                }
            }
        }
    }

    public int cardsLeft(){
        return size - cardsUsed;
    }

    public Card dealCard(){
        if (cardsUsed == size){
            // shuffle
            shuffle();
        }
        return deck[0];
    }

    public void printDeck(){
        for (Card c : deck){
            System.out.println(c.toString());
        }
    }

    public void shuffle(){
        for ( int i = size - 1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    public static void main (String []args){
        MultiDeck d = new MultiDeck(2);
        d.printDeck();
        d.shuffle();
        d.printDeck();
    }
}
