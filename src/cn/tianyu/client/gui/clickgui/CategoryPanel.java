package cn.tianyu.client.gui.clickgui;

import cn.tianyu.client.TYClient;
import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CN_tianyu
 */

public class CategoryPanel {
    private int x;
    private int y;
    private Category category;

    private final int width = 80;
    private final int height = 20;

    private int prevX;
    private int prevY;

    private boolean press;
    private boolean hovered;
    private boolean displayMod = false;

    private final List<cn.tianyu.client.gui.clickgui.ModPanel> modPanels = new ArrayList<>();
    private boolean displayVersionInfo = false; // 新增字段，跟踪是否显示版本信息

    public CategoryPanel(int x, int y, Category category) {
        this.x = x;
        this.y = y;
        this.category = category;

        for (Mod mod : TYClient.modManager.getByCategory(category)) {
            modPanels.add(new cn.tianyu.client.gui.clickgui.ModPanel(mod));
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        hovered = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;

        // 拖动功能
        if (hovered && press) {
            x = mouseX + prevX;
            y = mouseY + prevY;
        }

        // 绘制背景
        int background = new Color(0, 0, 0, 190).getRGB();
        int select = new Color(166, 166, 166, 128).getRGB();
        DrawUtil.drawRect(x, y, width, height, hovered ? select : background);

        // 绘制分类名称
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        fontRendererObj.drawString(category.name(), x + (width / 2 - fontRendererObj.getStringWidth(category.name()) / 2), y + (height / 2 - fontRendererObj.FONT_HEIGHT / 2), 0xFFFFFFFF);

        // 绘制模块面板
        int index = 0;
        if (displayMod) {
            for (cn.tianyu.client.gui.clickgui.ModPanel it : modPanels) {
                it.x = x; // 确保模块面板的x坐标
                it.y = y + height + index; // 模块面板的y坐标
                it.drawScreen(mouseX, mouseY, partialTicks);
                index += 20; // 每个模块面板向下移动20个像素
            }
        }

        // 绘制版本信息
        if (displayVersionInfo) {
            index = 0; // 重置索引用于版本信息绘制
            for (Mod mod : TYClient.modManager.getByCategory(category)) {
                String versionText = "描述:" + mod.getVersion();
                fontRendererObj.drawString(versionText, x, y + height + index + 20 * modPanels.size(), Color.BLUE.getRGB());
                index += 20; // 每行向下移动20个像素
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            press = true;
            prevX = x - mouseX;
            prevY = y - mouseY;
        } else if (mouseButton == 1 && hovered) {
            displayMod = !displayMod; // 切换显示模块
            displayVersionInfo = !displayVersionInfo; // 切换显示版本信息
        }

        if (displayMod) {
            modPanels.forEach(it -> it.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        press = false;

        if (displayMod) {
            modPanels.forEach(it -> it.mouseReleased(mouseX, mouseY, state));
        }
    }
}
