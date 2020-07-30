package lilypuree.dragonvale.dragons.entity.client;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.dragons.entity.FireDragonEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
public class BabyFireDragonModel extends AnimatedEntityModel<FireDragonEntity> {


    private final AnimatedModelRenderer spike3;
    private final AnimatedModelRenderer spike4;
    private final AnimatedModelRenderer spike5;
    private final AnimatedModelRenderer body;
    private final AnimatedModelRenderer lwing;
    private final AnimatedModelRenderer rwing;
    private final AnimatedModelRenderer tail3;
    private final AnimatedModelRenderer tail1;
    private final AnimatedModelRenderer tail2;
    private final AnimatedModelRenderer claw4;
    private final AnimatedModelRenderer claw3;
    private final AnimatedModelRenderer claw2;
    private final AnimatedModelRenderer claw1;
    private final AnimatedModelRenderer rarm;
    private final AnimatedModelRenderer larm;
    private final AnimatedModelRenderer spike1;
    private final AnimatedModelRenderer spike2;
    private final AnimatedModelRenderer lleg;
    private final AnimatedModelRenderer rleg;
    private final AnimatedModelRenderer lfoot;
    private final AnimatedModelRenderer rfoot;
    private final AnimatedModelRenderer head;
    private final AnimatedModelRenderer hspike1;
    private final AnimatedModelRenderer hspike2;

