package ca.canadien3005.artefact.event;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.config.ArtefactCommonConfigs;
import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Artefact.MOD_ID)
public class BallEventHandler {

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event){
        Player player = event.getPlayer();
        Entity eventTarget = event.getTarget();
        Level level = event.getPlayer().level;

        CompoundTag nbt = player.getPersistentData();
        String key = Ball.KEY;

        if (nbt.contains(key) && eventTarget instanceof LivingEntity target){
            CompoundTag itemTag = nbt.getCompound(key);
            int current = itemTag.getInt(key+".current");
            CompoundTag tag = itemTag.getCompound(key+current);
            Item item = Item.byId(tag.getInt(key+".id"));
            if (item instanceof Ball ballItem){
                if (ballItem.canUse(player)){
                    ballItem.hit(level, player, target);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onLiving(LivingEvent.LivingUpdateEvent event){
        Entity entity = event.getEntity();
        CompoundTag entityCompoundTag = entity.getPersistentData();
        String key = Ball.KEY+".entity.remainingTick";

        if (entityCompoundTag.contains(key)){
            int remainingTick = entityCompoundTag.getInt(key);

            if (remainingTick%20 == 0) {
                entity.hurt(DamageSource.DROWN, 1);
            }

            if (remainingTick <= 0){
                entityCompoundTag.remove(key);
            } else {
                entityCompoundTag.putInt(key, remainingTick-1);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event){
        boolean resetOnDeath = ArtefactCommonConfigs.RESET_ON_DEATH.get();
        if (event.getEntity() instanceof Player player && resetOnDeath){
            CompoundTag nbt = player.getPersistentData();
            if (nbt.contains(Ball.KEY)){
                nbt.remove(Ball.KEY);
            }
        }
    }
}
