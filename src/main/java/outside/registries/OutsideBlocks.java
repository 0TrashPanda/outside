package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import outside.block.BlockFlax;
import outside.block.BlockRettingBarrel;

public class OutsideBlocks implements ModInitializer {

	public static final BlockFlax FLAX = new BlockFlax(AbstractBlock.Settings.create().nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
	public static final BlockRettingBarrel RETTING_BARREL = new BlockRettingBarrel(true, 10, QuiltBlockSettings.create());



	public OutsideBlocks() {
    }

	public static void init(ModContainer mod) {
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "flax"), FLAX);
		Registry.register(Registries.BLOCK, new Identifier(mod.metadata().id(), "retting_barrel"), RETTING_BARREL);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addItem(FLAX.asItem());
			entries.addItem(RETTING_BARREL.asItem());
		});
	}

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
