package ca.canadien3005.artefact.item.custom.multiToolSaveData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.saveddata.SavedData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MultiToolSaveData extends SavedData{

    private final List<MultiToolSaveObject> DATA = new ArrayList<>();

    public MultiToolSaveData(){
    }

    public MultiToolSaveData(CompoundTag nbt){
        load(nbt);
    }

    public void load(CompoundTag nbt){
        CompoundTag saveData = nbt.getCompound("savedata");
        for (int i = 0; saveData.contains("data"+i); i++) {
            DATA.add(MultiToolSaveObject.serialize(saveData.getCompound("data"+i)));
        }
    }

    public List<MultiToolSaveObject> getDATA() {
        return DATA;
    }

    @Override
    @NonNull
    public CompoundTag save(CompoundTag compoundTag) {
        CompoundTag saveData = new CompoundTag();

        for (ListIterator<MultiToolSaveObject> iterator = DATA.listIterator(); iterator.hasNext(); ){
            saveData.put("data"+iterator.nextIndex(), iterator.next().deserialize());
        }

        compoundTag.put("savedata", saveData);

        return compoundTag;
    }

    private static void putData(MultiToolSaveObject object, ServerLevel level){
        MultiToolSaveData data = level.getDataStorage().computeIfAbsent(MultiToolSaveData::new, MultiToolSaveData::new, "test");
        data.DATA.add(object);
        data.setDirty();
    }

    public static void putData(Item current, ServerLevel level){
        MultiToolSaveData.putData(new MultiToolSaveObject(current), level);
    }

    public static MultiToolSaveData getData(ServerLevel level){
        return level.getDataStorage().get(MultiToolSaveData::new, "test");
    }
}
