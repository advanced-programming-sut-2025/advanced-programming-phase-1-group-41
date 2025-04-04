package views;

public enum Menu {
    Authentication(new AuthenticationMenu()),
    Profile(new ProfileMenu()),
    Main(new MainMenu()),
    Exit(new ExitMenu()),
    Game(new GameMenu())
    ;


    public final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
}
