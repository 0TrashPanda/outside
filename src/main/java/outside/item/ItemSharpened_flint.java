package outside.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
			Modifiers modefiers = Modifiers.values()[rand.nextInt(Modifiers.values().length)];
			itemStack.getOrCreateNbt().putLong("modifier", modefiers.ordinal());
        	user.sendMessage(Text.of(String.format("Stored modifier: %s", modefiers.name)), true);
		} else {
			long x = itemStack.getOrCreateNbt().getLong("modifier");
			user.sendMessage(Text.of(String.format("Loaded modifier: %s", Modifiers.values()[(int) x].name)), true);
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
}
