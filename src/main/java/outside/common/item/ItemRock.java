package outside.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

    public ItemRock(Settings settings) {
        super(settings.maxDamage(32));
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
}