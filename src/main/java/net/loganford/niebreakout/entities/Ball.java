package net.loganford.niebreakout.entities;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.states.BreakoutState;
import net.loganford.noideaengine.audio.Audio;
import net.loganford.noideaengine.shape.SweepResult;
import net.loganford.noideaengine.state.entity.Entity;
import net.loganford.noideaengine.utils.math.MathUtils;
import org.joml.Vector3f;

public class Ball extends Entity<BreakoutGame, BreakoutState> {
    private Vector3f velocity = new Vector3f();
    private Vector3f nextPosition = new Vector3f();

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
            float direction = MathUtils.randRangeF(MathUtils.PI/4f, 3*MathUtils.PI/4f);
            //Set initial velocity to be 128 pixels per second
            velocity.set(128, 0, 0).rotateZ(-direction);
        });
    }

    @Override
    public void step(BreakoutGame game, BreakoutState scene, float delta) {
        super.step(game, scene, delta);

        if(velocity.lengthSquared() != 0) {
            //Obtain the next position of the ball. We need to multiply by delta time to account for varying frame rates
            nextPosition.set(velocity).mul(delta/1000f);
            //Find if there are any collisions between this position and the desired position
            SweepResult result = sweep(nextPosition, Solid.class);
            move(result);
            if(result.collides()) {
                if(result.getEntity() instanceof Brick) {
                    //Handle collision with brick
                    result.getEntity().destroy();
                    //"Bounce" the velocity vector
                    result.reflect(velocity);
                    //Make ball move five percent faster
                    velocity.mul(1.05f);
                    bounceSound.play();
                }
                else if(result.getEntity() instanceof Paddle && velocity.y > 0) {
                    //Handle collision with paddle
                    Paddle paddle = (Paddle) result.getEntity();
                    float offset = (getX() - paddle.getX()) / paddle.getWidth();
                    float angle = MathUtils.lerp(3*MathUtils.PI/4f, MathUtils.PI/4f, MathUtils.clamp(0, 1, offset));
                    velocity.set(velocity.length(), 0f, 0f).rotateZ(-angle);
                    bounceSound.play();
                }
            }
        }

        //Bounce the ball along the three sides of the screen
        if(getY() < 0 && velocity.y < 0) {
            velocity.y *= -1;
        }
        if(getX() < 0 && velocity.x < 0) {
            velocity.x *= -1;
        }
        if(getX() > scene.getWidth() && velocity.x > 0) {
            velocity.x *= -1;
        }

        //Ball is off the screen
        if(getY() > scene.getHeight() + 20) {
            //Destroy ball
            boomSound.play();
            destroy();
            //Add a new one
            Ball ball = new Ball();
            ball.setPos(scene.getWidth()/2f, scene.getHeight()-48);
            scene.add(ball);
        }
    }
}
