package cn.tianyu.client.module.impl.client;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Notifications extends Mod {
    private static final List<Notification> notifications = new ArrayList<>();

    public Notifications() {
        super("Notifications", "通知功能", Category.client);
    }

    public static void addNotification(String message, boolean isEnabled) {
        Notification notification = new Notification(message, isEnabled);
        notifications.add(notification);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notifications.remove(notification);
            }
        }, 5000);
    }

    @Override
    public void draw() {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        int screenHeight = scaledResolution.getScaledHeight();
        int screenWidth = scaledResolution.getScaledWidth();

        int yOffset = screenHeight - 20;
        for (Notification notification : notifications) {
            int textWidth = fontRenderer.getStringWidth(notification.message);
            int boxWidth = textWidth + 10;
            int boxHeight = 20;

            if (!notification.isDisplayed) {
                notification.startX = screenWidth + boxWidth;
                notification.isDisplayed = true;
            }

            int x = notification.startX - (int) ((System.currentTimeMillis() - notification.startTime) / 10.0);
            if (x < screenWidth - boxWidth - 10) {
                x = screenWidth - boxWidth - 10;
            }

            // 保存OpenGL状态
            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

            // 绘制黑色背景框
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.5f); // 黑色半透明背景
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(x, yOffset - boxHeight);
            GL11.glVertex2f(x + boxWidth, yOffset - boxHeight);
            GL11.glVertex2f(x + boxWidth, yOffset);
            GL11.glVertex2f(x, yOffset);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            // 绘制通知框
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            if (notification.isEnabled) {
                GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.5f); // 绿色
            } else {
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f); // 红色
            }
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(x, yOffset - boxHeight);
            GL11.glVertex2f(x + boxWidth, yOffset - boxHeight);
            GL11.glVertex2f(x + boxWidth, yOffset);
            GL11.glVertex2f(x, yOffset);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            // 绘制文本
            fontRenderer.drawString(notification.message, x + 5, yOffset - 15, 0xFFFFFF);

            // 恢复OpenGL状态
            GL11.glPopAttrib();
            GL11.glPopMatrix();

            yOffset -= 30;
        }
    }

    private static class Notification {
        private final String message;
        private final boolean isEnabled;
        private final long startTime;
        private boolean isDisplayed;
        private int startX;

        public Notification(String message, boolean isEnabled) {
            this.message = message;
            this.isEnabled = isEnabled;
            this.startTime = System.currentTimeMillis();
            this.isDisplayed = false;
        }
    }
}
