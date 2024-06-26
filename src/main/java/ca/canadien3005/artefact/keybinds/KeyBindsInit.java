package ca.canadien3005.artefact.keybinds;

import ca.canadien3005.artefact.Artefact;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class KeyBindsInit {
    public static KeyMapping abilityKey;

    public static void register(IEventBus eventBus) {
        abilityKey = create("ability_key", 72);

        ClientRegistry.registerKeyBinding(abilityKey);
    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + Artefact.MOD_ID + "." + name, key, "key.category." + Artefact.MOD_ID);
    }

}
