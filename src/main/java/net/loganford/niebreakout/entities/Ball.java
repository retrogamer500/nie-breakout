package net.loganford.niebreakout.entities;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.states.BreakoutState;
import net.loganford.noideaengine.audio.Audio;
import net.loganford.noideaengine.state.entity.Entity2D;
import net.loganford.noideaengine.utils.math.MathUtils;
import org.joml.Vector2f;

public class Ball extends Entity2D<BreakoutGame, BreakoutState> {
    private Vector2f velocity = new Vector2f();
    private float speed = 0;

    private Audio boomSound;
    private Audio bounceSound;


    @Override
    public void onCreate(BreakoutGame game, BreakoutState scene) {

        super.onCreate(game, scene);
        //Load sounds
        boomSound = game.getAudioManager().get("boom.ogg");
        bounceSound = game.getAudioManager().get("bounce.ogg");
        //Set sprite, and create a rectangular collision mask based on the loaded sprite
        setSprite(game.getSpriteManager().get("ball.png"));
        createMaskFromSprite();
        //Create an event that will happen in one second that will set the direction and velocity of the ball
        getAlarms().add(1000, ()-> {
            speed = 50;
            float direction = MathUtils.randRangeF(MathUtils.PI/4f, 3*MathUtils.PI/4f);
            velocity.set((float)(Math.cos(direction)), (float)(-Math.sin(direction)));
        });
    }

    @Override
    public void step(BreakoutGame game, BreakoutState scene, float delta) {
        super.step(game, scene, delta);

        if(speed != 0) {
            //Move X
            {
                float deltaX = velocity.x * speed * delta / 100f;
                Brick brick = getCollisionAt(Brick.class, getX() + deltaX, getY());
                if (brick == null && getX() + deltaX > 0 && getX() + deltaX < 640) {
                    setX(getX() + deltaX);
                } else {
                    velocity.x *= -1;
                    bounceSound.play();
                    if (brick != null) {
                        brick.destroy();
                    }
                }
            }

            //Move Y
            {
                float deltaY = velocity.y * speed * delta / 100f;
                Brick brick = getCollisionAt(Brick.class, getX(), getY() + deltaY);
                if (brick == null && getY() + deltaY > 0) {
                    setY(getY() + deltaY);
                } else {
                    velocity.y *= -1;
                    bounceSound.play();
                    if(brick != null) {
                        brick.destroy();
                    }
                }
            }

            //Handle collisions with paddle
            Paddle paddle = getCollision(Paddle.class);
            if(paddle != null && velocity.y > 0) {
                float offset = (getX() - paddle.getX()) / paddle.getWidth();
                float angle = MathUtils.lerp(3*MathUtils.PI/4f, MathUtils.PI/4f, MathUtils.clamp(0, 1, offset));
                velocity.set((float)(Math.cos(angle)), (float)(-Math.sin(angle)));
                bounceSound.play();
            }
        }

        if(getY() > 650) {
            //Ball is off the screen
            //Destroy ball
            boomSound.play();
            destroy();
            //Add a new one
            Ball ball = new Ball();
            ball.setPos(640/2f, 480-48);
            scene.add(ball);
        }
    }
}
