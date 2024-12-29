package cn.tianyu.client.module.impl.client;

import cn.tianyu.client.TYClient;
import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author CN_tianyu
 */

public class HUD extends Mod {
    private float hue = 0.005f;
    private static final int FONT_SCALE = 2;

    public HUD() {
        super("HUD", "仿外挂界面", Category.client);
        setKey(Keyboard.KEY_O);
    }

    private float I = 0.005f;

    @Override
    public void draw() {
        Minecraft minecraft = Minecraft.getMinecraft();
        FontRenderer fontRenderer = minecraft.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        int width = scaledResolution.getScaledWidth();

        hue += I;
        if (hue > 1.0f) {
            hue -= 1.0f;
        }

        int color = Color.getColorFromHue(hue);

        List<Mod> enabledMods = TYClient.modManager.getEnableMods();
        enabledMods.sort((o1, o2) -> fontRenderer.getStringWidth(o2.getName()) - fontRenderer.getStringWidth(o1.getName()));

        int yPosition = 0;

        GL11.glPushMatrix();
        GL11.glScalef(FONT_SCALE, FONT_SCALE, 1.0F);
        for (Mod mod : enabledMods) {
            String modName = mod.getName();
            int stringWidth = fontRenderer.getStringWidth(modName);
            fontRenderer.drawString(modName, (width - stringWidth * FONT_SCALE) / FONT_SCALE, yPosition / FONT_SCALE, color);
            yPosition += fontRenderer.FONT_HEIGHT * FONT_SCALE + 5;
        }
        GL11.glPopMatrix();
    }

    private static class Color {
        public static int getColorFromHue(float hue) {
            int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
            return (rgb & 0xFFFFFF) | 0xFF000000;
        }
    }
}
