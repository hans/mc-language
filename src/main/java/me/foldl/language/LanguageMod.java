package me.foldl.language;

import me.foldl.language.mixin.ServerWorldMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.level.ServerWorldProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LanguageMod implements ModInitializer {
	public static final Block ENTRY_BLOCK = new EntryBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
	public static BlockEntityType<EntryBlockEntity> ENTRY_BLOCK_ENTITY;
	public static Item ENTRY_BLOCK_ITEM;

	private List<BlockEntity> placedBlocks = new ArrayList<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ENTRY_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("language", "entry_block"),
				BlockEntityType.Builder.create(EntryBlockEntity::new, ENTRY_BLOCK).build(null));

		ENTRY_BLOCK_ITEM = Registry.register(Registry.ITEM, new Identifier("language", "entry_block"),
				new BlockItem(ENTRY_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

		final int[] i = new int[] {0};
		final int switchInterval = 500;
		ServerTickEvents.END_WORLD_TICK.register((ServerWorld world) -> {
			if (i[0] % switchInterval == 0) {
				int clearDuration, rainDuration;
				boolean isRaining;
				System.out.println(i[0] / switchInterval);
				if ((i[0] / switchInterval) % 2 == 0) {
					clearDuration = 24000; rainDuration = 0;
					isRaining = false;
				} else {
					clearDuration = 0; rainDuration = 24000;
					isRaining = true;
				}
				System.out.println("starting RAIN");
				System.out.println(isRaining);
				world.setWeather(clearDuration, rainDuration, isRaining, isRaining);
			}

			i[0]++;

			// Health effects of weather
			world.getPlayers().forEach((player) -> {
				// Is player sheltered?
				boolean sheltered = false;
				Vec3d abovePlayer = player.getPos().add(0f, 10f, 0f);
				BlockHitResult raycast = world.raycast(
						new RaycastContext(abovePlayer, player.getPos(), RaycastContext.ShapeType.COLLIDER,
										   RaycastContext.FluidHandling.NONE, player));
				sheltered = !(raycast == null || raycast.getType() == BlockHitResult.Type.MISS);

				ServerWorldProperties wp = ((ServerWorldMixin) world).getWorldProperties();

				if (wp.isRaining() && !sheltered) {
					System.out.println("Damaging");
					System.out.println(player.damage(DamageSource.GENERIC, 2.0f));
				}
			});
		});

		final int[] j = new int[] {0};
		ClientTickEvents.END_WORLD_TICK.register((ClientWorld world) -> {
			// Check for block under crosshairs.
			MinecraftClient client = MinecraftClient.getInstance();
			HitResult hit = client.crosshairTarget;
			if (hit.getType() == HitResult.Type.BLOCK) {
				BlockPos pos = ((BlockHitResult) hit).getBlockPos();
				BlockState blockState = world.getBlockState(pos);
				Block block = blockState.getBlock();
				if (block instanceof EntryBlock) {
					EntryBlock entryBlock = (EntryBlock) block;
					EntryBlockEntity entity = (EntryBlockEntity) world.getBlockEntity(pos);
					client.player.sendMessage(new LiteralText("entry: " + entity.getMessage()), false);
				}
			}

			if (j[0] % 250 == 0) {
				// insert new entry block
				System.out.println("add");
				client.player.inventory.insertStack(1, new ItemStack(ENTRY_BLOCK_ITEM, 1));
			}
			j[0]++;
		});

		// DEV below doesn't work -- at time of callback, there is no entity placed at the position. so can't add
		// it to a list!
//		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
//			BlockEntity entity = world.getBlockEntity(hitResult.getBlockPos());
//			System.out.println("placed " + entity);
//			placedBlocks.add(entity);
//
//			return ActionResult.PASS;
//		});
	}
}
