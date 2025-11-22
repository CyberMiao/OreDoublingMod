// src/main/java/miaow/ore_doubling/item/CrystalItem.java
package miaow.ore_doubling.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class CrystalItem extends Item {
    private final int color;

    public CrystalItem(Properties properties, int color) {
        super(properties);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }
}