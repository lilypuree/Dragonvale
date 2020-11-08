package lilypuree.dragonvale.dragons.entity.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lilypuree.dragonvale.dragons.DragonGrowthStages;
import lilypuree.dragonvale.dragons.entity.DragonEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.util.JSONException;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.animation.builder.Animation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;
import software.bernie.geckolib.reload.ReloadManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class DragonModelBase<T extends DragonEntityBase> extends AnimatedEntityModel<T> {


    private int level;
    private AnimatedEntityModel<T> babyModel;
    private AnimatedEntityModel<T> adultModel;


    public DragonModelBase(AnimatedEntityModel<T> babyModel, AnimatedEntityModel<T> adultModel) {
        this.babyModel = babyModel;
        this.adultModel = adultModel;
        IReloadableResourceManager resourceManager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
        reloadResourceManagerForModel(babyModel, resourceManager);
        reloadResourceManagerForModel(adultModel, resourceManager);
    }

    private AnimatedEntityModel<T> getModelWithAge(){
        if (level < 4) {
           return babyModel;
        } else {
           return adultModel;
        }
    }

    @Override
    public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        getModelWithAge().setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setRotationAngles(T t, float v, float v1, float v2, float v3, float v4) {
        getModelWithAge().setRotationAngles(t, v, v1, v2, v3, v4);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        getModelWithAge().render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    public void reloadResourceManagerForModel(AnimatedEntityModel<T> model, IResourceManager resourceManager){
        try {
            Gson GSON = new Gson();
            SimpleResource resource = (SimpleResource) resourceManager.getResource(model.getAnimationFileLocation());
            InputStreamReader stream = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(stream);
            JsonObject jsonobject = (JsonObject) JSONUtils.fromJson(GSON, reader, JsonObject.class);
            resource.close();
            stream.close();
            model.setAnimationFile(jsonobject);
            Method loadAllAnimations = ObfuscationReflectionHelper.findMethod(AnimatedEntityModel.class, "loadAllAnimations");
            loadAllAnimations.invoke(model);
        } catch (IOException var7) {
            GeckoLib.LOGGER.error("Encountered error while loading animations.", var7);
            throw new RuntimeException(var7);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AnimatedModelRenderer getBone(String boneName) {
        return getModelWithAge().getBone(boneName);
    }

    @Override
    public Animation getAnimation(String name) {
        return getModelWithAge().getAnimation(name);
    }

    @Override
    public JsonObject getAnimationFile() {
        return getModelWithAge().getAnimationFile();
    }

    @Override
    public Map.Entry<String, JsonElement> getAnimationByName(String name) throws JSONException {
        return getModelWithAge().getAnimationByName(name);
    }

    @Override
    public ResourceLocation getAnimationFileLocation() {
        return null;
    }
}
