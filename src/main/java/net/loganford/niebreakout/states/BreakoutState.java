package net.loganford.niebreakout.states;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.entities.Ball;
import net.loganford.niebreakout.entities.Brick;
import net.loganford.niebreakout.entities.Paddle;
import net.loganford.noideaengine.state.Scene;
import net.loganford.noideaengine.state.entity.systems.RegisterSystem;
import net.loganford.noideaengine.state.entity.systems.UnregisterSystem;
import net.loganford.noideaengine.state.entity.systems.collision.NaiveCollisionSystem;
import net.loganford.noideaengine.state.entity.systems.collision.SpacialPartitionCollisionSystem;

@UnregisterSystem(SpacialPartitionCollisionSystem.class)
@RegisterSystem(NaiveCollisionSystem.class)
public class BreakoutState extends Scene<BreakoutGame> {
    @Override
    public void beginState(BreakoutGame game) {
        super.beginState(game);
        setStretch(true);
        getView().setWidth(640);
        getView().setHeight(480);

        for(int j = 0; j < 9; j++) {
            for (int i = j; i < 18 - j; i++) {
                Brick brick = new Brick();
                brick.setPos(32 + i * 32, 16 + j * 16);
                add(brick);
            }
        }

        Ball ball = new Ball();
        ball.setPos(getWidth()/2f, getHeight()-48);
        add(ball);

        Paddle paddle = new Paddle();
        paddle.setPos((getWidth()-64)/2f, getHeight()-32);
        add(paddle);
    }
}
