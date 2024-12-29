package cn.tianyu.client.gui.clickgui;

import cn.tianyu.client.module.Mod;
import cn.tianyu.client.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

/**
 * @author CN_tianyu
 */

public class ModPanel {
    private final Mod mod;
    public int x;
    public int y;

    private final int width = 80;
    private final int height = 20;

    private boolean hovered;
    private boolean enabled;
    private boolean showText; // 新增变量

    private static final int GAP = 10; // 定义模块之间的间隔

    public ModPanel(Mod mod) {
        this.mod = mod;
        this.enabled = mod.isEnable();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        hovered = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
        int background = mod.isEnable() ? new Color(0, 174, 248, 87).getRGB() : new Color(0, 0, 0, 255).getRGB();
        DrawUtil.drawRect(x, y, this.width, this.height, background);
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        font.drawString(mod.getName(), x + (width / 2 - font.getStringWidth(mod.getName()) / 2), y + (height / 2 - font.FONT_HEIGHT / 2), 0xFFFFFFFF);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && hovered) {
            mod.setEnable(!mod.isEnable());
            this.enabled = !this.enabled;
        } else if (mouseButton == 1 && hovered) { // 处理右键点击
            showText = !showText; // 切换显示状态
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        // 可以根据需要添加逻辑
    }

    // 设置ModPanel的y坐标，增加间隔
    public void setYWithGap(int baseY) {
        this.y = baseY + GAP; // 根据基础y值设置y坐标，增加10像素间隔
    }
}
