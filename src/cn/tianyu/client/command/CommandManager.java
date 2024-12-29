package cn.tianyu.client.command;

import cn.tianyu.client.command.commands.HelpCommand;
import cn.tianyu.client.command.commands.OpenCommand;
import cn.tianyu.client.command.commands.bindCommand;

import java.util.*;

/**
 * @author CN_tianyu
 */

public class CommandManager {
    private final Map<String[], Command> commandMap = new HashMap<>();

    public void load() {
        HelpCommand helpCommand = new HelpCommand();
        commandMap.put(helpCommand.getKey(), helpCommand);

        OpenCommand OpenCommand = new OpenCommand();
        commandMap.put(OpenCommand.getKey(), OpenCommand);

        bindCommand bindCommand = new bindCommand();
        commandMap.put(bindCommand.getKey(), bindCommand);
    }

    public boolean run(String message) {
        if ('.' == message.charAt(0)) {

            String substring = message.substring(1);

            String[] s = substring.split(" ");

            String key = s[0];
            Command command = getCommand(key);

            if (command != null) {
                List<String> args = new ArrayList<>();
                Collections.addAll(args, s);
                args.remove(0);
                command.run(args.toArray(new String[0]));
            }
            return true;
        }
        return false;
    }

    public Command getCommand(String key) {
        for (Map.Entry<String[], Command> commandEntry : commandMap.entrySet()) {
            for (String k : commandEntry.getKey()) {
                if (k.equals(key)) {
                    return commandEntry.getValue();
                }
            }
        }
        return null;
    }
}
