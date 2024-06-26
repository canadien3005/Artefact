package ca.canadien3005.artefact.event;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import ca.canadien3005.artefact.keybinds.KeyBindsInit;
import ca.canadien3005.artefact.screen.ModScreenMultiTools;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Artefact.MOD_ID, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        getIsKeyPressed(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        getIsKeyPressed(mc, event.getButton(), event.getAction());
    }

    private static void getIsKeyPressed(Minecraft mc, int key, int action) {
        assert mc.player != null;
        Item item = mc.player.getItemInHand(InteractionHand.MAIN_HAND).getItem().asItem();
        if(KeyBindsInit.abilityKey.isDown() && item instanceof MultiToolsItem mt){
            mc.setScreen(new ModScreenMultiTools(mt));
        }
    }
}