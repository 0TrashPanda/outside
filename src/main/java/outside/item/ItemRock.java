package outside.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public class ItemRock extends Item {

    Random rand =  new Random();

    public static final float ATTACK_DAMAGE = 1.0f;
    public static final float ATTACK_SPEED = -2.5f;
    public static final int DURABILITY = 32;


    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ItemRock(Settings settings) {
        super(settings.maxDamage(DURABILITY));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ATTACK_DAMAGE, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", ATTACK_SPEED, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == Blocks.STONE) {
            ItemStack itemStack = context.getStack();
            PlayerEntity playerEntity = context.getPlayer();
            playerEntity.playSound(SoundEvents.BLOCK_TUFF_BREAK, 1.0F, 1.0F);
            int damage = getRockDamage(itemStack.getDamage());
            itemStack.damage(damage, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private int getRockDamage(int damage) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += rand.nextInt(3);
        }
        int result = sum + rand.nextInt(damage == 0 ? 1 : damage);
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
