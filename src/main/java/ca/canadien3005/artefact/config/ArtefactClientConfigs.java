package ca.canadien3005.artefact.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ArtefactClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Artefact");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
