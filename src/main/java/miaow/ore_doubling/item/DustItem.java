// src/main/java/miaow/ore_doubling/item/DustItem.java
package miaow.ore_doubling.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class DustItem extends Item {
    private final int color;
    private final float purity; // 0.0 - 1.0

    public DustItem(Properties properties, int color, float purity) {
        super(properties);
        this.color = color;
        this.purity = purity;
    }

    public int getColor() {
        return color;
    }

    public float getPurity() {
        return purity;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = super.getName(stack);
        if (purity < 1.0f) {
            return baseName.copy().withStyle(ChatFormatting.GRAY);
        }
        return baseName.copy().withStyle(ChatFormatting.WHITE);
    }
}