package me.foldl.language;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class EntryBlock extends Block implements BlockEntityProvider {

//    public static final IntProperty STRENGTH = IntProperty.of("strength", 0, 3);

    public EntryBlock(Settings settings) {
        super(settings);
//        setDefaultState(getStateManager().getDefaultState().with(STRENGTH, 3));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EntryBlockEntity();
    }


    //    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        world.setBlockState(pos, state.with(STRENGTH, 3));
//        return ActionResult.SUCCESS;
//    }


}
