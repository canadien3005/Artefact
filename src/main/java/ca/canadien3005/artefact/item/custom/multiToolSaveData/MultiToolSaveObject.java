package ca.canadien3005.artefact.item.custom.multiToolSaveData;

import ca.canadien3005.artefact.Artefact;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

public class MultiToolSaveObject {
    private Item current;

    public MultiToolSaveObject(Item item){
        current = item;
    }

    public CompoundTag deserialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(Artefact.MOD_ID+".lastItem", Item.getId(current));
        return nbt;
    }

    public static MultiToolSaveObject serialize(CompoundTag nbt){
        return new MultiToolSaveObject(Item.byId(nbt.getInt(Artefact.MOD_ID+".lastItem")));
    }

    public Item getCurrent() {
        return current;
    }
}
