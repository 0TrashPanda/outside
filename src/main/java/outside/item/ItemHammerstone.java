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
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import outside.registries.OutsideItems;

import java.util.Random;

public class ItemHammerstone extends Item {

    Random rand =  new Random();

    public static final float ATTACK_DAMAGE = 1.0f;
    public static final float ATTACK_SPEED = -2.5f;

    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ItemHammerstone(Settings settings, int durability) {
        super(settings.maxDamage(durability));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ATTACK_DAMAGE, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ATTACK_SPEED, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack hammerstoneStack = user.getStackInHand(hand);
		Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
		ItemStack flintStack = user.getStackInHand(otherHand);
		if (flintStack.getItem() != Items.FLINT) {
			return TypedActionResult.pass(hammerstoneStack);
		}
		user.playSound(SoundEvents.BLOCK_TUFF_BREAK, 1.0F, 1.0F);
		int damage = getRockDamage(hammerstoneStack.getDamage());
		hammerstoneStack.damage(damage, user, p -> p.sendToolBreakStatus(hand));
		ItemStack outputStack = ItemUsage.exchangeStack(flintStack, user, new ItemStack(OutsideItems.SHARPEND_FLINT));
		user.setStackInHand(otherHand, outputStack);
		return TypedActionResult.success(hammerstoneStack, world.isClient());
	}

    private int getRockDamage(int damage) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += rand.nextInt(3);
        }
        int result = sum + rand.nextInt(damage < 2 ? 1 : damage / 2);
        return result;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }
}
