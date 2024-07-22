package ca.canadien3005.artefact.event;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.event.loot.BallDropAdditionModifier;
import ca.canadien3005.artefact.event.loot.BallAdditionModifier;
import ca.canadien3005.artefact.event.loot.BallSmallAdditionModifier;
import ca.canadien3005.artefact.event.loot.BallSmallDropAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Artefact.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                           event) {
        event.getRegistry().registerAll(
                new BallAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"water_ball_in_shipwreck")),
                new BallAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"water_ball_in_big_city")),
                new BallSmallAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"water_ball_in_small_city")),
                new BallSmallDropAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"water_ball_from_drowned")),


                new BallSmallAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"fire_ball_in_ruined_portal")),
                new BallSmallDropAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"fire_ball_from_blaze")),

                new BallAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"wither_ball_in_nether_bridge")),
                new BallSmallDropAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID, "wither_ball_from_wither_skeleton")),
                new BallDropAdditionModifier.Serializer().setRegistryName
                        (new ResourceLocation(Artefact.MOD_ID,"wither_ball_from_wither"))
        );
    }
}
