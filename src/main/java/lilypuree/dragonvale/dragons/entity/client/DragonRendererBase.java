package lilypuree.dragonvale.dragons.entity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.Vec3d;

public abstract class DragonRendererBase<T extends DragonEntityBase> extends MobRenderer<T, DragonModelBase<T>> {

    private DragonModelBase<T> dragonModel;

    public DragonRendererBase(EntityRendererManager rendererManager, DragonModelBase<T> dragonModel, float shadowSize) {
        super(rendererManager, dragonModel, shadowSize);
        this.dragonModel = dragonModel;
    }


    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        int level = entityIn.getLevel();
        dragonModel.setLevel(level);
        float scale = DragonEntityBase.getScale(level);
        matrixStackIn.push();
        if (level < 4) {
            matrixStackIn.scale(scale, scale, scale);
        } else {
//            matrixStack.translate(0, -0.5, 0);
            matrixStackIn.scale(scale, scale, scale);
//            matrixStack.translate(0, +0.5, 0);
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.pop();
    }
}
