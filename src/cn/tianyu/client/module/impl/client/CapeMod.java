package cn.tianyu.client.module.impl.client;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;

/**
 * @author CN_tianyu
 */

public class CapeMod extends Mod {
    public CapeMod() {
        super("Cape", "披风", Category.client);
    }
    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        enable();
    }
}