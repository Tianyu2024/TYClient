package cn.tianyu.client.module.impl.world;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import cn.tianyu.client.module.impl.client.Notifications;

/**
 * @author CN_tianyu
 */

public class XRay extends Mod {
    public XRay() {
        super("XRay", "透矿", Category.world);
        setKey(Keyboard.KEY_M);
    }

    @Override
    public void enable() {
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        Notifications.addNotification("开启Xray", true);
    }

    @Override
    public void disable() {
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        Notifications.addNotification("关闭Xray", false);
    }
}
