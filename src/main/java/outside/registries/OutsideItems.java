package outside.registries;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import outside.item.ItemRock;

public class OutsideItems implements ModInitializer {

    public OutsideItems() {
    }

	public static final ItemRock ROCK = new ItemRock(new QuiltItemSettings());

	@Override
	public void onInitialize(ModContainer mod) {
    }
}
