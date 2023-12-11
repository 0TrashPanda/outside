package outside.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import outside.Modifiers;
public class ItemSharpened_flint extends Item {

    Random rand =  new Random();

    public static final float ATTACK_DAMAGE = 2.0f;
    public static final float ATTACK_SPEED = -2.0f;
    public static final int DURABILITY = 32;

    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ItemSharpened_flint(Settings settings) {
        super(settings.maxDamage(DURABILITY));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ATTACK_DAMAGE, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ATTACK_SPEED, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient) {
			return TypedActionResult.pass(user.getStackInHand(hand));
		}
		ItemStack itemStack = user.getStackInHand(hand);
		if (user.isSneaking()) {
			Modifiers modefier = Modifiers.values()[rand.nextInt(Modifiers.values().length)];
			itemStack.getOrCreateNbt().putInt("modifier", modefier.ordinal());
			Style color = Style.EMPTY.withColor(TextColor.fromRgb(modefier.hexColor));
            List<Text> name = Text.of(modefier.name + " " + this.getName().getString()).setStyle(color);
			MinecraftClient client = MinecraftClient.getInstance();
			TooltipContext context = client.options.advancedItemTooltips ? TooltipContext.Default.SHOW_ADVANCED_DETAILS : TooltipContext.Default.HIDE_ADVANCED_DETAILS;
			itemStack.getItem().appendTooltip(itemStack, world, name, context);
			System.out.println(itemStack.getItem().getTooltipData(itemStack) + " false");
			System.out.println(itemStack.getTooltip(user, context) + " true");
			System.out.println(user.getStackInHand(Hand.OFF_HAND).getTooltip(user, context) + " offhand");
		} else {
			int modifier = itemStack.getOrCreateNbt().getInt("modifier");
			user.sendMessage(Text.of(String.format("Loaded modifier: %s", Modifiers.values()[modifier].name)), true);
		}
		return TypedActionResult.success(user.getStackInHand(hand));
		}


    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		int modifier = itemStack.getOrCreateNbt().getInt("modifier");
		itemStack.getOrCreateNbt().putInt("modifier", modifier);
		Style color = Style.EMPTY.withColor(TextColor.fromRgb(Modifiers.values()[modifier].hexColor));
		Text name = Text.of(Modifiers.values()[modifier].name).setStyle(color).get(0);
		tooltip.add(name);
	}
}
