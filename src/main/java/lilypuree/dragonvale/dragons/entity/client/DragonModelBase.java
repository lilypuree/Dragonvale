package lilypuree.dragonvale.dragons.entity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lilypuree.dragonvale.dragons.DragonGrowthStages;
import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;

public class DragonModelBase<T extends DragonEntityBase> extends EntityModel<T> {

    private int level;
    private AnimatedEntityModel<T> babyModel;
    private AnimatedEntityModel<T> adultModel;

    public DragonModelBase(AnimatedEntityModel<T> babyModel, AnimatedEntityModel<T> adultModel) {
        this.babyModel = babyModel;
        this.adultModel = adultModel;
    }

    @Override
    public void setRotationAngles(T t, float v, float v1, float v2, float v3, float v4) {
        if (this.isChild) {
            babyModel.setRotationAngles(t, v, v1, v2, v3, v4);
        } else {
            adultModel.setRotationAngles(t, v, v1, v2, v3, v4);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        if (level < 4) {
            babyModel.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        } else {
            adultModel.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
