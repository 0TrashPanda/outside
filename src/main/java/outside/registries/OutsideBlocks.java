package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.minecraft.block.Block;

public class OutsideBlocks implements ModInitializer {

    public OutsideBlocks() {
    }

	public static final Block EXAMPLE_BLOCK = new Block(QuiltBlockSettings.create());

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
