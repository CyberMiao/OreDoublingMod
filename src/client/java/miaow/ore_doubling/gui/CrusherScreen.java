package miaow.ore_doubling.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import miaow.ore_doubling.OreDoubling;
import miaow.ore_doubling.screen.CrusherScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrusherScreen extends HandledScreen<CrusherScreenHandler> {
    // 确保你在 src/main/resources/assets/oremod/textures/gui/crusher_gui.png 放置了纹理
    private static final Identifier TEXTURE = Identifier.of(OreDoubling.MOD_ID, "textures/gui/crusher_gui.png");

    public CrusherScreen(CrusherScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // 如果 GUI 标题字体不是居中的，可以在这里调整 titleX 和 titleY
        // titleY = 1000; // 隐藏默认标题 (如果需要)
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // 1. 绘制背景
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // 2. 绘制火焰 (修复版)
        if (handler.isBurning()) {
            int h = handler.getFuelProgress(); // 获取 0~14 的高度
            // h: 高度
            context.drawTexture(TEXTURE, x + 57, y + 52 - h, 176, 33 - h, 13, h + 1);
        }

        // 3. 绘制箭头
        int l = handler.getCraftingProgress();
        context.drawTexture(TEXTURE, x + 80, y + 35, 176, 0, l, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
