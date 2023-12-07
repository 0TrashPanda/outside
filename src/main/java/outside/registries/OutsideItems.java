package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import outside.item.ItemRock;

public class OutsideItems implements ModInitializer {

    public OutsideItems() {
    }

	public static final ItemRock ROCK = new ItemRock(new QuiltItemSettings());
	public static final ItemRock SHARPEND_FLINT = new ItemRock(new QuiltItemSettings());

	public static void init(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "rock"),
				OutsideItems.ROCK);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "sharpend_flint"),
				OutsideItems.SHARPEND_FLINT);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "example_block"),
				new BlockItem(OutsideBlocks.EXAMPLE_BLOCK, new QuiltItemSettings()));
	}

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
