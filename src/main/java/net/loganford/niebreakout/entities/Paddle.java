package net.loganford.niebreakout.entities;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.states.BreakoutState;
import net.loganford.noideaengine.Input;
import net.loganford.noideaengine.graphics.Image;
import net.loganford.noideaengine.graphics.Sprite;
import net.loganford.noideaengine.state.entity.Entity2D;

public class Paddle extends Entity2D<BreakoutGame, BreakoutState> {

    //Units are in pixels per second
    private float velocityX = 0;
    private float deltaVelocity = 3000;
    private float maxVelocity = 800;

    @Override
    public void onCreate(BreakoutGame game, BreakoutState scene) {
        super.onCreate(game, scene);
        Image image = game.getImageManager().get("paddle.png");
        setSprite(new Sprite(image));
        createMaskFromSprite();
    }

    @Override
    public void step(BreakoutGame game, BreakoutState scene, float delta) {
        super.step(game, scene, delta);

        //Handle paddle movement
        if(game.getInput().keyDown(Input.KEY_A) || game.getInput().keyDown(Input.KEY_LEFT)) {
            velocityX = Math.max(-maxVelocity, velocityX - deltaVelocity * delta/1000f);
        }
        else if(game.getInput().keyDown(Input.KEY_D) || game.getInput().keyDown(Input.KEY_RIGHT)) {
            velocityX = Math.min(maxVelocity, velocityX + deltaVelocity * delta/1000f);
        }
        else {
            if(velocityX > 0) {
                velocityX = Math.max(0, velocityX - deltaVelocity * delta/1000f);
            }
            else if(velocityX < 0) {
                velocityX = Math.min(0, velocityX + deltaVelocity * delta/1000f);
            }
        }

        setX(getX() + velocityX * delta/1000f);

        if(getX() < 0) {
            setX(0);
            velocityX = 0;
        }

        if(getX() > 640 - 64) {
            setX(640 - 64);
            velocityX = 0;
        }
    }

    public float getWidth() {
        return getSprite().getFrames().get(0).getImage().getWidth();
    }
}
