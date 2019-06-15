package net.loganford.niebreakout.states;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.entities.Ball;
import net.loganford.niebreakout.entities.Brick;
import net.loganford.niebreakout.entities.Paddle;
import net.loganford.noideaengine.state.Scene;

public class BreakoutState extends Scene<BreakoutGame> {
    @Override
    public void beginState(BreakoutGame game) {
        super.beginState(game);

        for(int j = 0; j < 9; j++) {
            for (int i = j; i < 18 - j; i++) {
                Brick brick = new Brick();
                brick.setPos(32 + i * 32, 16 + j * 16);
                add(brick);
            }
        }

        Ball ball = new Ball();
        ball.setPos(640/2f, 480-48);
        add(ball);

        Paddle paddle = new Paddle();
        paddle.setPos((640-64)/2f, 480-32);
        add(paddle);
    }
}
