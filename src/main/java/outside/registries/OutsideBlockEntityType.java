package outside.registries;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OutsideBlockEntityType implements ModInitializer {

	public static final BlockEntityType<outside.block.entity.BlockEntityRettingBarrel> RETTING_BARREL_BLOCK_ENTITY = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		new Identifier("outside", "retting_barrel_block_entity"),
		QuiltBlockEntityTypeBuilder.create(outside.block.entity.BlockEntityRettingBarrel::new, outside.registries.OutsideBlocks.RETTING_BARREL).build()
	);

	public OutsideBlockEntityType() {
    }

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
