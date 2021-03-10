package me.foldl.language;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class LanguageMod implements ModInitializer {
	public static final Block ENTRY_BLOCK = new EntryBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
	public static BlockEntityType<EntryBlockEntity> ENTRY_BLOCK_ENTITY;
	public static Item ENTRY_BLOCK_ITEM;

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
		});

		ClientTickEvents.END_WORLD_TICK.register((ClientWorld world) -> {
			// Check for block under crosshairs.
			MinecraftClient client = MinecraftClient.getInstance();
			HitResult hit = client.crosshairTarget;
			if (hit.getType() == HitResult.Type.BLOCK) {
				BlockPos pos = ((BlockHitResult) hit).getBlockPos();
				BlockState blockState = world.getBlockState(pos);
				Block block = blockState.getBlock();
				if (block instanceof EntryBlock) {
					System.out.println("block");
				}
			}
		});
	}
}
