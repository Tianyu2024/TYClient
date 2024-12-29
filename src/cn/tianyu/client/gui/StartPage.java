package cn.tianyu.client.gui;

import cn.tianyu.client.TYClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class StartPage extends GuiScreen {

    private long startTime;
    private static final String BACKGROUND_TEXTURE = "font/font/CN_tianyu.png";
    private static final int FONT_SIZE = 67;

    @Override
    public void initGui() {
        super.initGui();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackgroundImage();

        drawLoadingAnimation(this.width / 2, this.height / 2 + 50, partialTicks);

        drawCustomFontString(TYClient.NAME, this.width / 2, this.height / 4 - 50, 0xFFFFFF, FONT_SIZE);

        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime >= 3000) {
            this.mc.displayGuiScreen(new CustomLoginScreen());  // 跳转到新的界面
        }

        super.drawScreen1(mouseX, mouseY, partialTicks);
    }

    private void drawBackgroundImage() {
        // 绘制背景图片
        Minecraft.getMinecraft().getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation(BACKGROUND_TEXTURE));
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(width, 0);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glEnd();
    }

    private void drawLoadingAnimation(int x, int y, float partialTicks) {
        // 旋转动画效果
        long elapsedTime = System.currentTimeMillis() - startTime;
        float rotationAngle = (elapsedTime / 1000f) * 360f;

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotationAngle, 0, 0, 1);  // 旋转

        GL11.glLineWidth(8.0f);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);  // 白色圆圈
        for (int i = 0; i < 360; i++) {
            double angle = Math.toRadians(i);
            double x1 = Math.cos(angle) * 20;
            double y1 = Math.sin(angle) * 20;
            GL11.glVertex2d(x1, y1);
        }
        GL11.glEnd();

        GL11.glPopMatrix();
    }

    private void drawCustomFontString(String text, int x, int y, int color, int fontSize) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(fontSize / 10f, fontSize / 10f, 1.0f);

        int textWidth = mc.fontRendererObj.getStringWidth(text);
        mc.fontRendererObj.drawString(text, -textWidth / 2, 0, color);

        GL11.glPopMatrix();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        //
    }

    public static void init() {
        StartPage startPage = new StartPage();
        Minecraft.getMinecraft().displayGuiScreen(startPage);
    }
}
