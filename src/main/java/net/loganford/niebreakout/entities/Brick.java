package net.loganford.niebreakout.entities;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.niebreakout.states.BreakoutState;
import net.loganford.noideaengine.graphics.Image;
import net.loganford.noideaengine.graphics.Sprite;
import net.loganford.noideaengine.state.entity.Entity2D;

public class Brick extends Entity2D<BreakoutGame, BreakoutState> {

    @Override
    public void onCreate(BreakoutGame game, BreakoutState scene) {
        super.onCreate(game, scene);
        Image image = game.getImageManager().get("brick.png");
        setSprite(new Sprite(image));
        createMaskFromSprite();
    }
}
