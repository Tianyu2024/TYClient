package cn.tianyu.client.module.impl.abab;

import cn.tianyu.client.module.Category;
import cn.tianyu.client.module.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author CN_tianyu
 */
public class acdsda extends Mod {

    private final Minecraft mc = Minecraft.getMinecraft();

    public acdsda() {
        super("箱子小偷", "自动拿走箱子", Category.abab);
    }

    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }

    public void update() {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        // 检查当前玩家是否打开了箱子
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest) mc.currentScreen;
            // 获取箱子中的所有槽位
            List<Slot> chestSlots = guiChest.inventorySlots.inventorySlots;

            // 遍历箱子中的所有槽位
            for (int i = 0; i < chestSlots.size(); i++) {
                Slot slot = chestSlots.get(i);
                ItemStack stackInSlot = slot.getStack();

                // 检查该槽位是否有物品，并且背包有空位
                if (stackInSlot != null && stackInSlot.stackSize > 0 && canTakeItem()) {
                    // 找到一个背包空位
                    for (int j = 9; j < 36; j++) {
                        Slot playerSlot = mc.thePlayer.inventoryContainer.getSlot(j);
                        if (playerSlot.getStack() == null || playerSlot.getStack().stackSize == 0) {
                            // 拿起箱子里的物品到玩家背包
                            // windowClick(窗口ID, 槽位索引, 鼠标按钮, 操作模式, 玩家)
                            mc.playerController.windowClick(guiChest.inventorySlots.windowId, i, 0, 1, mc.thePlayer);
                            return; // 一次只取一组物品，取完一组后就返回
                        }
                    }
                }
            }
        }
    }

    // 检查背包是否有空位
    private boolean canTakeItem() {
        // 获取玩家背包中的所有槽位
        List<Slot> playerSlots = mc.thePlayer.inventoryContainer.inventorySlots;

        for (Slot slot : playerSlots) {
            // 检查槽位是否为空
            if (slot.getStack() == null || slot.getStack().stackSize <= 0) {
                return true; // 背包有空位，可以取物品
            }
        }
        return false; // 背包没有空位，不能继续拿物品
    }
}