    public BabyFireDragonModel() {
        textureWidth = 64;
        textureHeight = 64;
        spike3 = new AnimatedModelRenderer(this);
        spike3.setRotationPoint(0.0F, 17.0F, 2.9F);
        spike3.setTextureSize(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike3.setModelRendererName("spike3");
        this.registerModelRenderer(spike3);

        spike4 = new AnimatedModelRenderer(this);
        spike4.setRotationPoint(0.0F, 1.2F, 4.0F);
        spike3.addChild(spike4);
        spike4.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike4.setModelRendererName("spike4");
        this.registerModelRenderer(spike4);

        spike5 = new AnimatedModelRenderer(this);
        spike5.setRotationPoint(0.0F, 2.5F, 8.0F);
        spike3.addChild(spike5);
        spike5.setTextureOffset(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike5.setModelRendererName("spike5");
        this.registerModelRenderer(spike5);

        body = new AnimatedModelRenderer(this);
        body.setRotationPoint(0.0F, -6.0F, -8.0F);
        spike3.addChild(body);
        setRotationAngle(body, 0.3491F, 0.0F, 0.0F);
        body.setTextureOffset(0, 17).addBox(-3.5F, -1.0F, -3.5F, 7.0F, 12.0F, 7.0F, 0.0F, false);
        body.setModelRendererName("body");
        this.registerModelRenderer(body);

        lwing = new AnimatedModelRenderer(this);
        lwing.setRotationPoint(3.5F, 3.0F, 3.0F);
        body.addChild(lwing);
        setRotationAngle(lwing, 0.4363F, 0.0F, 0.0F);
        lwing.setTextureOffset(11, 32).addBox(0.02F, -4.5F, -1.0F, 0.0F, 10.0F, 20.0F, 0.0F, false);
        lwing.setModelRendererName("lwing");
        this.registerModelRenderer(lwing);

        rwing = new AnimatedModelRenderer(this);
        rwing.setRotationPoint(-3.5F, 3.0F, 3.0F);
        body.addChild(rwing);
        setRotationAngle(rwing, 0.4363F, 0.0F, 0.0F);
        rwing.setTextureOffset(11, 32).addBox(-0.02F, -4.5F, -1.0F, 0.0F, 10.0F, 20.0F, 0.0F, false);
        rwing.setModelRendererName("rwing");
        this.registerModelRenderer(rwing);

        tail3 = new AnimatedModelRenderer(this);
        tail3.setRotationPoint(0.0F, 4.0F, 6.0F);
        spike3.addChild(tail3);
        tail3.setTextureOffset(33, 21).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 4.0F, 0.0F, false);
        tail3.setModelRendererName("tail3");
        this.registerModelRenderer(tail3);

        tail1 = new AnimatedModelRenderer(this);
        tail1.setRotationPoint(0.0F, 3.0F, -2.0F);
        spike3.addChild(tail1);
        tail1.setTextureOffset(33, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 4.0F, 0.0F, false);
        tail1.setModelRendererName("tail1");
        this.registerModelRenderer(tail1);

        tail2 = new AnimatedModelRenderer(this);
        tail2.setRotationPoint(0.0F, 3.7F, 2.0F);
        spike3.addChild(tail2);
        tail2.setTextureOffset(33, 11).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 4.0F, 0.0F, false);
        tail2.setModelRendererName("tail2");
        this.registerModelRenderer(tail2);

        claw4 = new AnimatedModelRenderer(this);
        claw4.setRotationPoint(-5.5F, 0.0F, -12.0F);
        spike3.addChild(claw4);
        setRotationAngle(claw4, 0.5236F, 0.0F, 0.0F);
        claw4.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        claw4.setModelRendererName("claw4");
        this.registerModelRenderer(claw4);

        claw3 = new AnimatedModelRenderer(this);
        claw3.setRotationPoint(-3.5F, 0.0F, -12.0F);
        spike3.addChild(claw3);
        setRotationAngle(claw3, 0.5236F, 0.0F, 0.0F);
        claw3.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        claw3.setModelRendererName("claw3");
        this.registerModelRenderer(claw3);

        claw2 = new AnimatedModelRenderer(this);
        claw2.setRotationPoint(5.5F, 0.0F, -12.0F);
        spike3.addChild(claw2);
        setRotationAngle(claw2, 0.5236F, 0.0F, 0.0F);
        claw2.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        claw2.setModelRendererName("claw2");
        this.registerModelRenderer(claw2);

        claw1 = new AnimatedModelRenderer(this);
        claw1.setRotationPoint(3.5F, 0.0F, -12.0F);
        spike3.addChild(claw1);
        setRotationAngle(claw1, 0.5236F, 0.0F, 0.0F);
        claw1.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        claw1.setModelRendererName("claw1");
        this.registerModelRenderer(claw1);

        rarm = new AnimatedModelRenderer(this);
        rarm.setRotationPoint(-4.5F, -2.0F, -7.0F);
        spike3.addChild(rarm);
        setRotationAngle(rarm, 0.5236F, 0.0F, 0.0F);
        rarm.setTextureOffset(0, 44).addBox(-1.0F, -1.5F, -5.0F, 2.0F, 3.0F, 5.0F, 0.0F, false);
        rarm.setModelRendererName("rarm");
        this.registerModelRenderer(rarm);

        larm = new AnimatedModelRenderer(this);
        larm.setRotationPoint(4.5F, -2.0F, -7.0F);
        spike3.addChild(larm);
        setRotationAngle(larm, 0.5236F, 0.0F, 0.0F);
        larm.setTextureOffset(0, 44).addBox(-1.0F, -1.5F, -5.0F, 2.0F, 3.0F, 5.0F, 0.0F, false);
        larm.setModelRendererName("larm");
        this.registerModelRenderer(larm);

        spike1 = new AnimatedModelRenderer(this);
        spike1.setRotationPoint(0.0F, -5.0F, -4.0F);
        spike3.addChild(spike1);
        setRotationAngle(spike1, 0.3491F, 0.0F, 0.0F);
        spike1.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike1.setModelRendererName("spike1");
        this.registerModelRenderer(spike1);

        spike2 = new AnimatedModelRenderer(this);
        spike2.setRotationPoint(0.0F, -2.0F, -2.0F);
        spike3.addChild(spike2);
        setRotationAngle(spike2, 0.3491F, 0.0F, 0.0F);
        spike2.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spike2.setModelRendererName("spike2");
        this.registerModelRenderer(spike2);

        lleg = new AnimatedModelRenderer(this);
        lleg.setRotationPoint(5.0F, 1.0F, -4.0F);
        spike3.addChild(lleg);
        lleg.setTextureOffset(33, 29).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
        lleg.setModelRendererName("lleg");
        this.registerModelRenderer(lleg);

        rleg = new AnimatedModelRenderer(this);
        rleg.setRotationPoint(-5.0F, 1.0F, -4.0F);
        spike3.addChild(rleg);
        rleg.setTextureOffset(33, 29).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
        rleg.setModelRendererName("rleg");
        this.registerModelRenderer(rleg);

        lfoot = new AnimatedModelRenderer(this);
        lfoot.setRotationPoint(5.0F, 1.0F, -4.0F);
        spike3.addChild(lfoot);
        lfoot.setTextureOffset(0, 37).addBox(-2.5F, 4.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
        lfoot.setModelRendererName("lfoot");
        this.registerModelRenderer(lfoot);

        rfoot = new AnimatedModelRenderer(this);
        rfoot.setRotationPoint(-5.0F, 1.0F, -4.0F);
        spike3.addChild(rfoot);
        rfoot.setTextureOffset(0, 37).addBox(-2.5F, 4.0F, -3.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
        rfoot.setModelRendererName("rfoot");
        this.registerModelRenderer(rfoot);

        head = new AnimatedModelRenderer(this);
        head.setRotationPoint(0.0F, -5.0F, -7.0F);
        spike3.addChild(head);
        head.setTextureOffset(0, 0).addBox(-4.0F, -7.0F, -7.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.setModelRendererName("head");
        this.registerModelRenderer(head);

        hspike1 = new AnimatedModelRenderer(this);
        hspike1.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hspike1);
        hspike1.setTextureOffset(0, 0).addBox(-0.5F, -8.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hspike1.setModelRendererName("hspike1");
        this.registerModelRenderer(hspike1);

        hspike2 = new AnimatedModelRenderer(this);
        hspike2.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hspike2);
        hspike2.setTextureOffset(0, 0).addBox(-0.5F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hspike2.setModelRendererName("hspike2");
        this.registerModelRenderer(hspike2);

        this.rootBones.add(spike3);
    }

    @Override
    public ResourceLocation getAnimationFileLocation() {
        return new ResourceLocation(DragonvaleMain.MODID, "animations/babyfiredragon.json");
    }
}