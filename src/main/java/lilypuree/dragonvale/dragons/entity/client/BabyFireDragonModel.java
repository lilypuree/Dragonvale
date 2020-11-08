package lilypuree.dragonvale.dragons.entity.client;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.dragons.entity.FireDragonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
@OnlyIn(Dist.CLIENT)
public class BabyFireDragonModel extends AnimatedEntityModel<FireDragonEntity> {

    private final AnimatedModelRenderer root;
    private final AnimatedModelRenderer head;
    private final AnimatedModelRenderer hspike1;
    private final AnimatedModelRenderer hspike2;
    private final AnimatedModelRenderer rleg;
    private final AnimatedModelRenderer rfoot;
    private final AnimatedModelRenderer lleg;
    private final AnimatedModelRenderer lfoot;
    private final AnimatedModelRenderer larm;
    private final AnimatedModelRenderer lclaw;
    private final AnimatedModelRenderer rarm;
    private final AnimatedModelRenderer rclaw;
    private final AnimatedModelRenderer tail_first;
    private final AnimatedModelRenderer spike_tail_first;
    private final AnimatedModelRenderer tail_mid;
    private final AnimatedModelRenderer spike_tail_mid;
    private final AnimatedModelRenderer tail_last;
    private final AnimatedModelRenderer spike_tail_last;
    private final AnimatedModelRenderer body;
    private final AnimatedModelRenderer lwing;
    private final AnimatedModelRenderer rwing;
    private final AnimatedModelRenderer spike_body_1;
    private final AnimatedModelRenderer spike_body_2;


