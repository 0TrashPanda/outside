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

public class ItemRock extends Item {

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
            playerEntity.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0F, 1.0F);
            itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}