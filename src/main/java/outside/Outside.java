package outside;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import outside.registries.OutsideItems;

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

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addItem(OutsideItems.ROCK);
		});
	}
}
