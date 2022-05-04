package xenocraft.magicparkour;

public class Msg {

    private static Main main;

    private Msg() {}

    public static void init(Main main) {
        Msg.main = main;
    }

    public static String get(String key) {
        return main.getMessagesConfig().getString(key);
    }

}
