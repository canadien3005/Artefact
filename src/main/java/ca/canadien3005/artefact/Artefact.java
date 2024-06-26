package ca.canadien3005.artefact;

import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import ca.canadien3005.artefact.keybinds.KeyBindsInit;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Artefact.MOD_ID)
public class Artefact {

    public static final String MOD_ID = "artefact";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Artefact() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        KeyBindsInit.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::doModStuff);
        eventBus.addListener(this::doClientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doModStuff(final FMLCommonSetupEvent event){
        List<Item> multiToolList = ModItems.multiToolItems;

        multiToolList.add(ModItems.MULTI_TOOL_AXE.get());
        multiToolList.add(ModItems.MULTI_TOOL_PICKAXE.get());
        multiToolList.add(ModItems.MULTI_TOOL_SHOVEL.get());
        multiToolList.add(ModItems.MULTI_TOOL_HOE.get());
    }

    private void doClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.MULTI_TOOLS.get(), new ResourceLocation(Artefact.MOD_ID, "item"), (stack, world, entity, seed) -> {
                MultiToolsItem mt = (MultiToolsItem)(stack.getItem());
                Item current = mt.getCurrent();
                float increment = 1f/ModItems.multiToolItems.size();
                int indice = 0;

                for(Item item : ModItems.multiToolItems) {
                    if (current == item) {
                        return indice*increment;
                    };
                    indice++;
                }

                return -1.0f;
            });
        });
    }
}
