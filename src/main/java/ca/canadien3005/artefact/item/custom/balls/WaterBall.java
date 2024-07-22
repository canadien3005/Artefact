package ca.canadien3005.artefact.item.custom.balls;

import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WaterBall extends Ball {

    public WaterBall(Properties properties) {
        super(properties);
    }

    @Override
    public void hit(Level level, Player player, LivingEntity target) {
        use(player);
        String key = KEY+".entity.remainingTick";
        CompoundTag entityCompoundTags = target.getPersistentData();
        int remainingTick = 100;

        if (entityCompoundTags.contains(key)){
            remainingTick = entityCompoundTags.getInt(key)+100;
            if (remainingTick >= 500){
                remainingTick = 500;
            }
        }

        entityCompoundTags.putInt(key, remainingTick);
    }
}
