package lilypuree.dragonvale.dragons.entity.client;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.dragons.entity.FireDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FireDragonRenderer extends DragonRendererBase<FireDragonEntity> {

    private static final ResourceLocation BFD_TEXTURE = new ResourceLocation(DragonvaleMain.MODID, "textures/entity/babyfiredragon.png");
    private static final ResourceLocation AFD_TEXTURE = new ResourceLocation(DragonvaleMain.MODID, "textures/entity/adultfiredragon.png");


    public FireDragonRenderer(EntityRendererManager rendererManager){
        super(rendererManager, new DragonModelBase<>(new BabyFireDragonModel(), new AdultFireDragonModel()), 0.8f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(FireDragonEntity entity) {
        if(entity.isChild()){
            return BFD_TEXTURE;
        }else {
            return AFD_TEXTURE;
        }
    }


}
