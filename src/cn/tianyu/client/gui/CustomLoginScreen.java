package cn.tianyu.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CustomLoginScreen extends GuiScreen {
    private GuiTextField usernameField;
    private GuiTextField passwordField;
    private String statusMessage;
    private ResourceLocation backgroundTexture;
    private static final int LARGE_FONT_SIZE = 45;
    int fontSize = LARGE_FONT_SIZE;

    @Override
    public void initGui() {
        buttonList.clear();

        // 初始化文本框
        usernameField = new GuiTextField(0, fontRendererObj, width / 2 - 100, height / 4 - 20, 200, 20);
        usernameField.setMaxStringLength(30);
        usernameField.setFocused(true);

        passwordField = new GuiTextField(1, fontRendererObj, width / 2 - 100, height / 4 + 20, 200, 20);
        passwordField.setMaxStringLength(30);
        passwordField.setEnableBackgroundDrawing(true);

        // 添加按钮
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 60, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 100, "Exit"));
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 140, "Reg"));
        buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 180, "Cancel Login"));  // 新增按钮

        // 加载背景图片
        backgroundTexture = new ResourceLocation("client/Q.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundTexture);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0) {  // Login按钮
            String username = usernameField.getText();
            String password = passwordField.getText();
            new Thread(() -> {
                String response = checkAccountOnServer(username, password);
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    if ("Login successful".equals(response)) {
                        Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
                    } else {
                        statusMessage = "登录失败：账户不存在或密码错误。";
                    }
                });
            }).start();
        } else if (button.id == 1) {  // Exit按钮
            Minecraft.getMinecraft().shutdown();
        } else if (button.id == 2) {  // Reg按钮
            statusMessage = "注册功能尚未实现。";
        } else if (button.id == 3) {  // 新增的“取消登录”按钮
            Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());  // 跳转到主菜单
        }
    }

    private String checkAccountOnServer(String username, String password) {
        try (Socket socket = new Socket("127.0.0.1", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String credentials = username + " " + password;
            out.println(credentials);
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "连接失败";
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground();
        drawDynamicBackground(partialTicks);

        drawCustomFontString("Welcome", width / 2 ,height / 4 - 60, Color.WHITE.getRGB(), fontSize);
        usernameField.drawTextBox();
        passwordField.drawTextBox();
        drawString(fontRendererObj, "用户名:", width / 2 - 100, height / 4 - 40, Color.WHITE.getRGB());
        drawString(fontRendererObj, "密码:", width / 2 - 100, height / 4 + 0, Color.WHITE.getRGB());

        if (statusMessage != null) {
            drawStringWithGL(statusMessage, width / 2 - 100, height / 4 + 120);
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.drawScreen1(mouseX, mouseY, partialTicks);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawBackground() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundTexture);
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

    private void drawDynamicBackground(float partialTicks) {
        int width = this.width;
        int height = this.height;

        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_QUADS);

        // 动态颜色计算
        float time = (Minecraft.getSystemTime() % 10000L) / 10000.0F;
        float red = 0.5f + 0.5f * (float) Math.sin(time * 2 * Math.PI);
        float green = 0.5f + 0.5f * (float) Math.sin(time * 2 * Math.PI + Math.PI / 3);
        float blue = 0.5f + 0.5f * (float) Math.sin(time * 2 * Math.PI + 2 * Math.PI / 3);

        // 左上角
        GL11.glColor3f(red, green, blue);
        GL11.glVertex2f(0, 0);

        // 右上角
        GL11.glColor3f(green, blue, red);
        GL11.glVertex2f(width, 0);

        // 右下角
        GL11.glColor3f(blue, red, green);
        GL11.glVertex2f(width, height);

        // 左下角
        GL11.glColor3f(red, green, blue);
        GL11.glVertex2f(0, height);

        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void drawStringWithGL(String text, int x, int y) {
        fontRendererObj.drawString(text, x, y, Color.WHITE.getRGB());
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
    public void updateScreen() {
        usernameField.updateCursorCounter();
        passwordField.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        usernameField.mouseClicked(mouseX, mouseY, button);
        passwordField.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        usernameField.textboxKeyTyped(typedChar, keyCode);
        passwordField.textboxKeyTyped(typedChar, keyCode);
    }
}