    public BabyFireDragonModel()
    {
        textureWidth = 64;
        textureHeight = 64;
        root = new AnimatedModelRenderer(this);
        root.setRotationPoint(0.0F, 17.0F, 2.9F);

        root.setModelRendererName("root");
        this.registerModelRenderer(root);

        head = new AnimatedModelRenderer(this);
        head.setRotationPoint(0.0F, -5.0F, -7.0F);
        root.addChild(head);
        head.setTextureOffset(0, 0).addBox(-4.0F, -7.0F, -7.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.setModelRendererName("head");
        this.registerModelRenderer(head);

        hspike1 = new AnimatedModelRenderer(this);
        hspike1.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hspike1);
        hspike1.setTextureOffset(0, 0).addBox(-0.5F, -8.0F, -2.9F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hspike1.setModelRendererName("hspike1");
        this.registerModelRenderer(hspike1);

        hspike2 = new AnimatedModelRenderer(this);
        hspike2.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hspike2);
        hspike2.setTextureOffset(0, 0).addBox(-0.5F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hspike2.setModelRendererName("hspike2");
        this.registerModelRenderer(hspike2);

        rleg = new AnimatedModelRenderer(this);
        rleg.setRotationPoint(-5.0F, 1.0F, -4.0F);
        root.addChild(rleg);
        rleg.setTextureOffset(33, 29).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
        rleg.setModelRendererName("rleg");
        this.registerModelRenderer(rleg);

        rfoot = new AnimatedModelRenderer(this);
        rfoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        rleg.addChild(rfoot);
        rfoot.setTextureOffset(0, 37).addBox(-2.5F, 4.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
        rfoot.setModelRendererName("rfoot");
        this.registerModelRenderer(rfoot);

        lleg = new AnimatedModelRenderer(this);
        lleg.setRotationPoint(5.0F, 1.0F, -4.0F);
        root.addChild(lleg);
        lleg.setTextureOffset(33, 29).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, true);
        lleg.setModelRendererName("lleg");
        this.registerModelRenderer(lleg);

        lfoot = new AnimatedModelRenderer(this);
        lfoot.setRotationPoint(0.0F, 0.0F, 0.0F);
        lleg.addChild(lfoot);
        lfoot.setTextureOffset(0, 37).addBox(-2.5F, 4.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, true);
        lfoot.setModelRendererName("lfoot");
        this.registerModelRenderer(lfoot);

        larm = new AnimatedModelRenderer(this);
        larm.setRotationPoint(4.5F, -2.0F, -7.0F);
        root.addChild(larm);
        setRotationAngle(larm, 0.5236F, 0.0F, 0.0F);
        larm.setTextureOffset(0, 44).addBox(-1.0F, -1.5F, -5.0F, 2.0F, 3.0F, 5.0F, 0.0F, true);
        larm.setModelRendererName("larm");
        this.registerModelRenderer(larm);

        lclaw = new AnimatedModelRenderer(this);
        lclaw.setRotationPoint(-9.0F, 0.0F, -5.0F);
        larm.addChild(lclaw);
        lclaw.setTextureOffset(0, 3).addBox(-1.5F, 0.0F, -0.9F, 3.0F, 0.0F, 1.0F, 0.0F, false);
        lclaw.setModelRendererName("lclaw");
        this.registerModelRenderer(lclaw);

        rarm = new AnimatedModelRenderer(this);
        rarm.setRotationPoint(-4.5F, -2.0F, -7.0F);
        root.addChild(rarm);
        setRotationAngle(rarm, 0.5236F, 0.0F, 0.0F);
        rarm.setTextureOffset(0, 44).addBox(-1.0F, -1.5F, -5.0F, 2.0F, 3.0F, 5.0F, 0.0F, false);
        rarm.setModelRendererName("rarm");
        this.registerModelRenderer(rarm);

        rclaw = new AnimatedModelRenderer(this);
        rclaw.setRotationPoint(0.0F, 0.0F, -5.0F);
        rarm.addChild(rclaw);
        rclaw.setTextureOffset(0, 3).addBox(7.5F, 0.0F, -0.9F, 3.0F, 0.0F, 1.0F, 0.0F, false);
        rclaw.setModelRendererName("rclaw");
        this.registerModelRenderer(rclaw);

        tail_first = new AnimatedModelRenderer(this);
        tail_first.setRotationPoint(0.0F, 3.0F, -2.0F);
        root.addChild(tail_first);
        tail_first.setTextureOffset(33, 0).addBox(-3.0F, -3.0F, -0.9F, 6.0F, 6.0F, 4.0F, 0.0F, false);
        tail_first.setModelRendererName("tail_first");
        this.registerModelRenderer(tail_first);

        spike_tail_first = new AnimatedModelRenderer(this);
        spike_tail_first.setRotationPoint(0.0F, -3.0F, 2.0F);
        tail_first.addChild(spike_tail_first);
        spike_tail_first.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike_tail_first.setModelRendererName("spike_tail_first");
        this.registerModelRenderer(spike_tail_first);

        tail_mid = new AnimatedModelRenderer(this);
        tail_mid.setRotationPoint(0.0F, 0.7F, 4.0F);
        tail_first.addChild(tail_mid);
        tail_mid.setTextureOffset(33, 11).addBox(-2.5F, -2.7F, -0.9F, 5.0F, 5.0F, 4.0F, 0.0F, false);
        tail_mid.setModelRendererName("tail_mid");
        this.registerModelRenderer(tail_mid);

        spike_tail_mid = new AnimatedModelRenderer(this);
        spike_tail_mid.setRotationPoint(0.0F, -2.5F, 1.0F);
        tail_mid.addChild(spike_tail_mid);
        spike_tail_mid.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike_tail_mid.setModelRendererName("spike_tail_mid");
        this.registerModelRenderer(spike_tail_mid);

        tail_last = new AnimatedModelRenderer(this);
        tail_last.setRotationPoint(0.0F, 0.3F, 3.1F);
        tail_mid.addChild(tail_last);
        tail_last.setTextureOffset(33, 21).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, 0.0F, false);
        tail_last.setModelRendererName("tail_last");
        this.registerModelRenderer(tail_last);

        spike_tail_last = new AnimatedModelRenderer(this);
        spike_tail_last.setRotationPoint(0.0F, -1.5F, 2.9F);
        tail_last.addChild(spike_tail_last);
        spike_tail_last.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike_tail_last.setModelRendererName("spike_tail_last");
        this.registerModelRenderer(spike_tail_last);

        body = new AnimatedModelRenderer(this);
        body.setRotationPoint(0.0F, -6.0F, -8.0F);
        root.addChild(body);
        setRotationAngle(body, 0.3491F, 0.0F, 0.0F);
        body.setTextureOffset(0, 17).addBox(-3.5F, -1.0F, -3.5F, 7.0F, 12.0F, 7.0F, 0.0F, false);
        body.setModelRendererName("body");
        this.registerModelRenderer(body);

        lwing = new AnimatedModelRenderer(this);
        lwing.setRotationPoint(3.5F, 3.0F, 3.0F);
        body.addChild(lwing);
        setRotationAngle(lwing, 0.4363F, 0.0F, 0.0F);
        lwing.setTextureOffset(19, 40).addBox(0.02F, -4.0F, -0.9F, 0.0F, 8.0F, 13.0F, 0.0F, false);
        lwing.setModelRendererName("lwing");
        this.registerModelRenderer(lwing);

        rwing = new AnimatedModelRenderer(this);
        rwing.setRotationPoint(-3.5F, 3.0F, 3.0F);
        body.addChild(rwing);
        setRotationAngle(rwing, 0.4363F, 0.0F, 0.0F);
        rwing.setTextureOffset(19, 40).addBox(-0.02F, -4.0F, -0.9F, 0.0F, 8.0F, 13.0F, 0.0F, false);
        rwing.setModelRendererName("rwing");
        this.registerModelRenderer(rwing);

        spike_body_1 = new AnimatedModelRenderer(this);
        spike_body_1.setRotationPoint(0.0F, 1.6F, 4.0F);
        body.addChild(spike_body_1);
        setRotationAngle(spike_body_1, -1.5708F, 0.0F, 0.0F);
        spike_body_1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike_body_1.setModelRendererName("spike_body_1");
        this.registerModelRenderer(spike_body_1);

        spike_body_2 = new AnimatedModelRenderer(this);
        spike_body_2.setRotationPoint(0.0F, 5.0F, 4.0F);
        body.addChild(spike_body_2);
        setRotationAngle(spike_body_2, -1.5708F, 0.0F, 0.0F);
        spike_body_2.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike_body_2.setModelRendererName("spike_body_2");
        this.registerModelRenderer(spike_body_2);

        this.rootBones.add(root);
    }

    @Override
    public ResourceLocation getAnimationFileLocation() {
        return new ResourceLocation(DragonvaleMain.MODID, "animations/babyfiredragon.json");
    }

    @Override
    public void setRotationAngles(FireDragonEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.tail_first.rotateAngleY = this.head.rotateAngleY * -1 * 0.1F;
        this.tail_mid.rotateAngleY = this.head.rotateAngleY * -1 * 0.07F;
        this.tail_last.rotateAngleY = this.head.rotateAngleY * -1 * 0.05F;
    }
}