import java.util.*;

/**
 * Created by nikhil on 3/9/18.
 */
public class BlackJack {
    private int noOfPlayers;
    private final int NUMBER_OF_DECKS = 2;
    MultiDeck deck;
    Player dealer;
    Hand dealerHand;
    List<Player> players;
    List<Game> games;
    Scanner scanner;

    public BlackJack(){
        initialize();
        deck.shuffle();
        getPlayerBets();
        startGame();
    }

    public void initialize(){
        System.out.println("Welcome to Blackjack!");

        scanner = new Scanner(System.in);
        do {
            System.out.print("Choose the number of players: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }
            noOfPlayers = scanner.nextInt();
            if (noOfPlayers > 4){
                System.out.println("Sorry, the table is too small for more than 4 players to play at a time. Please try again.");
            }

            if (noOfPlayers < 0){
                System.out.println("Sorry, we can\'t play with a negative number of people. Please try again.");
            }
        }
        while(noOfPlayers < 0 || noOfPlayers > 4);

        System.out.printf("Initializing with %d players.\n", noOfPlayers);
        System.out.println();

        deck = new MultiDeck(NUMBER_OF_DECKS);
        dealer = new Player();

        games = new ArrayList<>();

        //initialize player objects
        players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++){
            players.add(new Player(i));
        }

    }

