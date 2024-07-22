package ca.canadien3005.artefact.item.custom.balls;

import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class ThunderBall extends Ball {
    public ThunderBall(Properties properties) {
        super(properties);
    }

    @Override
    public void hit(Level level, Player player, LivingEntity target) {
        use(player, 20);
        LightningBolt lg = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        lg.moveTo(target.getX(), target.getY(), target.getZ());
        level.addFreshEntity(lg);
    }
}
