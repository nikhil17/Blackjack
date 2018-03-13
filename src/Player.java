import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 3/10/18.
 */
public class Player {
    private int money;
    private int number;

    public Player() {
        money = 1000;
    }

    public Player(int number) {
        this();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }


}
