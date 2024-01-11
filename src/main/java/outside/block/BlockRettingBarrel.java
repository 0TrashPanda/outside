package outside.block;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import outside.block.entity.BlockEntityRettingBarrel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import outside.item.ItemDirtyFlax;
import outside.registries.OutsideItems;
import outside.registries.OutsideBlockEntityType;


import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;


public class BlockRettingBarrel extends BlockWithEntity implements Waterloggable {
	private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	protected static final VoxelShape OUTLINE_SHAPE;
	public static final BooleanProperty WATERLOGGED;
	public static final DirectionProperty FACING;


	public BlockRettingBarrel(boolean emitsParticles, int fireDamage, QuiltBlockSettings settings) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()))).with(WATERLOGGED, false)).with(FACING, Direction.NORTH));
	}

   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof BlockEntityRettingBarrel) {
         BlockEntityRettingBarrel rettingBarrelBlockEntity = (BlockEntityRettingBarrel)blockEntity;
         ItemStack itemStack = player.getStackInHand(hand);
         Optional<CampfireCookingRecipe> optional = rettingBarrelBlockEntity.getRecipeFor(itemStack);
         if (optional.isPresent()) {
            if (!world.isClient && rettingBarrelBlockEntity.addItem(player, player.getAbilities().creativeMode ? itemStack.copy() : itemStack, ((CampfireCookingRecipe)optional.get()).getCookTime())) {
               player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
               return ActionResult.SUCCESS;
            }

            return ActionResult.CONSUME;
         }
      }

      return ActionResult.PASS;
   }

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if  (entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity)entity;
			ItemStack itemStack = itemEntity.getStack();
			if (itemStack.getItem() instanceof ItemDirtyFlax) {
				if (itemStack.getCount() > 1) {
					itemStack.decrement(1);
					itemEntity.setStack(itemStack);
				} else {
					itemEntity.remove(net.minecraft.entity.Entity.RemovalReason.DISCARDED);
				}
				ItemEntity flaxEntity = new ItemEntity(world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), new ItemStack(OutsideItems.FLAX));
				world.spawnEntity(flaxEntity);
			}
		}
		super.onEntityCollision(state, world, pos, entity);
	}

	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BlockEntityRettingBarrel) {
				ItemScatterer.spawn(world, pos, ((BlockEntityRettingBarrel)blockEntity).getItemsBeingCooked());
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      WorldAccess worldAccess = ctx.getWorld();
      BlockPos blockPos = ctx.getBlockPos();
      boolean bl = worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER;
      return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, bl)))).with(FACING, ctx.getPlayerFacing());
   }

   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.get(WATERLOGGED)) {
         world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return direction == Direction.DOWN ? (BlockState)state : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
   }


	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPE;
	}

	public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		return RAYCAST_SHAPE;
	}

   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }


   public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
      if (!(Boolean)state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
         world.setBlockState(pos, (BlockState)((BlockState)state.with(WATERLOGGED, true)), 3);
         world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
         return true;
      } else {
         return false;
      }
   }

   public FluidState getFluidState(BlockState state) {
      return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
   }

   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED, FACING});
   }

   public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new BlockEntityRettingBarrel(pos, state);
   }

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (!world.isClient) {
			return (boolean)state.get(WATERLOGGED) ? checkType(type, OutsideBlockEntityType.RETTING_BARREL_BLOCK_ENTITY, BlockEntityRettingBarrel::litServerTick) : null;
		}
		return null;
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	static {
		WATERLOGGED = Properties.WATERLOGGED;
		FACING = Properties.HORIZONTAL_FACING;
		OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), RAYCAST_SHAPE, BooleanBiFunction.ONLY_FIRST);
	}
}