    public void getPlayerBets(){

        System.out.println("Collecting player bets: ");

        for (Player player : players){
            int currentPlayerMoney = player.getMoney();
            int bet;

            do {
                System.out.println("Player " + player.getNumber());
                System.out.println("Money : " + currentPlayerMoney);
                System.out.println("Choose an amount to bet :");

                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a valid integer.\n", input);
                }
                bet = scanner.nextInt();

                if (bet > currentPlayerMoney){
                    System.out.println("Sorry, you don\'t have that much money to bet. Please try again.");
                } else if (bet < 0){
                    System.out.println("Please enter a positive integer");
                } else{
                    System.out.println("Your bet of "+ bet + " was accepted.\n");
                    player.setMoney(currentPlayerMoney - bet);
                    games.add(new Game(player, new Hand(), bet));
                }

            } while(bet < 0 || bet > currentPlayerMoney);

        }
    }

    public void startNewGame(){
        games.clear();
        //iterate through players and see if they want to continue
    }

    public Hand createDealerHand(){
        Hand h = new Hand();
        h.addCard(deck.dealCard());
        h.addCard(deck.dealCard());
        return h;
    }


    public void startGame(){
        dealerHand = createDealerHand();
        boolean dealerBlackjack = false;

        //dealer got a blackjack
        if (dealerHand.getHandValue() == 21){
            System.out.println("The dealer got a Blackjack Natural!");
            System.out.println("Dealer's cards: ");
            System.out.println(dealerHand.toString());
            dealerBlackjack = true;
        }

        for (ListIterator<Game> gameIterator = games.listIterator(); gameIterator.hasNext();){
            System.out.println("Dealer is showing \n" + dealerHand.firstCardString() + "\n");

            Game g = gameIterator.next();
            Player player = g.getPlayer();
            Hand hand = g.getHand();
            hand.addCard(deck.dealCard());
            hand.addCard(deck.dealCard());
            System.out.println("Player "+ player.getNumber() +"\'s cards :\n" + g.getHand().toString() + "\n");
            if (hand.getHandValue() == 21){

                System.out.println("Player " + player.getNumber() + " got a natural Blackjack!");
                if (!dealerBlackjack){
                    payOut(g, 2);
                    gameIterator.remove();
                    continue;
                } else{
                    int bet = g.getBet();
                    System.out.println("Woop. Looks like its a tie. You win some, you lose some, and you draw some.");
                    System.out.println("Here's your money back: $"+ bet);
                    player.setMoney(player.getMoney()+ bet);

                }
                gameIterator.remove();

            } else {
                // if this player's hand is < 21
                if (dealerBlackjack){
                    // dealer got a blackjack but this player didn't
                    System.out.println("Player "+ player.getNumber()+ " lost... Try again next time! ");
                    System.out.println("Player money left : $" + player.getMoney());
                    gameIterator.remove();
                    continue;
                }

                else if (hand.canSplit() && player.getMoney() > g.getBet()){
                    // handle splitting
                    split(g, player, hand, gameIterator);

                } else if (player.getMoney() > g.getBet()){
                    // double down
                    doubleDown(g, player, hand, gameIterator);

                }
            }

        }
        allPlayersHitOrStand();
    }

    public void split(Game g, Player player, Hand hand, ListIterator gameIterator){
        System.out.println("You got 2 of a kind! If you choose to split, a wager equal " +
                "to your original bet will be placed on the new hand. " +
                "Do you want to split? (1 = Yes, 0 = No)");
        int result;
        do {
            //check for next number
            String errorMessage = ("Sorry, we don't know what that number means. \n" +
                    "Please enter 1 if you want to split and 0 if not.");
            result = getIntegerInputFromUser(scanner, errorMessage);

        } while (result != 0 || result != 1);

        if (result == 1){
            // if the player wants to split, create a new game and add it to the list
            int oldBet = g.getBet();
            gameIterator.add(new Game(player, new Hand(hand.removeCard(1)), oldBet));
            player.setMoney(player.getMoney() - oldBet);
        }
    }

    public void doubleDown(Game g, Player player, Hand hand, ListIterator gameIterator){
        System.out.println("Do you want to double down? (0 = No, 1 = Yes)");
        int result;
        do {
            //check for next number
            String errorMessage =("Sorry, we don't know what that number means. \n" +
                    "Please enter 1 if you want to double down and 0 if not.");
            result = getIntegerInputFromUser(scanner, errorMessage);
            System.out.println(result);

        } while (result != 0 && result != 1);

        if (result == 1){
            int oldBet = g.getBet();
            g.setBet(oldBet * 2);
            player.setMoney(player.getMoney() - oldBet);
            Card newCard = deck.dealCard();
            System.out.println("You drew " + newCard.toString());
            hand.addCard(newCard);

            int value = hand.getHandValue();
            if (value == 21){
                // doesn't execute if dealer got a blackjack
                payOut(g, 1.5);
                gameIterator.remove();
            }
        }
    }


    public void allPlayersHitOrStand(){
        for (ListIterator<Game> gameIterator = games.listIterator(); gameIterator.hasNext();){
            // let each player hit until they break or stand for each game
            Game g = gameIterator.next();
            Player player = g.getPlayer();
            Hand hand  = g.getHand();
            boolean playerStand = false;
            while (!playerStand){
                System.out.println(hand.toString() + "\n");
                System.out.println("Press 1 to hit and 0 to stand ");
                int result;
                do {
                    //check for next number
                    String errorMessage =("Sorry, we don't know what that number means. \n" +
                            "Please enter 1 if you want to hit  and 0 if you want to stand.");
                    result = getIntegerInputFromUser(scanner, errorMessage);
                    System.out.println(result);

                } while (result != 0 && result != 1);

                if (result == 1){
                    // hit
                    Card newCard = deck.dealCard();
                    System.out.println("You drew the " + newCard.toString() + "\n");
                    hand.addCard(newCard);

                    if (hand.getHandValue() > 21){
                        System.out.println("You busted. Try again next time. ");
                        System.out.println("Player " + player.getNumber() + " money left : $" + player.getMoney());
                        gameIterator.remove();
                        continue;
                    }else if (hand.getHandValue() == 21){
                        payOut(g, 1.5);
                    }

                }else{
                    playerStand = true;
                }
            }

        }


        dealersTurn();
    }

    public void dealersTurn(){

        System.out.println("All players have bust or stood. Dealer's turn. ");

        while (dealerHand.getHandValue() < 16){
            // hit the dealer
            Card newCard = deck.dealCard();
            System.out.println("Dealer drew the " + newCard.toString() + "\n");
            dealerHand.addCard(newCard);
        }

        if (dealerHand.getHandValue() > 21){
            dealerBust();
        }


    }

    public void dealerBust(){
        System.out.println("The dealer bust! All players get their payouts. \n");

        for (Game g : games){
            payOut(g, 1.5);

        }

        newRound();
    }

    public void newRound(){
        // ask players if they want to keep playing
        //remove players that have no money
        // shuffle the deck and take bets again
        games = new ArrayList<>();

        System.out.println("Starting new round");
        deck.shuffle();
        getPlayerBets();
        startGame();
    }

    public void payOut(Game g, double factor){
        Player p = g.getPlayer();
        int bet = g.getBet();
        int payout = (int)(bet * factor);
        System.out.println("You won $"+ payout +" on your bet of $" + bet + "!!!");
        p.setMoney(p.getMoney() + payout);
        System.out.println("Player " + p.getNumber() + " 's money : $"+ p.getMoney()+ "\n");
    }

    public void endGame(){

    }

    /**
     * gets integer input from user and checks if the input is 1 or 0
     * @param scanner
     * @param errorMessage
     */
    public int getIntegerInputFromUser(Scanner scanner, String errorMessage){
        int result;
        while (!scanner.hasNextInt()) {
            String input = scanner.next();
            System.out.printf("\"%s\" is not a valid number.\n", input);
        }
        result = scanner.nextInt();

        if (result != 0 && result != 1){
            System.out.println(errorMessage);
        }
        return result;
    }


    public static void main(String [] args){
        new BlackJack();
    }
}
