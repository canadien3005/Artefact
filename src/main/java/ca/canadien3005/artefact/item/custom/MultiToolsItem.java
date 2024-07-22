package ca.canadien3005.artefact.item.custom;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.screen.MultiToolsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MultiToolsItem extends DiggerItem {

    public static List<Item> items;

    public MultiToolsItem(Properties pProperties){
        super(1, 1, Tiers.WOOD, BlockTags.REPLACEABLE_PLANTS, pProperties);
    }

    private static List<Item> getItems(List<Integer> items) {
        if (items == null) return null;

        List<Item> itemList = new ArrayList<>();

        for (Integer item : items){
            itemList.add(Item.byId(item));
        }

        return itemList;
    }

    private static void changeItemStack(ItemStack pItemStack){
        if (MultiToolsScreen.getIndice() != -2){
            CompoundTag nbt = pItemStack.getOrCreateTag();
            Item item;
            if (MultiToolsScreen.getIndice() == -1){
                item = ModItems.MULTI_TOOLS.get();
            } else {
                item = items.get(MultiToolsScreen.getIndice());
            }

            nbt.putInt("artefact.current_tool", Item.getId(item));
            Minecraft.getInstance().gui.setOverlayMessage(new TextComponent(item.getDescription().getString()), false);
            pItemStack.setTag(nbt);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        Enchantment[] enchantments = {Enchantments.BLOCK_FORTUNE, Enchantments.MENDING, Enchantments.VANISHING_CURSE,
                Enchantments.UNBREAKING, Enchantments.BLOCK_EFFICIENCY, Enchantments.SILK_TOUCH};

        //for (Enchantment permitEnchantement : enchantments){
            //if (enchantment.equals(permitEnchantement)) return super.canApplyAtEnchantingTable(stack, enchantment);
        //}

        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return super.isEnchantable(pStack);
    }

    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pStack.sameItem(ModItems.MULTI_TOOLS.get().getDefaultInstance())){
            if (!pLevel.isClientSide && pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
                pStack.hurtAndBreak(1, pEntityLiving, (p_40992_) -> {
                    p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        return true;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pIsSelected) {
            changeItemStack(pStack);
            if (!(pLevel.isClientSide())){
                MultiToolsScreen.setIndice(-2);
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        InteractionHand pHand = pContext.getHand();
        ItemStack pItemStack = pContext.getItemInHand();
        Item item = getItem(pItemStack);
        BlockHitResult pHitResult = new BlockHitResult(pContext.getClickLocation(), pContext.getClickedFace(), pContext.getClickedPos(), pContext.isInside());

        UseOnContext newContext = new UseOnContext(pLevel, pPlayer, pHand, item.getDefaultInstance(), pHitResult);
        InteractionResult interactionResult;

        if (this == item){
            return super.useOn(pContext);
        } else {
            interactionResult = item.useOn(newContext);
        }


        if (interactionResult == InteractionResult.CONSUME){
            if (pPlayer != null) {
                pItemStack.hurtAndBreak(1, pPlayer, (p_150686_) -> {
                    p_150686_.broadcastBreakEvent(pContext.getHand());
                });
            }
        }

        //pItemStack.setDamageValue(pItemStack.getDamageValue() + (newContext.getItemInHand().getDamageValue()-initialDamage));
        return interactionResult;
    }

    //Use on a block
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Item current = getItem(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND));
        if (this == current){
            return super.use(pLevel, pPlayer, pUsedHand);
        } else {
            return current.use(pLevel, pPlayer, pUsedHand);
        }
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack itemStack, BlockState pBlock) {
        Item current = getItem(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND));
        if (this == current){
            return super.isCorrectToolForDrops(itemStack, pBlock);
        } else {
            return current.isCorrectToolForDrops(itemStack, pBlock);
        }
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        Item current = getItem(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND));

        if (this == current){
            return super.getDestroySpeed(pStack, pState);
        } else {
            return current.getDestroySpeed(pStack, pState);
        }
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(items != null && !items.isEmpty()){
            pTooltipComponents.add(new TextComponent(new TranslatableComponent("text." + Artefact.MOD_ID + ".multi_tools_tooltip").getString() +
                    new TranslatableComponent("text." + Artefact.MOD_ID + "." + getItem(pStack).getDescriptionId()).getString()));
        }
    }

    public static Item getItem(ItemStack itemStack){
        CompoundTag tags = itemStack.getTag();
        if (tags != null) {
            int value = tags.getInt("artefact.current_tool");
            Item item = Item.byId(value);
            if (item != Items.AIR) {
                return item;
            }
        }
        return ModItems.MULTI_TOOLS.get();
    }
}
