package cn.tianyu.client.command.commands;

import cn.tianyu.client.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class bindCommand extends Command {
    public bindCommand() {
        super(new String[]{"bind"});
    }

    @Override
    public void run(String[] args) {
        if (args.length == 2) {
            // 用户输入了 modName 和 key，但我们暂未实现功能
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("功能暂未完成"));
        } else {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("用法: .bind <modName> <key>"));
        }
    }
}
