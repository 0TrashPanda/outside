package outside.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.stat.Stats;


public class ItemFlintSpear extends TridentItem {

	public ItemFlintSpear(Settings settings) {
		super(settings);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		System.out.println("onStoppedUsing");
		if (user instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity)user;
			int i = this.getMaxUseTime(stack) - remainingUseTicks;
			if (i >= 10) {
				int j = EnchantmentHelper.getRiptide(stack);
				if (j <= 0 || playerEntity.isTouchingWaterOrRain()) {
					if (!world.isClient) {
						stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
						if (j == 0) {
							TridentEntity tridentEntity = new TridentEntity(world, playerEntity, stack);
							tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
							if (playerEntity.getAbilities().creativeMode) {
								tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
							}

							world.spawnEntity(tridentEntity);
							world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
							if (!playerEntity.getAbilities().creativeMode) {
								playerEntity.getInventory().removeOne(stack);
							}
						}
					}

					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
					if (j > 0) {
						float f = playerEntity.getYaw();
						float g = playerEntity.getPitch();
						float h = -MathHelper.sin(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
						float k = -MathHelper.sin(g * (float) (Math.PI / 180.0));
						float l = MathHelper.cos(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
						float m = MathHelper.sqrt(h * h + k * k + l * l);
						float n = 3.0F * ((1.0F + (float)j) / 4.0F);
						h *= n / m;
						k *= n / m;
						l *= n / m;
						playerEntity.addVelocity((double)h, (double)k, (double)l);
						playerEntity.startRiptideAttack(20);
						if (playerEntity.isOnGround()) {
							float y = 1.1999999F;
							playerEntity.move(MovementType.SELF, new Vec3d(0.0, y, 0.0));
						}

						SoundEvent soundEvent;
						if (j >= 3) {
							soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
						} else if (j == 2) {
							soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
						} else {
							soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
						}

						world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
					}
				}
			}
		}
	}

}
