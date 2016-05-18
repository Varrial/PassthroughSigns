package dmillerw.sign;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author dmillerw
 */
@Mod(modid = "passthroughsigns", name = "PassthroughSigns", version = "%MOD_VERSION%", acceptedMinecraftVersions = "[1.9,1.9.4)", dependencies = "required-after:Forge@[12.16.1.1887,)")
public class PassthroughSigns {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        Block block = state.getBlock();
        EnumFacing facing = EnumFacing.getFront(state.getBlock().getMetaFromState(state)).getOpposite();
        if (block == Blocks.WALL_SIGN) {
            ItemStack held = event.getEntityPlayer().getHeldItem(event.getHand());
            if (held != null && held.getItem() instanceof ItemBlock) {
                event.setUseItem(Event.Result.DENY);
            }
            if (!event.getEntityPlayer().isSneaking()) {
                BlockPos posOffset = new BlockPos(event.getPos().getX() + facing.getFrontOffsetX(), event.getPos().getY() + facing.getFrontOffsetY(), event.getPos().getZ() + facing.getFrontOffsetZ());
                Block attached = event.getWorld().getBlockState(posOffset).getBlock();
                if (!attached.isAir(state, event.getWorld(), posOffset)) {
                    attached.onBlockActivated(event.getWorld(), posOffset, event.getWorld().getBlockState(posOffset), event.getEntityPlayer(), event.getHand(), held, event.getFace(), 0, 0, 0);
                }
            }
        }
    }
}