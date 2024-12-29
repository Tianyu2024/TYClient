package cn.tianyu.client.module.impl.useful;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.util.EnumParticleTypes;

public class fenliLizi extends Mod {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public fenliLizi() {
        super("轻击粒子", "打东西就会冒出锋利粒子", Category.useful);
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public void ontick() {
        super.ontick();

        // 获取玩家对象
        EntityPlayer player = minecraft.thePlayer;

        if (player != null && minecraft.objectMouseOver != null) {
            // 检查玩家是否正在攻击
            if (minecraft.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                // 获取玩家攻击的目标实体
                Entity targetEntity = minecraft.objectMouseOver.entityHit;

                if (targetEntity != null && player.isSwingInProgress) {
                    // 当玩家攻击目标时，生成锋利粒子效果
                    spawnSharpParticle(player, targetEntity);
                }
            }
        }
    }

    private void spawnSharpParticle(EntityPlayer player, Entity targetEntity) {
        // 获取玩家和目标实体的位置
        Vec3 playerPos = player.getPositionEyes(1.0F); // 获取玩家的视点位置
        Vec3 targetPos = targetEntity.getPositionVector(); // 获取目标实体的位置

        // 计算目标实体与玩家之间的向量方向
        Vec3 direction = targetPos.subtract(playerPos).normalize();

        // 在目标实体附近生成多个粒子，模拟锋利粒子的效果
        spawnParticle(player.worldObj, playerPos, direction);
    }

    private void spawnParticle(World world, Vec3 position, Vec3 direction) {
        // 在目标位置生成锋利粒子
        world.spawnParticle(EnumParticleTypes.CRIT, position.xCoord, position.yCoord, position.zCoord,
                direction.xCoord * 5, direction.yCoord * 5, direction.zCoord * 5);
    }
}
