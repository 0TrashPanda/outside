package outside;

import net.fabricmc.api.ModInitializer;
import outside.common.registries.OutsideItems;

public class Outside implements ModInitializer {

    @Override
    public void onInitialize() {
        new OutsideItems();
    }
}