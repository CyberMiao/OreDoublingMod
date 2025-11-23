package miaow.ore_doubling.block.entity;

import miaow.ore_doubling.inventory.ImplementedInventory;
import miaow.ore_doubling.recipe.CrushingRecipe;
import miaow.ore_doubling.registry.ModBlockEntities;
import miaow.ore_doubling.registry.ModRecipes;
import miaow.ore_doubling.screen.CrusherScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// 假设你已经有了 ImplementedInventory 接口，如果没有，请参考 Fabric Wiki
public class CrusherBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    // 进度条数据
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72; // 处理时间
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            // 实现 get/set 用于 GUI 同步数据
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    case 2 -> CrusherBlockEntity.this.fuelTime;
                    case 3 -> CrusherBlockEntity.this.maxFuelTime;
                    default -> 0;
                };
            }
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrusherBlockEntity.this.progress = value;
                    case 1 -> CrusherBlockEntity.this.maxProgress = value;
                    case 2 -> CrusherBlockEntity.this.fuelTime = value;
                    case 3 -> CrusherBlockEntity.this.maxFuelTime = value;
                }
            }
            public int size() { return 4; }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory; }

    // 每一 tick 执行的逻辑
    public static void tick(World world, BlockPos pos, BlockState state, CrusherBlockEntity entity) {
        if(world.isClient) return;

        // 处理燃料
        if(entity.isConsumingFuel()) {
            entity.fuelTime--;
        }

        // 检查是否有配方匹配
        if(entity.hasRecipe()) {
            // 如果没燃料但有煤炭，添加燃料
            if(!entity.isConsumingFuel() && entity.hasFuelInSlot()) {
                entity.consumeFuel();
            }

            // 如果有燃料，开始工作
            if(entity.isConsumingFuel()) {
                entity.progress++;
                if(entity.progress >= entity.maxProgress) {
                    entity.craftItem();
                    entity.progress = 0;
                }
            }
        } else {
            entity.progress = 0;
        }

        // 标记脏数据以保存
        markDirty(world, pos, state);
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<CrushingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().value().getResult(null); // 获取配方结果

        // 检查输出槽是否满了
        ItemStack outputSlot = this.getStack(2);
        return outputSlot.isEmpty() || (outputSlot.getItem() == result.getItem() && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxCount());
    }

    private void craftItem() {
        Optional<RecipeEntry<CrushingRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().getResult(null);

        // 消耗输入
        this.removeStack(0, 1);

        // 增加输出
        ItemStack outputSlot = this.getStack(2);
        if(outputSlot.isEmpty()) {
            this.setStack(2, result.copy());
        } else {
            outputSlot.increment(result.getCount());
        }
    }

    private Optional<RecipeEntry<CrushingRecipe>> getCurrentRecipe() {
        // 创建一个临时的 Inventory 用于匹配配方 (RecipeInput 的一种实现)
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i=0; i<this.size(); i++) inv.setStack(i, this.getStack(i));

        // 在 1.21 中，配方查找可能需要适配 RecipeInput
        // 这里简化处理，假设你有自定义的方法或适配器
        return this.world.getRecipeManager().getFirstMatch(ModRecipes.CRUSHING_TYPE, new SingleStackRecipeInput(this.getStack(0)), this.world);
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.ore_doubling.crusher");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrusherScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    // 辅助方法：简化的 RecipeInput 封装 (1.21 需要)
    public record SingleStackRecipeInput(ItemStack item) implements RecipeInput {
        @Override public ItemStack getStackInSlot(int slot) { return item; }
        @Override public int getSize() { return 1; }
    }

    private boolean isConsumingFuel() { return this.fuelTime > 0; }
    private boolean hasFuelInSlot() { return !this.getStack(1).isEmpty() && this.getStack(1).getItem() == Items.COAL; } // 简化：只认煤炭
    private void consumeFuel() {
        this.removeStack(1, 1);
        this.fuelTime = 1600; // 煤炭燃烧时间
        this.maxFuelTime = 1600;
    }

    // NBT 数据保存与读取
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("crusher.progress", progress);
        nbt.putInt("crusher.fuelTime", fuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("crusher.progress");
        fuelTime = nbt.getInt("crusher.fuelTime");
    }

    // 屏幕相关接口方法 (略，用于打开GUI)
    // ... createMenu, getDisplayName ...
}
