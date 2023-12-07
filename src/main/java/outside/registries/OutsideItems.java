package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import outside.item.ItemSharpened_flint;
import outside.item.ItemHammerstone;

public class OutsideItems implements ModInitializer {

    public OutsideItems() {
    }

	public static final ItemHammerstone ROCK = new ItemHammerstone(new QuiltItemSettings(), 64);
	public static final ItemSharpened_flint SHARPEND_FLINT = new ItemSharpened_flint(new QuiltItemSettings());

	public static void init(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "rock"),
				OutsideItems.ROCK);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addItem(OutsideItems.ROCK);
		});

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "sharpend_flint"),
				OutsideItems.SHARPEND_FLINT);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
					entries.addItem(OutsideItems.SHARPEND_FLINT);
		});

		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "example_block"),
				new BlockItem(OutsideBlocks.EXAMPLE_BLOCK, new QuiltItemSettings()));
	}

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
