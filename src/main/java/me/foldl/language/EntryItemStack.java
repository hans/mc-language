package me.foldl.language;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

/**
 * A stack of placeable blocks related to an Entry.
 */
public class EntryItemStack {//} extends ItemStack {

//    private CompoundTag entryTag;
//
//    public EntryItemStack(CompoundTag entryTag) {
//        super(LanguageMod.ENTRY_BLOCK_ITEM, 1);
//        this.entryTag = entryTag;
//    }
//
//    /**
//     * TODO this CANNOT be the right way to carry state from item inventory into a block entity. But I was tired of
//     * trying to find the Right Way and chose this one.
//     * @param context
//     * @return
//     */
//    @Override
//    public ActionResult useOnBlock(ItemUsageContext context) {
//        ActionResult result = super.useOnBlock(context);
//        if (result != ActionResult.SUCCESS)
//            return result;
//
//        System.out.println("here");
//        BlockPos pos = context.getBlockPos();
//        EntryBlockEntity entity = (EntryBlockEntity) context.getWorld().getBlockEntity(pos);
//
//        // SUPER HACK: re-instate x/y/z in block entity tag so that things don't break
//        entryTag.putInt("x", pos.getX());
//        entryTag.putInt("y", pos.getY());
//        entryTag.putInt("z", pos.getZ());
//
//        entity.fromTag(context.getWorld().getBlockState(pos), entryTag);
//
//        return result;
//    }
}
