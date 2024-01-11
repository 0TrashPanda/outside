// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class retting_barrel_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "retting_barrel_converted"), "main");
	private final ModelPart bone;

	public retting_barrel_Converted(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(1, -14).mirror().addBox(-16.0F, -16.0F, 0.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(23, 3).addBox(-16.0F, -16.0F, 15.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 1).mirror().addBox(-1.0F, -16.0F, 1.0F, 1.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(1, -14).addBox(-16.0F, -16.0F, 1.0F, 1.0F, 16.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(12, 2).addBox(-15.0F, -1.0F, 1.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 80, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}