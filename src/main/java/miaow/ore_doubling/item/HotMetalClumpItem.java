// src/main/java/miaow/ore_doubling/item/HotMetalClumpItem.java
package miaow.ore_doubling.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class HotMetalClumpItem extends Item {
    private final int color;

    public HotMetalClumpItem(Properties properties, int color) {
        super(properties);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // 如果玩家误食，会受到伤害
        if (!level.isClientSide) {
            entity.hurt(level.damageSources().onFire(), 2.0f);
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        }
        return stack;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.RED);
    }
}