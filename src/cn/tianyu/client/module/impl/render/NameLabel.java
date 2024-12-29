package cn.tianyu.client.module.impl.render;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import cn.tianyu.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author CN_tianyu
 */

public class NameLabel extends Mod {
    public NameLabel() {
        super("NameLabel", "名字标签", Category.render);
    }

    @Override
    public void render(float partialTicks) {
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity instanceof EntityPlayerSP) {
                    continue;
                }
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                RenderUtil.renderLabel(entityLivingBase, entityLivingBase.getDisplayName().getFormattedText() + " " + String.format("%s/%s", entityLivingBase.getMaxHealth(), entityLivingBase.getHealth()), 64, partialTicks);
            }
        }
    }
}
