/**
 * Class representing a card
 */
public class Card {
    private int number;
    private SuitEnum suit;
    private boolean visibility = false;
    private int value = 0;

    public Card(SuitEnum suit, int number){
        this.number = number;
        this.suit = suit;
        if (number >= 10){
            value = 10;
        }
        else value = number;
    }

    public int getValue(){
        return value;
    }

    public int getNumber(){
        return number;
    }

    public boolean getVisibility(){
        return visibility;
    }

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    public String toString(){
        String card;
        switch (number){
            case 1:
                card = "Ace";
                break;
            case 11:
                card = "Jack";
                break;
            case 12:
                card = "Queen";
                break;
            case 13:
                card = "King";
                break;
            default:
                card = Integer.toString(number);
                break;
        }

        return card +" of " + suit.name();
    }

}
