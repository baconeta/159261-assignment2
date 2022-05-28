package space_resistance.ui.screens.menu;

import space_resistance.assets.Colors;
import space_resistance.assets.FontBook;
import space_resistance.assets.SoundEffects;
import space_resistance.ui.components.Button;
import tengine.geom.TPoint;
import tengine.graphics.components.text.TLabel;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class Credits extends SubMenu {
    public Credits(Consumer<SubmenuOption> submenuSelectionNotifier) {
        super(submenuSelectionNotifier);

        TLabel title = new TLabel("credits");
        title.setColor(Colors.Text.PRIMARY);
        title.setFont(FontBook.shared().titleFont());
        title.setOrigin(new TPoint(220, 120));

        initContent();

        Button close = new Button("Close");
        close.setState(Button.State.FOCUSSED);
        close.setOrigin(new TPoint(290, 780));

        addAll(title, close);
    }

    public void initContent() {
        TLabel creditsLine1 = text("Ali Soltanian Fard Jahromi:\n\n" +
                "Lead Game and Level Designer\n" +
                "Game art and animations, including Sprites and UI elements\n" +
                "Player setup, including player controls, systems and gameplay design\n" +
                "Firing systems for both the enemies and players, including bosses\n" +
                "All background elements including scrolling system\n" +
                "HUD system design and programming\n" +
                "Explosions system for enemies and bosses\n" +
                "Pausing system and pause menu design\n" +
                "Created and added all sound effects and music\n" +
                "World handling for out of screen actors and boundary system for player\n" +
                "Enemy type and class system design and programming\n" +
                "\nJoshua Pearson:\n\n" +
                "Producer and Project Lead\n" +
                "VCS, organisation and production processes\n" +
                "Enemy Spawning system\n" +
                "Enemy Wave design and functionality including boss transitions\n" +
                "Class refactoring and consistency\n" +
                "Collision management and optimisation (game level)\n" +
                "Pickup system, shield system and functionality\n" +
                "Simple boss AI system\n" +
                "Enemy type and class system programming\n" +
                "Multiplayer tidy up and implementation\n" +
                "\nTessa Power:\n\n" +
                "Engine and Tools Lead Developer\n" +
                "Completely designed ECS Game Engine from the ground up including elements such as:\n" +
                " Physics engine with momentum and collisions system\n" +
                " Actor system for game world objects requiring components and control\n" +
                " Abstracted Graphical system used for visual elements, animations and Actors.\n" +
                " Flyweight design for asset optimisation\n" +
                " Mediator pattern for screen systems and data handling\n" +
                "Global system code control plus all quality checks and fixes\n" +
                "Complete game backend optimisation\n" +
                "Game foundation and system set up");
        creditsLine1.setOrigin(new TPoint(105, 200));

        TLabel fontAttr = bodyText("Revamped Font by Chequered Ink");
        fontAttr.setOrigin(new TPoint(105, 730));

        addAll(fontAttr, creditsLine1);
    }

    private TLabel text(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().defaultFont());

        return line;
    }
    private TLabel bodyText(String str) {
        TLabel line = new TLabel(str);
        line.setColor(Colors.Text.PRIMARY);
        line.setFont(FontBook.shared().bodyFont());

        return line;
    }
    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            SoundEffects.shared().menuSelect().play();
            submenuSelectionNotifier.accept(SubmenuOption.CLOSE);
        }
    }
}
