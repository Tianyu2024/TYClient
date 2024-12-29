package cn.tianyu.client.config;

import cn.tianyu.client.TYClient;
import net.minecraft.client.Minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author CN_tianyu
 */

public class Config {
    private final String name;

    public Config(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCCC(){
        return name;
    }

    public Path getPath() {
        return Paths.get(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), TYClient.NAME, "config", name + ".json");
    }

    public void load() {

    }

    public void save() {

    }
}
