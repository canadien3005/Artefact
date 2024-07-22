package ca.canadien3005.artefact.item.custom;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.config.ArtefactCommonConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class Ball extends Item {

    public static final String KEY = Artefact.MOD_ID+".ball";

    public Ball(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide){
            if (canAddPlayerNbt(pPlayer, pUsedHand)){
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
            } else {
                Minecraft.getInstance().gui.setOverlayMessage(new TranslatableComponent("text."+Artefact.MOD_ID+".ballLimit"), false);
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }
        } else {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        System.out.println("finish");
        if (pLivingEntity instanceof Player player){
            addPlayerNbt(player, pStack);
            if(!player.isCreative()){
                player.getUseItem().shrink(1);
            }
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public boolean canUse(Player player){
        return player.getMainHandItem().getItem() instanceof SwordItem || player.getMainHandItem().getItem() instanceof AxeItem;
    }

    public abstract void hit(Level level, Player player, LivingEntity target);

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 30;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    private boolean canAddPlayerNbt(Player player, InteractionHand interactionHand){
        CompoundTag nbt = player.getPersistentData();
        ItemStack itemUsed = player.getItemInHand(interactionHand);
        int maxValue = ArtefactCommonConfigs.MAX_VALUE.get();

        if (!nbt.contains(KEY)) {
            return true;
        } else {
            CompoundTag itemTag = nbt.getCompound(KEY);
            int elements = itemTag.getInt(KEY+".elements");
            boolean find = false;

            for (int i = itemTag.getInt(KEY+".current"); i < elements && !find; i++) {
                CompoundTag tag = itemTag.getCompound(KEY + i);

                if (tag.getInt(KEY + ".id") == Item.getId(itemUsed.getItem())) {
                    return !(tag.getInt(KEY + ".count") + 1 > maxValue);
                }
            }

            return !find;
        }
    }

    private void addPlayerNbt(Player player, ItemStack itemUsed) {
        CompoundTag nbt = player.getPersistentData();
        CompoundTag itemTag = new CompoundTag();
        CompoundTag tag = new CompoundTag();
        String key = KEY;
        int maxValue = ArtefactCommonConfigs.MAX_VALUE.get();
        int value = 1;
        boolean find = false;

        if (!nbt.contains(key)) {
            tag.putInt(key + ".id", Item.getId(itemUsed.getItem()));
            tag.putInt(key + ".count", value);

            itemTag.putInt(key+".elements", 1);
            itemTag.putInt(key+".current", 0);
            itemTag.put(key + "0", tag);
        } else {
            itemTag = nbt.getCompound(key);
            int elements = itemTag.getInt(key+".elements");

            for (int i = itemTag.getInt(key+".current"); i < elements && !find; i++) {
                tag = itemTag.getCompound(key+i);

                if (tag.getInt(key + ".id") == Item.getId(itemUsed.getItem())) {
                    value = tag.getInt(key + ".count")+1;
                    find = true;
                    if (value <= maxValue){
                        tag.putInt(key + ".count", value);
                        itemTag.put(key+i, tag);
                    } else {
                        Minecraft.getInstance().gui.setOverlayMessage(new TranslatableComponent("text."+Artefact.MOD_ID+".ballLimit"), false);
                    }
                }
            }

            if (!find){
                tag = new CompoundTag();
                tag.putInt(key + ".id", Item.getId(itemUsed.getItem()));
                tag.putInt(key + ".count", value);

                elements++;
                itemTag.put(key + (elements-1), tag);
                itemTag.putInt(key+".elements", elements);
            }
        }

        nbt.put(key, itemTag);
    }

    public void use(Player player, int damage){
        CompoundTag playerNbt = player.getPersistentData();
        CompoundTag playerTag = playerNbt.getCompound(KEY);
        int current = playerTag.getInt(KEY+".current");
        CompoundTag tag = playerTag.getCompound(KEY+current);
        ItemStack itemStack = player.getUseItem();
        String key = KEY;

        int value = tag.getInt(key+".count")-1;

        if (value > 0) {
            tag.putInt(key+".count", value);
        } else {
            int elements = playerTag.getInt(KEY+".elements")-1;

            if (elements == 0){
                playerNbt.remove(KEY);
            } else {
                playerTag.remove(KEY+current);
                playerTag.putInt(KEY+".current", current+1);
                playerTag.putInt(KEY+".elements", elements);
            }
        }

        if(itemStack.isDamageableItem()){
            itemStack.setDamageValue(damage);
        }
    }

    public void use(Player player){
        use(player,0);
    }
}
