package com.girafi.passthroughsigns.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPassable {

    public boolean canBePassed(World world, BlockPos pos, EnumPassableType type);

    public static enum EnumPassableType {
        WALL_SIGN, //TODO Rename to HANGING
        /*Item frames, banners etc. */
        HANGING_ENTITY;
    }
}