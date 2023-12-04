package outside;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import outside.common.registries.OutsideItems;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Outside implements ModInitializer {

    private static final ItemGroup OUTSIDE_GROUP = FabricItemGroup.builder()
    .icon(() -> new ItemStack(OutsideItems.ROCK))
    .displayName(Text.translatable("itemGroup.outside"))
        .entries((context, entries) -> {
        entries.add(OutsideItems.ROCK);
    })
    .build();

    @Override
    public void onInitialize() {
        new OutsideItems();
        Registry.register(Registries.ITEM_GROUP, new Identifier( "outside"), OUTSIDE_GROUP);
    }
}