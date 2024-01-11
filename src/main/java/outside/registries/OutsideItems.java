package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import outside.item.ItemSharpenedFlint;
import outside.item.ItemDirtyFlax;
import outside.item.ItemFlintSpear;
import outside.item.ItemHammerstone;

public class OutsideItems implements ModInitializer {

    public OutsideItems() {
    }

	public static final ItemHammerstone ROCK = new ItemHammerstone(new QuiltItemSettings(), 64);
	public static final ItemSharpenedFlint SHARPEND_FLINT = new ItemSharpenedFlint(new QuiltItemSettings());
	public static final ItemFlintSpear FLINT_SPEAR = new ItemFlintSpear(new QuiltItemSettings());
	public static final Item FLAX = new Item(new Item.Settings());
	public static final Item FLAX_SEED = new AliasedBlockItem(OutsideBlocks.FLAX, new Item.Settings());
	public static final Item RETTED_FLAX = new Item(new Item.Settings());
	public static final ItemDirtyFlax DIRTY_FLAX = new ItemDirtyFlax(new Item.Settings());


	public static void init(ModContainer mod) {
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "rock"), ROCK);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "sharpend_flint"), SHARPEND_FLINT);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "flint_spear"), FLINT_SPEAR);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "flax"), FLAX);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "flax_seeds"), FLAX_SEED);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "retted_flax"), RETTED_FLAX);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "dirty_flax"), DIRTY_FLAX);
		Registry.register(Registries.ITEM, new Identifier(mod.metadata().id(), "retting_barrel"), new BlockItem(OutsideBlocks.RETTING_BARREL, new QuiltItemSettings()));



		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
				entries.addItem(ROCK);
				entries.addItem(SHARPEND_FLINT);
				entries.addItem(FLINT_SPEAR);
				entries.addItem(FLAX);
				entries.addItem(FLAX_SEED);
				entries.addItem(RETTED_FLAX);
				entries.addItem(DIRTY_FLAX);
				entries.addItem(OutsideBlocks.RETTING_BARREL);
		});
	}

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
