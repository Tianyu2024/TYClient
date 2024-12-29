package cn.tianyu.client.module.impl.render;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;

import java.awt.*;

/**
 * @author CN_tianyu
 */
public class KeyBoard extends Mod {

    public KeyBoard() {
        super("KeyBoard", "按键显示", Category.render);
    }

    @Override
    public void draw() {
        drawKey(0, 100);
    }

    public void drawKey(int x, int y) {
        int backgroundColor = new Color(0, 0, 0, 100).getRGB();
        int pressedColor = new Color(255, 255, 255, 190).getRGB();

        Minecraft minecraft = Minecraft.getMinecraft();
        GameSettings gameSettings = minecraft.gameSettings;
        FontRenderer fontRenderer = minecraft.fontRendererObj;

        // 绘制 W 键
        drawKeyButton(x + 25, y + 1, gameSettings.keyBindForward.isKeyDown(), "W", fontRenderer, pressedColor, backgroundColor);

        // 绘制 S 键
        drawKeyButton(x + 25, y + 26, gameSettings.keyBindBack.isKeyDown(), "S", fontRenderer, pressedColor, backgroundColor);

        // 绘制 A 键
        drawKeyButton(x, y + 26, gameSettings.keyBindLeft.isKeyDown(), "A", fontRenderer, pressedColor, backgroundColor);

        // 绘制 D 键
        drawKeyButton(x + 50, y + 26, gameSettings.keyBindRight.isKeyDown(), "D", fontRenderer, pressedColor, backgroundColor);

        // 绘制 SPACE 键
        DrawUtil.drawRect(x, y + 51, 75, 20, gameSettings.keyBindJump.isKeyDown() ? pressedColor : backgroundColor);
        fontRenderer.drawString("SPACE", x + 26, y + 56, 0xFFFFFFFF);
    }

    private void drawKeyButton(int x, int y, boolean isPressed, String label, FontRenderer fontRenderer, int pressedColor, int backgroundColor) {
        DrawUtil.drawRect(x, y, 25, 25, isPressed ? pressedColor : backgroundColor);
        fontRenderer.drawString(label, x + 10, y + 10, 0xFFFFFFFF);
    }
}
