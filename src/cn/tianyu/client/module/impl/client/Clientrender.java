package cn.tianyu.client.module.impl.client;

import cn.tianyu.client.TYClient;
import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * 作者：CN_tianyu
 */

public class Clientrender extends Mod {
    public Clientrender() {
        super("Clientrender", "显示客户端名称", Category.client);
    }

    @Override
    public void draw() {
        Minecraft minecraft = Minecraft.getMinecraft();
        FontRenderer fontRenderer = minecraft.fontRendererObj;
        EntityPlayer player = minecraft.thePlayer;

        int xPosition = 10;
        int yPosition = 10;

        // 绘制客户端名称
        String clientName = TYClient.NAME;
        fontRenderer.drawString(clientName, xPosition, yPosition, new Color(255, 255, 255).getRGB());

        // 绘制长方形方框
        int boxWidth = 150;
        int boxHeight = 40;
        yPosition += 20; // 调整y位置到客户端名称下方
        Gui.drawRect(xPosition, yPosition, xPosition + boxWidth, yPosition + boxHeight, new Color(0, 0, 0, 150).getRGB());

        // 绘制玩家皮肤头像
        ResourceLocation skin = getPlayerSkin(player);
        minecraft.getTextureManager().bindTexture(skin);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_BLEND);
        drawModalRectWithCustomSizedTexture(xPosition + 5, yPosition + 5, 8, 8, 8, 8, 64, 64);

        // 绘制玩家名字
        String playerName = player.getName();
        fontRenderer.drawString(playerName, xPosition + 50, yPosition + 5, new Color(255, 255, 255).getRGB());

        // 绘制玩家血量
        String health = "Health: " + (int) player.getHealth();
        fontRenderer.drawString(health, xPosition + 50, yPosition + 20, new Color(255, 255, 255).getRGB());
    }

    private ResourceLocation getPlayerSkin(EntityPlayer player) {
        NetworkPlayerInfo info = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(player.getUniqueID());
        return info != null ? info.getLocationSkin() : new ResourceLocation("textures/entity/steve.png");
    }

    @Override
    public void enable() {
        super.enable();
        Notifications.addNotification("开启render", true);
    }

    @Override
    public void disable() {
        super.disable();
        Notifications.addNotification("关闭render", false);
    }

    private void drawModalRectWithCustomSizedTexture(int x, int y, int u, int v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f((float)(u) * f, (float)(v + height) * f1);
        GL11.glVertex3f((float)(x), (float)(y + height), 0.0F);
        GL11.glTexCoord2f((float)(u + width) * f, (float)(v + height) * f1);
        GL11.glVertex3f((float)(x + width), (float)(y + height), 0.0F);
        GL11.glTexCoord2f((float)(u + width) * f, (float)(v) * f1);
        GL11.glVertex3f((float)(x + width), (float)(y), 0.0F);
        GL11.glTexCoord2f((float)(u) * f, (float)(v) * f1);
        GL11.glVertex3f((float)(x), (float)(y), 0.0F);
        GL11.glEnd();
    }
}
