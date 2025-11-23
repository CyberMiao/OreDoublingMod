package miaow.ore_doubling.screen;

import miaow.ore_doubling.block.entity.CrusherBlockEntity;
import miaow.ore_doubling.registry.ModScreenHandlers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class CrusherScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final CrusherBlockEntity blockEntity;

    // 构造函数 1：客户端调用 (由 ExtendedScreenHandlerType 自动传入 buf)
    public CrusherScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(4));
    }

    // 构造函数 2：服务器调用 (由 CrusherBlockEntity 传入)
    public CrusherScreenHandler(int syncId, PlayerInventory playerInventory,
                                BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.CRUSHER_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((CrusherBlockEntity) blockEntity);

        // 添加机器自身的槽位
        this.addSlot(new Slot(inventory, 0, 56, 17)); // 输入槽 (坐标需根据GUI图片调整)
        this.addSlot(new Slot(inventory, 1, 56, 53)); // 燃料槽
        this.addSlot(new Slot(inventory, 2, 116, 35)); // 输出槽

        // 添加玩家物品栏 (标准 9x3)
        addPlayerInventory(playerInventory);
        // 添加玩家快捷栏
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    // 获取进度条百分比（用于前端渲染）
    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // 进度
        int progressArrowSize = 26; // 箭头图片的像素宽度

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFuel() {
        int fuel = this.propertyDelegate.get(2);
        int maxFuel = this.propertyDelegate.get(3); // 燃料
        int fuelIconHeight = 14; // 火焰图标的高度

        return maxFuel != 0 && fuel != 0 ? fuel * fuelIconHeight / maxFuel : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    // 辅助判断：是否有燃料正在燃烧
    public boolean isBurning() {
        return propertyDelegate.get(2) > 0; // fuelTime > 0
    }

    // 1. 箭头进度条逻辑
    public int getCraftingProgress() {
        int progress = this.propertyDelegate.get(0);    // 当前进度
        int maxProgress = this.propertyDelegate.get(1); // 总时间 (例如 72)
        int arrowPixelWidth = 24; // 原版熔炉箭头的总像素宽度

        if (maxProgress == 0 || progress == 0) {
            return 0;
        }
        // 计算公式：(当前 / 总) * 像素宽度
        return progress * arrowPixelWidth / maxProgress;
    }

    // 2. 火焰燃烧逻辑
    public int getFuelProgress() {
        int currentFuelTime = this.propertyDelegate.get(2); // 剩余燃烧时间
        int maxFuelTime = this.propertyDelegate.get(3);     // 总燃烧时间 (例如煤炭是1600)
        int firePixelHeight = 13; // 原版火焰的总像素高度

        if (maxFuelTime == 0) {
            firePixelHeight = 13;
            return 0;
        }

        // 计算公式：(剩余 / 总) * 像素高度
        return currentFuelTime * firePixelHeight / maxFuelTime;
    }
}