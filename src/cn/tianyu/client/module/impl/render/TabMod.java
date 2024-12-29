package cn.tianyu.client.module.impl.render;

import cn.tianyu.client.TYClient;
import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;

/**
 * @author CN_tianyu
 */

public class TabMod extends Mod {

    private int currentCategory = 0;
    private int currentMod;
    private boolean mod = false;

    public TabMod() {
        super("TabMod", "快捷开关", Category.render);
    }

    @Override
    public void draw() {
        drawTab(0, 10);
    }

    @Override
    public void key(int key) {
        // 获取 Minecraft 实例
        Minecraft mc = Minecraft.getMinecraft();

        // 判断是否打开物品栏
        boolean isInventoryOpen = mc.thePlayer != null && mc.thePlayer.inventoryContainer != null;

        // 只有在物品栏打开时才允许移动
        if (isInventoryOpen) {
            if (!mod) {
                if (key == Keyboard.KEY_UP) {
                    if (currentCategory > 0) {
                        currentCategory--;
                    } else {
                        currentCategory = Category.values().length - 1;
                    }
                } else if (key == Keyboard.KEY_DOWN) {
                    if (currentCategory < Category.values().length - 1) {
                        currentCategory++;
                    } else {
                        currentCategory = 0;
                    }
                } else if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_RIGHT) {
                    mod = true;
                }
            } else {
                if (key == Keyboard.KEY_UP) {
                    if (currentMod > 0) {
                        currentMod--;
                    } else {
                        currentMod = TYClient.modManager.getByCategory(Category.values()[currentCategory]).size() - 1;
                    }
                } else if (key == Keyboard.KEY_DOWN) {
                    if (currentMod < TYClient.modManager.getByCategory(Category.values()[currentCategory]).size() - 1) {
                        currentMod++;
                    } else {
                        currentMod = 0;
                    }
                } else if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_RIGHT) {
                    Mod m = TYClient.modManager.getByCategory(Category.values()[currentCategory]).get(currentMod);
                    m.setEnable(!m.isEnable());
                } else if (key == Keyboard.KEY_LEFT) {
                    mod = false;
                    currentMod = 0;
                }
            }
        }
    }

    private void drawTab(int x, int y) {
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        int index = y;
        Category[] values = Category.values();
        for (int i = 0; i < values.length; i++) {
            Category value = values[i];
            Gui.drawRect(x, index,
                    x + fontRendererObj.getStringWidth(Category.useful.name()), index + fontRendererObj.FONT_HEIGHT,
                    i == currentCategory ? new Color(255, 255, 255, 123).getRGB() : new Color(4, 4, 4, 190).getRGB());
            if (i == currentCategory) {
                if (mod) {
                    int modX = x + fontRendererObj.getStringWidth(Category.client.name());
                    List<Mod> byCategory = TYClient.modManager.getByCategory(Category.values()[currentCategory]);
                    int modY = index;
                    for (int j = 0; j < byCategory.size(); j++) {
                        Mod m = byCategory.get(j);

                        Gui.drawRect(modX, modY, modX + getModWidth(), modY + fontRendererObj.FONT_HEIGHT,
                                j == currentMod ? new Color(255, 255, 255, 118).getRGB() : new Color(0, 0, 0, 153).getRGB());

                        fontRendererObj.drawString(m.getName(), modX, modY, m.isEnable() ? 0x22FF00 : 0xFF9900);
                        modY += fontRendererObj.FONT_HEIGHT;
                    }
                }
            }
            fontRendererObj.drawString(value.name(), x, index, 0xFFFFFFFF);
            index += fontRendererObj.FONT_HEIGHT;
        }
    }

    private int getModWidth() {
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        List<Mod> byCategory = TYClient.modManager.getByCategory(Category.values()[currentCategory]);
        byCategory.sort((o1, o2) -> fontRendererObj.getStringWidth(o2.getName()) - fontRendererObj.getStringWidth(o1.getName()));
        return fontRendererObj.getStringWidth(byCategory.get(0).getName());
    }
}
