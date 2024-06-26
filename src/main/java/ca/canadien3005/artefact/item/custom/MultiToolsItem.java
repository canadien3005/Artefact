package ca.canadien3005.artefact.item.custom;

import ca.canadien3005.artefact.item.ModItems;
import ca.canadien3005.artefact.item.custom.multiToolSaveData.MultiToolSaveData;
import ca.canadien3005.artefact.item.custom.multiToolSaveData.MultiToolSaveObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.List;

public class MultiToolsItem extends Item {

    private static final List<Item> items = ModItems.multiToolItems;
    private Item current = this;

    public MultiToolsItem(Properties pProperties){
        super(pProperties);
    }

    public void init(){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        List<MultiToolSaveObject> DATA = MultiToolSaveData.getData(server.overworld()).getDATA();
        current = DATA.get(DATA.size()-1).getCurrent();
    }

    public void changeCurrentItem(int indice) {
        if (indice == -1){
            this.current = this;
        } else {
            this.current = items.get(indice);
        }

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        MultiToolSaveData.putData(this.current, server.overworld());
        Minecraft.getInstance().gui
                .setOverlayMessage(new TextComponent(current.getDescription().getString()), false);
    }

    public void changeToDefault() {
        this.changeCurrentItem(-1);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        System.out.println(current);
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        InteractionHand pHand = pContext.getHand() ;
        MultiToolsItem multiToolsItem = (MultiToolsItem) pContext.getItemInHand().getItem();
        ItemStack pItemStack = multiToolsItem.current.getDefaultInstance();
        BlockHitResult pHitResult = new BlockHitResult(pContext.getClickLocation(), pContext.getClickedFace(), pContext.getClickedPos(), pContext.isInside());

        UseOnContext newContext = new UseOnContext(pLevel, pPlayer, pHand, pItemStack, pHitResult);
        if (!(current instanceof MultiToolsItem)){
            InteractionResult interactionResult = current.useOn(newContext);
            if (interactionResult == InteractionResult.SUCCESS) {
                pContext.getItemInHand().hurtAndBreak(1, pPlayer, (player) -> {
                    player.broadcastBreakEvent(pContext.getHand());
                });
            }

            return interactionResult;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!(current instanceof MultiToolsItem)){
            return current.use(pLevel, pPlayer, pUsedHand);
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return current.isCorrectToolForDrops(pBlock);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return current.getDestroySpeed(pStack, pState);
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("Current mode: " + current.getDescriptionId()));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return super.isDamageable(stack);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return super.isDamaged(stack);
    }

    public Item getCurrent() {
        return current;
    }
}
