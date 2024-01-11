package outside.block.entity;

import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeManager.CachedCheck;
import net.minecraft.util.Clearable;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Context;
import org.jetbrains.annotations.Nullable;
import outside.registries.OutsideBlockEntityType;

public class BlockEntityRettingBarrel extends BlockEntity implements Clearable {
   private static final int BURN_DECAY_RATE = 20;
   private static final int SLOTS = 64;
   private final DefaultedList<ItemStack> itemsBeingRetted;
   private final int[] rettingTimes;
   private final int[] rettingTotalTimes;
   private final CachedCheck<Inventory, CampfireCookingRecipe> recipeCache;

   public BlockEntityRettingBarrel(BlockPos pos, BlockState state) {
      super(OutsideBlockEntityType.RETTING_BARREL_BLOCK_ENTITY, pos, state);
      this.itemsBeingRetted = DefaultedList.ofSize(SLOTS, ItemStack.EMPTY);
      this.rettingTimes = new int[SLOTS];
      this.rettingTotalTimes = new int[SLOTS];
      this.recipeCache = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
   }

   public static void litServerTick(World world, BlockPos pos, BlockState state, BlockEntityRettingBarrel rettingBarrel) {
      boolean bl = false;

      for(int i = 0; i < rettingBarrel.itemsBeingRetted.size(); ++i) {
         ItemStack itemStack = (ItemStack)rettingBarrel.itemsBeingRetted.get(i);
         if (!itemStack.isEmpty()) {
            bl = true;
            int rettingTime = rettingBarrel.rettingTimes[i]++;
            if (rettingTime >= rettingBarrel.rettingTotalTimes[i]) {
               Inventory inventory = new SimpleInventory(new ItemStack[]{itemStack});
               ItemStack itemStack2 = (ItemStack)rettingBarrel.recipeCache.getRecipeFor(inventory, world).map((recipe) -> {
                  return recipe.craft(inventory, world.getRegistryManager());
               }).orElse(itemStack);
               if (itemStack2.isEnabled(world.getEnabledFlags())) {
                  ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemStack2);
                  rettingBarrel.itemsBeingRetted.set(i, ItemStack.EMPTY);
                  world.updateListeners(pos, state, state, 3);
                  world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Context.create(state));
               }
            }
         }
      }

      if (bl) {
         markDirty(world, pos, state);
      }

   }

   public static void unlitServerTick(World world, BlockPos pos, BlockState state, BlockEntityRettingBarrel rettingBarrel) {
      boolean bl = false;

      for(int i = 0; i < rettingBarrel.itemsBeingRetted.size(); ++i) {
         if (rettingBarrel.rettingTimes[i] > 0) {
            bl = true;
            rettingBarrel.rettingTimes[i] = MathHelper.clamp(rettingBarrel.rettingTimes[i] - 2, 0, rettingBarrel.rettingTotalTimes[i]);
         }
      }

      if (bl) {
         markDirty(world, pos, state);
      }

}

   public DefaultedList<ItemStack> getItemsBeingCooked() {
      return this.itemsBeingRetted;
   }

   public void readNbt(NbtCompound nbt) {
      super.readNbt(nbt);
      this.itemsBeingRetted.clear();
      Inventories.readNbt(nbt, this.itemsBeingRetted);
      int[] is;
      if (nbt.contains("CookingTimes", 11)) {
         is = nbt.getIntArray("CookingTimes");
         System.arraycopy(is, 0, this.rettingTimes, 0, Math.min(this.rettingTotalTimes.length, is.length));
      }

      if (nbt.contains("CookingTotalTimes", 11)) {
         is = nbt.getIntArray("CookingTotalTimes");
         System.arraycopy(is, 0, this.rettingTotalTimes, 0, Math.min(this.rettingTotalTimes.length, is.length));
      }

   }

   protected void writeNbt(NbtCompound nbt) {
      super.writeNbt(nbt);
      Inventories.writeNbt(nbt, this.itemsBeingRetted, true);
      nbt.putIntArray("CookingTimes", this.rettingTimes);
      nbt.putIntArray("CookingTotalTimes", this.rettingTotalTimes);
   }

   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return BlockEntityUpdateS2CPacket.of(this);
   }

   public NbtCompound toSyncedNbt() {
      NbtCompound nbtCompound = new NbtCompound();
      Inventories.writeNbt(nbtCompound, this.itemsBeingRetted, true);
      return nbtCompound;
   }

   public Optional<CampfireCookingRecipe> getRecipeFor(ItemStack item) {
      return this.itemsBeingRetted.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.recipeCache.getRecipeFor(new SimpleInventory(new ItemStack[]{item}), this.world);
   }

	public boolean addItem(@Nullable Entity user, ItemStack item, int cookingTotalTime) {
		Boolean removedItem = false;
		for(int i = 0; i < this.itemsBeingRetted.size(); ++i) {
			ItemStack itemStack = (ItemStack)this.itemsBeingRetted.get(i);
			if (itemStack.isEmpty()) {
				this.rettingTotalTimes[i] = cookingTotalTime;
				this.rettingTimes[i] = 0;
				this.itemsBeingRetted.set(i, item.split(1));
				this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), Context.create(user, this.getCachedState()));
				this.updateListeners();
				removedItem = true;
			}
		}
		return removedItem;
	}

   private void updateListeners() {
      this.markDirty();
      this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
   }

   public void clear() {
      this.itemsBeingRetted.clear();
   }

   public void spawnItemsBeingCooked() {
      if (this.world != null) {
         this.updateListeners();
      }

   }
}
