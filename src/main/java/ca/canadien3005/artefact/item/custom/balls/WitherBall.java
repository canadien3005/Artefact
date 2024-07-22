package ca.canadien3005.artefact.item.custom.balls;

import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WitherBall extends Ball {

    public WitherBall(Properties properties){
        super(properties);
    }

    @Override
    public void hit(Level level, Player player, LivingEntity target) {
        use(player);
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
    }
}
