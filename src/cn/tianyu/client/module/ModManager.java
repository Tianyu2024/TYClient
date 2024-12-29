package cn.tianyu.client.module;

import cn.tianyu.client.module.impl.abab.BrightPlayers;
import cn.tianyu.client.module.impl.abab.acdsda;
import cn.tianyu.client.module.impl.client.*;
import cn.tianyu.client.module.impl.movement.SprintMod;
import cn.tianyu.client.module.impl.render.*;
import cn.tianyu.client.module.impl.useful.*;
import cn.tianyu.client.module.impl.world.XRay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author CN_tianyu
 */

public class ModManager {
    private final List<Mod> mods = new ArrayList<>();
    private final Map<String, Mod> moduleMap = new HashMap<>();

    public List<Mod> getMods() {
        return mods;
    }

    public List<Mod> getEnableMods() {
        return mods.stream().filter(Mod::isEnable).collect(Collectors.toList());
    }

    public void onKey(int key) {
        for (Mod enableMod : mods) {
            if (enableMod.getKey() == key) {
                enableMod.setEnable(!enableMod.isEnable());
            }
        }
    }

    public <T extends Mod> T getModule(Class<T> cls) {
        return cls.cast(moduleMap.get(cls.getSimpleName()));
    }

    public void load() {
        //abab
        mods.add(new acdsda());
        mods.add(new BrightPlayers());
        //movement
        mods.add(new SprintMod());
        //Render
        mods.add(new BetterTab());
        mods.add(new ClickGUI());
        mods.add(new HitBox());
        mods.add(new KeyBoard());
        mods.add(new NameLabel());
        mods.add(new NoHurtCamera());
        mods.add(new SelectBlock());
        mods.add(new TabMod());
        mods.add(new TargetInfoMod());
        //useful
        mods.add(new Light());
        mods.add(new fps());
        mods.add(new FastPlace());
        mods.add(new ItemPhysics());
        mods.add(new MotionBlur());
        mods.add(new fenliLizi());
        //world
        mods.add(new XRay());
        //client
        mods.add(new CapeMod());
        mods.add(new Clientrender());
        mods.add(new HUD());
        mods.add(new IRC());
        mods.add(new Notifications());
    }

    public Mod getByName(String name) {
        for (Mod mod : mods) {
            if (name.equalsIgnoreCase(mod.getName())) {
                return mod;
            }
        }
        return null;
    }

    public Mod getByClass(Class<? extends Mod> modClass) {
        for (Mod mod : mods) {
            if (mod.getClass() == modClass) {
                return mod;
            }
        }
        return null;
    }

    public List<Mod> getByCategory(Category category) {
        return mods.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
    }
}
