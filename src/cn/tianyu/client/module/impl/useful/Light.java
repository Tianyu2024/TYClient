package cn.tianyu.client.module.impl.useful;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;

/**
 * @author CN_tianyu
 */

public class Light extends Mod {
    private static final float NIGHT_VISION_GAMMA = 100.0F;
    private static final float DEFAULT_GAMMA = 1.0F;

    public Light() {
        super("Light", "永久夜视", Category.useful);
    }

    public void enable() {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.gameSettings.gammaSetting = NIGHT_VISION_GAMMA;
    }

    public void disable() {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.gameSettings.gammaSetting = DEFAULT_GAMMA;
    }
}