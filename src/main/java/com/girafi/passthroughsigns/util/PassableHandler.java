package com.girafi.passthroughsigns.util;

import com.girafi.passthroughsigns.api.IPassable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.girafi.passthroughsigns.util.ConfigurationHandler.*;

public class PassableHandler {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        EntityPlayer player = event.getEntityPlayer();
        Block block = world.getBlockState(pos).getBlock();

        if (block == Blocks.WALL_SIGN && shouldWallSignBePassable || block == Blocks.WALL_BANNER && shouldBannerBePassable ||
                block instanceof IPassable && ((IPassable) block).canBePassed(world, pos, IPassable.EnumPassableType.WALL_BLOCK)) {
            EnumFacing facingOpposite = EnumFacing.getFront(block.getMetaFromState(state)).getOpposite();

            ItemStack heldStack = player.getHeldItem(event.getHand());
            if (heldStack != null && heldStack.getItem() instanceof ItemBlock) {
                event.setUseItem(Event.Result.DENY);
            }

            this.rightClick(world, pos, player, event.getHand(), heldStack, event.getFace(), facingOpposite);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = event.getTarget();

        if (entity instanceof EntityItemFrame && shouldItemFrameBePassable || entity instanceof EntityPainting && shouldPaintingsBePassable ||
                entity instanceof IPassable && ((IPassable) entity).canBePassed(world, pos, IPassable.EnumPassableType.HANGING_ENTITY)) {
            EnumFacing facingOpposite = entity.getHorizontalFacing().getOpposite();

            this.rightClick(world, pos, player, event.getHand(), player.getHeldItem(event.getHand()), event.getFace(), facingOpposite);
        }
    }

    private void rightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, ItemStack heldStack, EnumFacing facing, EnumFacing facingOpposite) {
        if (!player.isSneaking() && hand == EnumHand.MAIN_HAND) {
            BlockPos posOffset = pos.add(facingOpposite.getFrontOffsetX(), facingOpposite.getFrontOffsetY(), facingOpposite.getFrontOffsetZ());
            IBlockState attachedState = world.getBlockState(posOffset);
            if (!attachedState.getBlock().isAir(attachedState, world, pos)) {
                attachedState.getBlock().onBlockActivated(world, posOffset, attachedState, player, hand, heldStack, facing, 0, 0, 0);
            }
        }
    }
}