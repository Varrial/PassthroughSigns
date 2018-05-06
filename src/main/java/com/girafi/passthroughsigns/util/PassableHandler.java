package com.girafi.passthroughsigns.util;

import com.girafi.passthroughsigns.api.IPassable;
import com.girafi.passthroughsigns.api.PassthroughSignsAPI;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.girafi.passthroughsigns.util.ConfigurationHandler.*;

@EventBusSubscriber
public class PassableHandler {

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        EntityPlayer player = event.getEntityPlayer();
        Block block = world.getBlockState(pos).getBlock();

        if (block == Blocks.WALL_SIGN && shouldWallSignBePassable || block == Blocks.WALL_BANNER && shouldBannerBePassable ||
                block instanceof IPassable && ((IPassable) block).canBePassed(world, pos, IPassable.EnumPassableType.WALL_BLOCK) ||
                PassthroughSignsAPI.BLOCK_PASSABLES.contains(block)) {
            EnumFacing facingOpposite = EnumFacing.getFront(block.getMetaFromState(state)).getOpposite();

            ItemStack heldStack = player.getHeldItemMainhand();
            if (heldStack.getItem() instanceof ItemBlock) {
                event.setUseItem(Event.Result.DENY);
            }

            if (block == Blocks.WALL_SIGN) {
                if (Reference.IS_QUARK_LOADED && player.isSneaking() && shiftClickQuark) {
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

        if (entity instanceof EntityItemFrame && shouldItemFrameBePassable || entity instanceof EntityPainting && shouldPaintingsBePassable ||
                entity instanceof IPassable && ((IPassable) entity).canBePassed(world, pos, IPassable.EnumPassableType.HANGING_ENTITY) ||
                PassthroughSignsAPI.ENTITY_PASSABLES.contains(entity.getClass())) {
            EnumFacing facingOpposite = entity.getHorizontalFacing().getOpposite();

            if (!player.isSneaking()) {
                if (entity instanceof EntityItemFrame && turnOffItemRotation) {
                    event.setCanceled(true);
                }
                rightClick(world, pos, player, event.getHand(), event.getFace(), facingOpposite);
            }
        }
    }

    private static void rightClick(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, EnumFacing facingOpposite) {
        if (hand == EnumHand.MAIN_HAND) {
            BlockPos posOffset = pos.add(facingOpposite.getFrontOffsetX(), facingOpposite.getFrontOffsetY(), facingOpposite.getFrontOffsetZ());
            IBlockState attachedState = world.getBlockState(posOffset);
            if (!attachedState.getBlock().isAir(attachedState, world, pos)) {
                attachedState.getBlock().onBlockActivated(world, posOffset, attachedState, player, hand, facing, 0, 0, 0);
            }
        }
    }
}