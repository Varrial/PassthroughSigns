package dmillerw.sign;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author dmillerw
 */
@Mod(modid = "PassthroughSigns", name = "PassthroughSigns", version = "%MOD_VERSION%", acceptedMinecraftVersions = "[1.8,1.9)", dependencies = "required-after:Forge@[11.14.4,)")
public class PassthroughSigns {

    @Mod.Instance("PassthroughSigns")
    public static PassthroughSigns instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            IBlockState state = event.world.getBlockState(new BlockPos(event.pos.getX(), event.pos.getY(), event.pos.getZ()));
            Block block = state.getBlock();
            EnumFacing facing = EnumFacing.getFront(state.getBlock().getMetaFromState(state)).getOpposite();
            if (block == Blocks.wall_sign) {
                ItemStack held = event.entityPlayer.getHeldItem();
                if (held != null && held.getItem() instanceof ItemBlock) {
                    event.useItem = Event.Result.DENY;
                }
                if (!event.entityPlayer.isSneaking()) {
                    BlockPos pos = new BlockPos(event.pos.getX() + facing.getFrontOffsetX(), event.pos.getY() + facing.getFrontOffsetY(), event.pos.getZ() + facing.getFrontOffsetZ());
                    Block attached = event.world.getBlockState(pos).getBlock();
                    if (attached != null && !attached.isAir(event.world, pos)) {
                        attached.onBlockActivated(event.world, pos, event.world.getBlockState(pos), event.entityPlayer, event.face, 0, 0, 0);
                    }
                }
            }
        }
    }
}