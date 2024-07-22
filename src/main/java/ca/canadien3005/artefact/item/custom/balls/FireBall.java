package ca.canadien3005.artefact.item.custom.balls;

import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FireBall extends Ball {
    public FireBall(Properties properties) {
        super(properties);
    }

    @Override
    public void hit(Level level, Player player, LivingEntity target) {
        use(player, 10);
        target.setRemainingFireTicks(200);
    }
}
