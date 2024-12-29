package cn.tianyu.client;

import cn.tianyu.client.command.CommandManager;
import cn.tianyu.client.config.ConfigManager;
import cn.tianyu.client.gui.clickgui.ClickGui;
import cn.tianyu.client.module.ModManager;
import org.lwjgl.opengl.Display;

/**
 * @author CN_tianyu
 */

public class TYClient {
    public static final String NAME = "TYClient";
    public static final String VERSION = "1.3";
    public static ModManager modManager;
    public static ConfigManager configManager;
    public static CommandManager commandManager;
    public static TYClient instance;

    public static ClickGui clickGui;

    public static void start() {
        modManager = new ModManager();
        configManager = new ConfigManager();
        commandManager = new CommandManager();
        modManager.load();
        configManager.load();
        commandManager.load();
        clickGui = new ClickGui();
        Display.setTitle(NAME + " | " + VERSION + "  by CN_tianyu");
    }

    public static void stop() {
        configManager.save();
    }
}
