package ca.canadien3005.artefact;

import ca.canadien3005.artefact.config.ArtefactClientConfigs;
import ca.canadien3005.artefact.config.ArtefactCommonConfigs;
import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import ca.canadien3005.artefact.keybinds.KeyBindsInit;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;

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
        eventBus.addListener(this::doClientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ArtefactCommonConfigs.SPEC, MOD_ID + "-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ArtefactClientConfigs.SPEC, MOD_ID + "-client.toml");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        MultiToolsItem.items = new ArrayList<>();
        MultiToolsItem.items.add(ModItems.MULTI_TOOL_AXE.get());
        MultiToolsItem.items.add(ModItems.MULTI_TOOL_PICKAXE.get());
        MultiToolsItem.items.add(ModItems.MULTI_TOOL_SHOVEL.get());
        MultiToolsItem.items.add(ModItems.MULTI_TOOL_HOE.get());
    }

    private void doClientSetup(final FMLClientSetupEvent event) {
        // This is for multi texture items
        event.enqueueWork(() -> {
            // This allows multi texture for the multi_tools
            ItemProperties.register(ModItems.MULTI_TOOLS.get(), new ResourceLocation(Artefact.MOD_ID, "item"), (stack, world, entity, seed) -> {
                CompoundTag tags = stack.getTag();
                if(tags == null) return -1f;
                if (MultiToolsItem.items == null) return -1f;

                int value = tags.getInt("artefact.current_tool");
                float increment = 1f/ MultiToolsItem.items.size();
                int indice = 0;

                for(Item item : MultiToolsItem.items) {
                    if (Item.byId(value) == item) {
                        return indice*increment;
                    };
                    indice++;
                }

                return -1.0f;
            });
        });
    }
}
