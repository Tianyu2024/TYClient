package cn.tianyu.client.gui;

import cn.tianyu.client.TYClient;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class MainMenuGui extends GuiScreen {
    private static final int LARGE_FONT_SIZE = 72;  // 标题字体大小
    private static final int BUTTON_WIDTH = 120;   // 按钮宽度
    private static final int BUTTON_HEIGHT = 21;   // 按钮高度
    private static final int BUTTON_SPACING = 10;  // 按钮间距
    private static final ResourceLocation BACKGROUND = new ResourceLocation("client/bg/background.png");

    private float alpha = 0f;
    private long startTime;

    @Override
    public void initGui() {
        int startY = this.height / 4 - 30;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - BUTTON_WIDTH / 2, startY, BUTTON_WIDTH, BUTTON_HEIGHT, "Singleplayer"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - BUTTON_WIDTH / 2, startY + BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT, "Multiplayer"));
        this.buttonList.add(new GuiButton(0, this.width / 2 - BUTTON_WIDTH / 2, startY + 2 * (BUTTON_HEIGHT + BUTTON_SPACING), BUTTON_WIDTH, BUTTON_HEIGHT, "Settings"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - BUTTON_WIDTH / 2, startY + 3 * (BUTTON_HEIGHT + BUTTON_SPACING), BUTTON_WIDTH, BUTTON_HEIGHT, "Exit"));
        // 记录当前时间，用于透明度过渡
        startTime = System.currentTimeMillis();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // 计算当前透明度，根据时间变化
        long elapsed = System.currentTimeMillis() - startTime;
        float transitionDuration = 2000f; // 2秒过渡
        alpha = Math.min(1.0f, elapsed / transitionDuration);

        // 设置透明度
        GL11.glPushMatrix();
        GL11.glColor4f(1f, 1f, 1f, alpha);

        mc.getTextureManager().bindTexture(BACKGROUND);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);

        int fontSize = LARGE_FONT_SIZE;
        int titleY = 30;  // 调整标题位置，确保它不超出屏幕
        drawCustomFontString(TYClient.NAME, width / 2, titleY + 41, new Color(0, 0, 0, 255).getRGB(), fontSize);
        drawCustomFontString("赞助我+QQ:3808257263", width / 2, titleY + 250, new Color(0, 0, 0).getRGB(), 10);

        for (int i = 0; i < buttonList.size(); i++) {
            GuiButton button = buttonList.get(i);
            button.yPosition = titleY + fontSize + BUTTON_SPACING + i * (BUTTON_HEIGHT + BUTTON_SPACING) + 50;
            button.drawButton1(mc, mouseX, mouseY);
        }

        GL11.glPopMatrix();

        super.drawScreen1(mouseX, mouseY, partialTicks);
    }

    // 自定义绘制字体的方法
    private void drawCustomFontString(String text, int x, int y, int color, int fontSize) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(fontSize / 10f, fontSize / 10f, 1.0f);

        int textWidth = mc.fontRendererObj.getStringWidth(text);
        mc.fontRendererObj.drawString(text, -textWidth / 2, 0, color);

        GL11.glPopMatrix();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }
    }
}
