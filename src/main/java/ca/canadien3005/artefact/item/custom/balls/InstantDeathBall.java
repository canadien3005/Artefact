package ca.canadien3005.artefact.item.custom.balls;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.custom.Ball;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class InstantDeathBall extends Ball {

    public InstantDeathBall(Properties properties){
        super(properties);

    }

    @Override
    public void hit(Level level, Player player, LivingEntity target) {
        if(canUse(player)){
            use(player, 20);

            target.hurt(DamageSource.playerAttack(player), target.getMaxHealth()+target.getAbsorptionAmount());
            DamageSource damageSource = new DamageSource(Artefact.MOD_ID+".name").setMagic();
            player.hurt(damageSource, target.getMaxHealth()+target.getAbsorptionAmount());
        }
    }
}
