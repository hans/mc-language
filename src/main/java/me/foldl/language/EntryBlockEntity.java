package me.foldl.language;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class EntryBlockEntity extends BlockEntity {
    private String message;
    private float sentiment;
    private float decay = 1.0f;

    public EntryBlockEntity() {
        super(LanguageMod.ENTRY_BLOCK_ENTITY);

        // HACK: For now, make these randomly generated from here
        message = "sup";
        sentiment = 3.0f;
    }

    public String getMessage() {
        return message;
    }

    public float getSentiment() {
        return sentiment;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putString("message", message);
        tag.putFloat("sentiment", sentiment);

        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        message = tag.getString("message");
        sentiment = tag.getFloat("sentiment");
    }
}
