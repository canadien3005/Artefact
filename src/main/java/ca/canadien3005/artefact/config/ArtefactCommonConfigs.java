package ca.canadien3005.artefact.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ArtefactCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_VALUE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> RESET_ON_DEATH;

    static {
        BUILDER.push("Ball Configs");

        MAX_VALUE = BUILDER.comment("Define the max value of each ball a player can consume").define("Max Value", 16);
        RESET_ON_DEATH = BUILDER.comment("Define if the ball the player has consumed is remove if the player died (true/false)").define("Reset On Death", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
