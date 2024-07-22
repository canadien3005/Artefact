package ca.canadien3005.artefact.item;

import ca.canadien3005.artefact.Artefact;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final CreativeModeTab ARTEFACT_TAB = new CreativeModeTab(new TranslatableComponent(Artefact.MOD_ID + ".artefact_tab").getString()) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MULTI_TOOLS.get());
        }
    };
}
