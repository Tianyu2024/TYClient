package cn.tianyu.client.module.impl.useful;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.module.impl.client.Notifications;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;

/**
 * @author CN_tianyu
 */

public class FastPlace extends Mod {
    public FastPlace() {
        super("FastPlace", "快速放置(仅建筑时使用)", Category.useful);
    }

    @Override
    public void update() {
        if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
            if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                Minecraft.getMinecraft().rightClickDelayTimer = 0;
            } else {
                Minecraft.getMinecraft().rightClickDelayTimer = 4;
            }
        }
    }

    public static final String NAME = "FastPlace";
    @Override
    public void enable() {
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        Notifications.addNotification("开启" + FastPlace.NAME, true);
    }

    @Override
    public void disable() {
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        Notifications.addNotification("关闭"+ FastPlace.NAME, false);
    }
}
