package lilypuree.dragonvale.core;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MockPigRenderer extends MobRenderer<MockPigEntity, MockPigModel<MockPigEntity>> {
    private static final ResourceLocation PIG_TEXTURES = new ResourceLocation("textures/entity/pig/pig.png");

    public MockPigRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MockPigModel<>(), 0.7F);
//        this.addLayer(new SaddleLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MockPigEntity entity) {
        return PIG_TEXTURES;
    }
}
