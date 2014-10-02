package dmillerw.sign;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * @author dmillerw
 */
@Mod(modid = "PassthroughSigns", name = "PassthroughSigns", version = "%MOD_VERSION%")
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
            Block block = event.world.getBlock(event.x, event.y, event.z);
            ForgeDirection forgeDirection = ForgeDirection.getOrientation(event.world.getBlockMetadata(event.x, event.y, event.z)).getOpposite();
            if (block == Blocks.wall_sign) {
                ItemStack held = event.entityPlayer.getHeldItem();
                if (held != null && held.getItem() instanceof ItemBlock) {
                    event.useItem = Event.Result.DENY;
                }
                if (!event.entityPlayer.isSneaking()) {
                    Block attached = event.world.getBlock(event.x + forgeDirection.offsetX, event.y + forgeDirection.offsetY, event.z + forgeDirection.offsetZ);
                    if (attached != null && !attached.isAir(event.world, event.x + forgeDirection.offsetX, event.y + forgeDirection.offsetY, event.z + forgeDirection.offsetZ)) {
                        attached.onBlockActivated(event.world, event.x + forgeDirection.offsetX, event.y + forgeDirection.offsetY, event.z + forgeDirection.offsetZ, event.entityPlayer, event.face, 0, 0, 0);
                    }
                }
            }
        }
    }
}
