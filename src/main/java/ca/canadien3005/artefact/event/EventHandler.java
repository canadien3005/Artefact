package ca.canadien3005.artefact.event;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Artefact.MOD_ID, value = Dist.CLIENT)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerSpawn(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getPlayer();
        NonNullList<ItemStack> itemStacks = player.getInventory().items;

        for (ItemStack itemStack: itemStacks){
            if (itemStack.getItem() == ModItems.MULTI_TOOLS.get()){
                MultiToolsItem mt = (MultiToolsItem) itemStack.getItem();
                mt.init();
            }
        }
    }
}
