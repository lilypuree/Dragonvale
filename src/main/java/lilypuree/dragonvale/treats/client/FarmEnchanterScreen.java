package lilypuree.dragonvale.treats.client;

import com.mojang.blaze3d.systems.RenderSystem;
import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.treats.blocks.FarmEnchanterContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FarmEnchanterScreen extends ContainerScreen<FarmEnchanterContainer> {
    private static final ResourceLocation ENCHANTER_RESOURCE = new ResourceLocation(DragonvaleMain.MODID, "textures/gui/container/farm_enchanter.png");

    public FarmEnchanterScreen(FarmEnchanterContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        this.font.drawString(this.title.getFormattedText(), 60.0F, 6.0F, 4210752);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(ENCHANTER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        this.blit(i + 59, j + 20, 0, this.ySize + (this.container.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.container.getSlot(0).getHasStack() || this.container.getSlot(1).getHasStack()) && !this.container.getSlot(2).getHasStack()) {
            this.blit(i + 99, j + 45, this.xSize, 0, 28, 21);
        }
    }

}
