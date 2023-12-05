package outside;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
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
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "rock"),
				OutsideItems.ROCK);

		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "example_block"),
				OutsideBlocks.EXAMPLE_BLOCK);

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "example_block"),
				new BlockItem(OutsideBlocks.EXAMPLE_BLOCK, new QuiltItemSettings()));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addItem(OutsideBlocks.EXAMPLE_BLOCK.asItem());
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addItem(OutsideItems.ROCK);
		});
	}
}
