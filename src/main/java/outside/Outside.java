package outside;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import outside.registries.OutsideItems;
import outside.registries.OutsideBlocks;

public class Outside implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	// public static final Logger LOGGER = LoggerFactory.getLogger("Outside");

	@Override
	public void onInitialize(ModContainer mod) {
		// LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		new OutsideItems();
		OutsideItems.init(mod);

		new OutsideBlocks();
		OutsideBlocks.init(mod);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addItem(OutsideBlocks.EXAMPLE_BLOCK.asItem());
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addItem(OutsideItems.ROCK);
		});
	}
}
