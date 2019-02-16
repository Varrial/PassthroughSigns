package com.girafi.passthroughsigns.util;

import com.girafi.passthroughsigns.api.IPassable;
import com.girafi.passthroughsigns.api.PassthroughSignsAPI;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static com.girafi.passthroughsigns.util.ConfigurationHandler.GENERAL;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class PassableHandler {

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        EntityPlayer player = event.getEntityPlayer();
        Block block = state.getBlock();

        if (block instanceof BlockWallSign && GENERAL.shouldWallSignBePassable.get() || block instanceof BlockBannerWall && GENERAL.shouldBannerBePassable.get() ||
                block instanceof IPassable && ((IPassable) block).canBePassed(world, pos, IPassable.EnumPassableType.WALL_BLOCK) ||
                PassthroughSignsAPI.BLOCK_PASSABLES.contains(block)) {
            EnumFacing facingOpposite = EnumFacing.NORTH.getOpposite();
            if (state.has(BlockDirectional.FACING)) {
                facingOpposite = state.get(BlockDirectional.FACING).getOpposite();
            } else if (state.has(BlockHorizontal.HORIZONTAL_FACING)) {
                facingOpposite = state.get(BlockHorizontal.HORIZONTAL_FACING).getOpposite();
            }

            ItemStack heldStack = player.getHeldItemMainhand();
            if (heldStack.getItem() instanceof ItemBlock) {
                event.setUseItem(Event.Result.DENY);
            }

            if (block instanceof BlockWallSign) {
                if (Reference.IS_QUARK_LOADED && player.isSneaking() && GENERAL.shiftClickQuark.get()) {
                    rightClick(world, pos, player, event.getHand(), event.getFace(), facingOpposite);
                } else if (!Reference.IS_QUARK_LOADED) {
                    rightClick(world, pos, player, event.getHand(), event.getFace(), facingOpposite);
                }
            } else if (!player.isSneaking()) {
                rightClick(world, pos, player, event.getHand(), event.getFace(), facingOpposite);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = event.getTarget();

        if (entity instanceof EntityItemFrame && GENERAL.shouldItemFrameBePassable.get() || entity instanceof EntityPainting && GENERAL.shouldPaintingsBePassable.get() ||
                entity instanceof IPassable && ((IPassable) entity).canBePassed(world, pos, IPassable.EnumPassableType.HANGING_ENTITY) ||
                PassthroughSignsAPI.ENTITY_PASSABLES.contains(entity.getClass())) {
            EnumFacing facingOpposite = entity.getHorizontalFacing().getOpposite();

            if (!player.isSneaking()) {
                if (entity instanceof EntityItemFrame && GENERAL.turnOffItemRotation.get()) {
                    event.setCanceled(true);
                }
                rightClick(world, pos, player, event.getHand(), event.getFace(), facingOpposite);
            }
        }
    }

    private static void rightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, EnumFacing facingOpposite) {
        if (hand == EnumHand.MAIN_HAND) {
            BlockPos posOffset = pos.add(facingOpposite.getXOffset(), facingOpposite.getYOffset(), facingOpposite.getZOffset());
            IBlockState attachedState = world.getBlockState(posOffset);

            IBlockState stateDown = world.getBlockState(pos.down());
            if (!world.isAirBlock(pos.down()) && attachedState.getBlock().isAir(attachedState, world, pos)) {
                stateDown.onBlockActivated(world, pos.down(), player, hand, EnumFacing.DOWN, 0, 0, 0);
            } else if (!attachedState.getBlock().isAir(attachedState, world, pos)) {
                attachedState.onBlockActivated(world, posOffset, player, hand, facing, 0, 0, 0);
            }
        }
    }
}