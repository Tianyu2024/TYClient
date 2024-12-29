package cn.tianyu.client.module.impl.render;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.module.impl.client.Notifications;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

import java.awt.Color;

/**
 * @author CN_tianyu
 */

public class TargetInfoMod extends Mod {

    private int posX = 110;
    private int posY = 100;
    private int width = 100; // 宽度足够显示信息
    private int height = 400; // 高度足够显示信息
    private boolean isDragging = false;
    private int dragOffsetX, dragOffsetY;

    public TargetInfoMod() {
        super("TargetInfo", "攻击目标信息显示", Category.render);
        Notifications.addNotification("开启", true);
    }

    @Override
    public void draw() {
        Minecraft mc = Minecraft.getMinecraft();
        Entity entityHit = mc.objectMouseOver.entityHit;
        EntityPlayer player = mc.thePlayer;

        // 检查聊天框是否打开
        if (mc.currentScreen instanceof GuiChat) {
            if (Mouse.isButtonDown(0)) {
                if (isDragging) {
                    posX = Mouse.getX() * mc.displayWidth / mc.displayWidth - dragOffsetX;
                    posY = mc.displayHeight - Mouse.getY() * mc.displayHeight / mc.displayHeight - dragOffsetY;
                    posX = Math.max(0, Math.min(posX, mc.displayWidth - width));
                    posY = Math.max(0, Math.min(posY, mc.displayHeight - height));
                } else {
                    if (isMouseOver()) {
                        isDragging = true;
                        dragOffsetX = Mouse.getX() * mc.displayWidth / mc.displayWidth - posX;
                        dragOffsetY = mc.displayHeight - Mouse.getY() * mc.displayHeight / mc.displayHeight - posY;
                    }
                }
            } else {
                isDragging = false;
            }

            // Draw background rectangle (long rectangle)
            drawRect(posX, posY, posX + width, posY + height, new Color(0, 0, 0, 150).getRGB());

            // Draw player's model and info
            GuiInventory.drawEntityOnScreen(
                    posX + 20, posY + height / 4, 30, 0, 0, player
            );

            mc.fontRendererObj.drawString(
                    player.getName(), posX + 70, posY + 10, 0xFFFFFFFF
            );

            mc.fontRendererObj.drawString(
                    String.format("HP: %s/%s", (int) player.getHealth(), (int) player.getMaxHealth()),
                    posX + 70, posY + 30, 0xFFFFFFFF
            );

            // If there is an entity being attacked, display its info
            if (entityHit instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) entityHit;

                GuiInventory.drawEntityOnScreen(
                        posX + 20, posY + height / 2 + 20, 30, 0, 0, entity
                );

                mc.fontRendererObj.drawString(
                        entity.getName(), posX + 70, posY + height / 2 + 30, 0xFFFFFFFF
                );

                mc.fontRendererObj.drawString(
                        String.format("HP: %s/%s", (int) entity.getHealth(), (int) entity.getMaxHealth()),
                        posX + 70, posY + height / 2 + 50, 0xFFFFFFFF
                );
            }
        } else if (entityHit instanceof EntityLivingBase) {
            // 只在攻击目标时显示框
            EntityLivingBase entity = (EntityLivingBase) entityHit;

            // Draw background rectangle (long rectangle)
            drawRect(posX, posY, posX + width, posY + height, new Color(0, 0, 0, 150).getRGB());

            // Draw target's model and info
            GuiInventory.drawEntityOnScreen(
                    posX + 20, posY + height / 2 + 20, 30, 0, 0, entity
            );

            mc.fontRendererObj.drawString(
                    entity.getName(), posX + 70, posY + height / 2 + 30, 0xFFFFFFFF
            );

            mc.fontRendererObj.drawString(
                    String.format("HP: %s/%s", (int) entity.getHealth(), (int) entity.getMaxHealth()),
                    posX + 70, posY + height / 2 + 50, 0xFFFFFFFF
            );
        }
    }

    private boolean isMouseOver() {
        int mouseX = Mouse.getX() * Minecraft.getMinecraft().displayWidth / Minecraft.getMinecraft().displayWidth;
        int mouseY = Minecraft.getMinecraft().displayHeight - Mouse.getY() * Minecraft.getMinecraft().displayHeight / Minecraft.getMinecraft().displayHeight;
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        // Draw rectangle method (long rectangle) using Minecraft's method or custom drawing code
        Minecraft.getMinecraft().ingameGUI.drawRect(left, top, right, bottom, color);
    }
}
