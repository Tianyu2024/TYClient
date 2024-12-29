package cn.tianyu.client.command.commands;

import cn.tianyu.client.command.Command;

import java.util.Arrays;

/**
 * @author CN_tianyu
 */

public class HelpCommand extends Command {
    public HelpCommand() {
        super(new String[]{"help"});
    }

    @Override
    public void run(String[] args) {
        System.out.println(Arrays.toString(args));
    }
}
