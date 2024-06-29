package ca.canadien3005.artefact.datagen;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Artefact.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        addOverride(multiToolsItem(ModItems.MULTI_TOOLS.get()));
    }

    private ItemModelBuilder multiToolsItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(Artefact.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder addOverride(ItemModelBuilder itemModelBuilder){
        ItemModelBuilder itemBuilder = itemModelBuilder;
        List<Item> list = MultiToolsItem.items;
        float increment = 1f/ list.size();
        int value = 0;

        for (Item item : list){
            itemBuilder = itemBuilder.override().predicate(modLoc("item"), value*increment).model(new ModelFile.UncheckedModelFile(new ResourceLocation(item.getRegistryName().getNamespace(),"item/" + item))).end();
            value++;
        }

        return itemBuilder;
    }

    private List<Item> getItems(List<Integer> items) {
        if (items == null) return null;
        List<Item> itemList = new ArrayList<>();

        for (Integer item : items){
            itemList.add(Item.byId(item));
        }

        return itemList;
    }
}
