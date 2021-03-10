package me.foldl.language;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
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

		ClientTickEvents.END_WORLD_TICK.register((ClientWorld world) -> {
			System.out.println("end world tick");
		});
	}
}
