// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package lilypuree.dragonvale.dragons.entity.client;

import lilypuree.dragonvale.DragonvaleMain;
import lilypuree.dragonvale.dragons.entity.FireDragonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

@OnlyIn(Dist.CLIENT)
public class AdultFireDragonModel extends AnimatedEntityModel<FireDragonEntity> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer lleg;
	private final AnimatedModelRenderer lleg2;
	private final AnimatedModelRenderer lfoot;
	private final AnimatedModelRenderer rleg;
	private final AnimatedModelRenderer rleg2;
	private final AnimatedModelRenderer rfoot;
	private final AnimatedModelRenderer larm;
	private final AnimatedModelRenderer larm2;
	private final AnimatedModelRenderer lhand;
	private final AnimatedModelRenderer rarm;
	private final AnimatedModelRenderer rarm2;
	private final AnimatedModelRenderer rhand;
	private final AnimatedModelRenderer neck;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer ujaw;
	private final AnimatedModelRenderer ljaw;
	private final AnimatedModelRenderer tail;
	private final AnimatedModelRenderer tail2;
	private final AnimatedModelRenderer tail3;
	private final AnimatedModelRenderer tailfin;
	private final AnimatedModelRenderer lwing;
	private final AnimatedModelRenderer rwing;

    public AdultFireDragonModel()
    {
        textureWidth = 128;
		textureHeight = 128;
		body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 9.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 9.0F, 20.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		lleg = new AnimatedModelRenderer(this);
		lleg.setRotationPoint(4.5F, 3.0F, 1.0F);
		body.addChild(lleg);
		setRotationAngle(lleg, -0.1745F, 0.0F, 0.0F);
		lleg.setTextureOffset(60, 34).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 9.0F, 5.0F, 0.0F, false);
		lleg.setModelRendererName("lleg");
		this.registerModelRenderer(lleg);

		lleg2 = new AnimatedModelRenderer(this);
		lleg2.setRotationPoint(0.0F, 5.0F, -1.0F);
		lleg.addChild(lleg2);
		setRotationAngle(lleg2, 0.3491F, 0.0F, 0.0F);
		lleg2.setTextureOffset(45, 73).addBox(-1.5F, 0.5F, 1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		lleg2.setModelRendererName("lleg2");
		this.registerModelRenderer(lleg2);

		lfoot = new AnimatedModelRenderer(this);
		lfoot.setRotationPoint(0.0F, 6.0F, 2.0F);
		lleg2.addChild(lfoot);
		setRotationAngle(lfoot, -0.1745F, 0.0F, 0.0F);
		lfoot.setTextureOffset(70, 19).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
		lfoot.setModelRendererName("lfoot");
		this.registerModelRenderer(lfoot);

		rleg = new AnimatedModelRenderer(this);
		rleg.setRotationPoint(-4.5F, 3.0F, 1.0F);
		body.addChild(rleg);
		setRotationAngle(rleg, -0.1745F, 0.0F, 0.0F);
		rleg.setTextureOffset(0, 0).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 9.0F, 5.0F, 0.0F, false);
		rleg.setModelRendererName("rleg");
		this.registerModelRenderer(rleg);

		rleg2 = new AnimatedModelRenderer(this);
		rleg2.setRotationPoint(9.0F, 5.0F, -1.0F);
		rleg.addChild(rleg2);
		setRotationAngle(rleg2, 0.3491F, 0.0F, 0.0F);
		rleg2.setTextureOffset(33, 73).addBox(-10.5F, 0.5F, 1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		rleg2.setModelRendererName("rleg2");
		this.registerModelRenderer(rleg2);

		rfoot = new AnimatedModelRenderer(this);
		rfoot.setRotationPoint(0.0F, 6.0F, 2.0F);
		rleg2.addChild(rfoot);
		setRotationAngle(rfoot, -0.1745F, 0.0F, 0.0F);
		rfoot.setTextureOffset(68, 11).addBox(-11.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
		rfoot.setModelRendererName("rfoot");
		this.registerModelRenderer(rfoot);

		larm = new AnimatedModelRenderer(this);
		larm.setRotationPoint(5.0F, 5.0F, -8.0F);
		body.addChild(larm);
		larm.setTextureOffset(0, 75).addBox(0.0F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		larm.setModelRendererName("larm");
		this.registerModelRenderer(larm);

		larm2 = new AnimatedModelRenderer(this);
		larm2.setRotationPoint(1.0F, 1.0F, 1.0F);
		larm.addChild(larm2);
		setRotationAngle(larm2, 0.3491F, 0.0F, 0.0F);
		larm2.setTextureOffset(70, 72).addBox(-0.5F, -3.0F, -4.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
		larm2.setModelRendererName("larm2");
		this.registerModelRenderer(larm2);

		lhand = new AnimatedModelRenderer(this);
		lhand.setRotationPoint(0.5F, -2.0F, -4.0F);
		larm2.addChild(lhand);
		setRotationAngle(lhand, 0.3491F, 0.0F, 0.0F);
		lhand.setTextureOffset(75, 57).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
		lhand.setModelRendererName("lhand");
		this.registerModelRenderer(lhand);

		rarm = new AnimatedModelRenderer(this);
		rarm.setRotationPoint(-8.0F, 5.0F, -8.0F);
		body.addChild(rarm);
		rarm.setTextureOffset(18, 57).addBox(0.0F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		rarm.setModelRendererName("rarm");
		this.registerModelRenderer(rarm);

		rarm2 = new AnimatedModelRenderer(this);
		rarm2.setRotationPoint(1.0F, 1.0F, 1.0F);
		rarm.addChild(rarm2);
		setRotationAngle(rarm2, 0.3491F, 0.0F, 0.0F);
		rarm2.setTextureOffset(19, 70).addBox(-0.5F, -3.0F, -4.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
		rarm2.setModelRendererName("rarm2");
		this.registerModelRenderer(rarm2);

		rhand = new AnimatedModelRenderer(this);
		rhand.setRotationPoint(0.5F, -2.0F, -4.0F);
		rarm2.addChild(rhand);
		setRotationAngle(rhand, 0.3491F, 0.0F, 0.0F);
		rhand.setTextureOffset(75, 31).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
		rhand.setModelRendererName("rhand");
		this.registerModelRenderer(rhand);

		neck = new AnimatedModelRenderer(this);
		neck.setRotationPoint(0.0F, 3.0F, -10.0F);
		body.addChild(neck);
		setRotationAngle(neck, 0.5236F, 0.0F, 0.0F);
		neck.setTextureOffset(0, 57).addBox(-3.0F, -10.0F, -1.0F, 6.0F, 12.0F, 6.0F, 0.0F, false);
		neck.setTextureOffset(60, 67).addBox(0.0F, -14.0F, 4.0F, 0.0F, 12.0F, 5.0F, 0.0F, false);
		neck.setModelRendererName("neck");
		this.registerModelRenderer(neck);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, -10.0F, 0.0F);
		neck.addChild(head);
		setRotationAngle(head, -0.5236F, 0.0F, 0.0F);
		head.setTextureOffset(30, 30).addBox(-3.5F, -4.0F, -4.0F, 7.0F, 6.0F, 8.0F, 0.0F, false);
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		ujaw = new AnimatedModelRenderer(this);
		ujaw.setRotationPoint(0.0F, -2.0F, -4.0F);
		head.addChild(ujaw);
		ujaw.setTextureOffset(60, 48).addBox(-2.5F, -1.0F, -6.0F, 5.0F, 3.0F, 6.0F, 0.0F, false);
		ujaw.setModelRendererName("ujaw");
		this.registerModelRenderer(ujaw);

		ljaw = new AnimatedModelRenderer(this);
		ljaw.setRotationPoint(0.0F, -1.0F, -4.0F);
		head.addChild(ljaw);
		ljaw.setTextureOffset(64, 0).addBox(-2.0F, 1.0F, -6.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);
		ljaw.setModelRendererName("ljaw");
		this.registerModelRenderer(ljaw);

		tail = new AnimatedModelRenderer(this);
		tail.setRotationPoint(0.0F, 2.0F, 10.0F);
		body.addChild(tail);
		setRotationAngle(tail, -0.1745F, 0.0F, 0.0F);
		tail.setTextureOffset(40, 0).addBox(-3.5F, -2.5F, -1.0F, 7.0F, 7.0F, 10.0F, 0.0F, false);
		tail.setModelRendererName("tail");
		this.registerModelRenderer(tail);

		tail2 = new AnimatedModelRenderer(this);
		tail2.setRotationPoint(0.0F, 0.0F, 9.0F);
		tail.addChild(tail2);
		setRotationAngle(tail2, 0.0873F, 0.0F, 0.0F);
		tail2.setTextureOffset(52, 21).addBox(-2.5F, -1.5F, -1.0F, 5.0F, 5.0F, 8.0F, 0.0F, false);
		tail2.setModelRendererName("tail2");
		this.registerModelRenderer(tail2);

		tail3 = new AnimatedModelRenderer(this);
		tail3.setRotationPoint(0.0F, 0.0F, 7.0F);
		tail2.addChild(tail3);
		setRotationAngle(tail3, 0.0873F, 0.0F, 0.0F);
		tail3.setTextureOffset(60, 60).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 3.0F, 9.0F, 0.0F, false);
		tail3.setModelRendererName("tail3");
		this.registerModelRenderer(tail3);

		tailfin = new AnimatedModelRenderer(this);
		tailfin.setRotationPoint(0.0F, -1.0F, 3.5F);
		tail3.addChild(tailfin);
		setRotationAngle(tailfin, -0.0873F, 0.0F, 0.0F);
		tailfin.setTextureOffset(0, 6).addBox(0.0F, -3.0F, -4.0F, 0.0F, 4.0F, 8.0F, 0.0F, false);
		tailfin.setModelRendererName("tailfin");
		this.registerModelRenderer(tailfin);

		lwing = new AnimatedModelRenderer(this);
		lwing.setRotationPoint(5.0F, 0.0F, -2.0F);
		body.addChild(lwing);
		setRotationAngle(lwing, -0.6981F, 0.0F, 0.0F);
		lwing.setTextureOffset(0, 14).addBox(0.5F, -28.0F, -8.0F, 0.0F, 28.0F, 15.0F, 0.0F, false);
		lwing.setModelRendererName("lwing");
		this.registerModelRenderer(lwing);

		rwing = new AnimatedModelRenderer(this);
		rwing.setRotationPoint(5.0F, 0.0F, -2.0F);
		body.addChild(rwing);
		setRotationAngle(rwing, -0.6981F, 0.0F, 0.0F);
		rwing.setTextureOffset(30, 30).addBox(-10.5F, -28.0F, -8.0F, 0.0F, 28.0F, 15.0F, 0.0F, false);
		rwing.setModelRendererName("rwing");
		this.registerModelRenderer(rwing);

		this.rootBones.add(body);
	}


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation(DragonvaleMain.MODID, "animations/adultfiredragon.json");
    }


	@Override
	public void setRotationAngles(FireDragonEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}