package ca.canadien3005.artefact.datagen;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Artefact.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        multiToolsItem(ModItems.MULTI_TOOLS.get());
    }

    private ItemModelBuilder multiToolsItem(Item item) {
        ItemModelBuilder.OverrideBuilder itemModelBuilder = withExistingParent(item.getRegistryName().getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(Artefact.MOD_ID,"item/" + item.getRegistryName().getPath()))
                .override();

        float value = 0f;
        for (Item item1 : ModItems.multiToolItems) {
            itemModelBuilder.predicate(new ResourceLocation(Artefact.MOD_ID, ".item"), value).model((ModelFile) getItemModel(item1));
        }

        return itemModelBuilder.end();
    }

    private BakedModel getItemModel(Item item) {
        // Get where the icon is
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item);

    }
}
