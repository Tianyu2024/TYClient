package cn.tianyu.client.module.impl.render;

import cn.tianyu.client.TYClient;
import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * @author CN_tianyu
 */

public class ClickGUI extends Mod {
    public ClickGUI() {
        super("ClickGUI", "就是你现在看到的界面", Category.render);
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void enable() {
        Minecraft.getMinecraft().displayGuiScreen(TYClient.clickGui);
        setEnable(false);
    }
}
