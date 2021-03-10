package me.foldl.language.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerWorld.class)
public interface ServerWorldMixin {
    @Accessor("worldProperties")
    public ServerWorldProperties getWorldProperties();
}
