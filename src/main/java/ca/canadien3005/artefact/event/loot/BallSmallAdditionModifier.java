package ca.canadien3005.artefact.event.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BallSmallAdditionModifier extends LootModifier {
    private final Item addition;

    protected BallSmallAdditionModifier(LootItemCondition[] conditionsIn, Item addition) {
        super(conditionsIn);
        this.addition = addition;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= 0.1f){
            generatedLoot.add(new ItemStack(addition, context.getRandom().nextInt(1, 3)));
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BallSmallAdditionModifier> {

        @Override
        public BallSmallAdditionModifier read(ResourceLocation name, JsonObject object,
                                              LootItemCondition[] conditionsIn) {
            Item addition = ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(GsonHelper.getAsString(object, "addition")));
            return new BallSmallAdditionModifier(conditionsIn, addition);
        }

        @Override
        public JsonObject write(BallSmallAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", ForgeRegistries.ITEMS.getKey(instance.addition).toString());
            return json;
        }
    }
}
