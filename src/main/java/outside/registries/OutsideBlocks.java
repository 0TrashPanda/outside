package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import outside.block.BlockFlax;

public class OutsideBlocks implements ModInitializer {

	public static final BlockFlax FLAX = new BlockFlax(QuiltBlockSettings.create());

	public OutsideBlocks() {
    }

	public static void init(ModContainer mod) {
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "flax"),
				FLAX);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addItem(FLAX.asItem());
		});
	}

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
