package net.loganford.niebreakout.states;

import net.loganford.niebreakout.BreakoutGame;
import net.loganford.noideaengine.Input;
import net.loganford.noideaengine.graphics.Font;
import net.loganford.noideaengine.graphics.Renderer;
import net.loganford.noideaengine.state.GameState;

public class MainMenu extends GameState<BreakoutGame> {

    private Font menuFont;

    @Override
    public void beginState(BreakoutGame game) {
        super.beginState(game);
        setStretch(true);
        getView().setWidth(640);
        getView().setHeight(480);

        menuFont = game.getFontManager().get("roboto");
    }

    @Override
    public void step(BreakoutGame game, float delta) {
        super.step(game, delta);

        if(game.getInput().keyPressed(Input.KEY_ENTER)) {
            game.setState(new BreakoutState());
        }
    }

    @Override
    public void render(BreakoutGame game, Renderer renderer) {
        super.render(game, renderer);

        String headerText = "Breakout Example";
        menuFont.getColor().set(1f, 1f, 1f, 1f);
        menuFont.setScale(1f);
        float headerTextWidth = menuFont.getWidth(headerText);
        menuFont.print(renderer, (getView().getWidth() - headerTextWidth)/2, 32, headerText);

        String subtitleText = "Press enter to begin";
        menuFont.getColor().set(1f, 0f, 0f, 1f);
        menuFont.setScale(.5f);
        float subtitleTextWidth = menuFont.getWidth(headerText);
        menuFont.print(renderer, (getView().getWidth() - subtitleTextWidth)/2, getView().getHeight() - 128, subtitleText);
    }
}
