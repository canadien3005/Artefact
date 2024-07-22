package ca.canadien3005.artefact.item;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import ca.canadien3005.artefact.item.custom.balls.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Artefact.MOD_ID);

    public static final RegistryObject<Item> MULTI_TOOL_AXE = ITEMS.register("multi_tool_axe", () -> new AxeItem(Tiers.NETHERITE, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> MULTI_TOOL_PICKAXE = ITEMS.register("multi_tool_pickaxe", () -> new PickaxeItem(Tiers.NETHERITE, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> MULTI_TOOL_SHOVEL = ITEMS.register("multi_tool_shovel", () -> new ShovelItem(Tiers.NETHERITE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> MULTI_TOOL_HOE = ITEMS.register("multi_tool_hoe", () -> new HoeItem(Tiers.NETHERITE, -4, 0.0F, new Item.Properties()));
    public static final RegistryObject<Item> MULTI_TOOLS = ITEMS.register("multi_tools", () -> new MultiToolsItem(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB).fireResistant().stacksTo(1).defaultDurability(8000)));

    public static final RegistryObject<Item> FIRE_BALL = ITEMS.register("fire_ball", () -> new FireBall(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB)));
    public static final RegistryObject<Item> THUNDER_BALL = ITEMS.register("thunder_ball", () -> new ThunderBall(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB)));
    public static final RegistryObject<Item> WATER_BALL = ITEMS.register("water_ball", () -> new WaterBall(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB)));
    public static final RegistryObject<Item> WITHER_BALL = ITEMS.register("wither_ball", () -> new WitherBall(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB)));
    public static final RegistryObject<Item> INSTANT_DEATH_BALL = ITEMS.register("instant_death_ball", () -> new InstantDeathBall(new Item.Properties().tab(ModCreativeTabs.ARTEFACT_TAB)));

    //public static final RegistryObject<Item> MASK = ITEMS.register("mask", () -> new ArmorItem(ModArmorMaterials.MASK, EquipmentSlot.HEAD, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
