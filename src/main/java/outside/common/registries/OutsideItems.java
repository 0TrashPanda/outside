package outside.common.registries;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import outside.common.item.ItemRock;

public class OutsideItems implements ModInitializer {

    public OutsideItems() {
    }

    public static final ItemRock ROCK = Registry.register(Registries.ITEM, new Identifier("outside", "rock"), new ItemRock(new FabricItemSettings()));

    @Override
    public void onInitialize() {
    }
}
