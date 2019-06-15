package net.loganford.niebreakout;

import net.loganford.niebreakout.states.MainMenu;
import net.loganford.noideaengine.Game;

public class BreakoutGame extends Game {
    public BreakoutGame() {
        super(new MainMenu());
    }

    public static void main(String[] args) {
        BreakoutGame game = new BreakoutGame();
        game.run();
    }
}
