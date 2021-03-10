package me.foldl.language;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.CompoundTag;

/**
 * Inventory item carrying Entry state.
 */
public class EntryBlockItem {//} extends Item {

    private Item parent;
    private CompoundTag tag;

    public EntryBlockItem(Item parent, String message, float sentiment) {
        this.parent = parent;

        this.tag = new CompoundTag();
        tag.putString("message", message);
        tag.putFloat("sentiment", sentiment);
    }

//    @Override
    public Item asItem() {
        return null;
    }
}
