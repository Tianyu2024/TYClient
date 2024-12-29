package cn.tianyu.client.module;

import cn.tianyu.client.setting.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CN_tianyu
 */

public class Mod {
    private final String name;
    private final Category category;
    public boolean state = false;
    public final String version;
    private boolean enable;
    private boolean disable;
    private int key;

    private final List<Setting<?>> setting = new ArrayList<>();

    public Mod(String name, String version, Category category) {
        this.name = name;
        this.version = version;
        this.category = category;
    }

    public boolean getState() {
        return this.state;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public boolean isEnable() {
        return enable;
    }
    public boolean isDisable() {
        return disable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;

        if (enable) {
            enable();
        } else {
            disable();
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public List<Setting<?>> getSetting() {
        return setting;
    }

    public Setting<?> getSettingByName(String name) {
        for (Setting<?> setting : setting) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public void enable() {

    }

    public void disable() {

    }

    public void draw() {

    }

    public void render(float partialTicks) {

    }

    public void ontick() {

    }

    public void update() {

    }

    public void key(int key) {

    }
}
