/**
 * Created by nikhil on 3/12/18.
 */
public class Game {
    private int bet;
    private Hand hand;
    private Player player;

    public Game(Player player, Hand hand, int bet){
        this.bet = bet;
        this.hand = hand;
        this.player = player;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Player " +player.getNumber() + "\n");
        s.append("Hand " + hand.toString() + "\n");
        s.append("Wager :" + bet);
        return s.toString();
    }
}
