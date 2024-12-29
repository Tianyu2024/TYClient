package cn.tianyu.client.gui.clickgui;

import cn.tianyu.client.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CN_tianyu
 */

public class ClickGui extends GuiScreen {

    private final List<CategoryPanel> categoryPanels = new ArrayList<>();

    public ClickGui() {
        int x = 20;
        for (Category value : Category.values()) {
            categoryPanels.add(new CategoryPanel(x, 0, value));
            x += 100;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        categoryPanels.forEach(it -> it.drawScreen(mouseX, mouseY, partialTicks));
        super.drawScreen1(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        categoryPanels.forEach(it -> it.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        categoryPanels.forEach(it -> it.mouseReleased(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
