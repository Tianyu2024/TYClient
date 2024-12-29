package cn.tianyu.client.module.impl.movement;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.setting.EnableSetting;
import net.minecraft.client.Minecraft;


/**
 * @author CN_tianyu
 */

public class SprintMod extends Mod {

    private final EnableSetting setting = new EnableSetting("setting1", true);

    public SprintMod() {
        super("Sprint", "自动疾跑", Category.movement);
        getSetting().add(setting);
    }


    @Override
    public void enable() {
        System.out.println(getSettingByName("setting1").getValue());
    }

    @Override
    public void update() {
        if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
                || Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown()
                || Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown()) {
            if (!Minecraft.getMinecraft().thePlayer.isInWater())
                Minecraft.getMinecraft().thePlayer.setSprinting(true);
        }
    }
}
