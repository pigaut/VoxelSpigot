package io.github.pigaut.voxel.core.particle;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.version.*;
import org.bukkit.*;

import java.util.*;

public class ParticleType {

    public static final List<Particle> DIRECTIONAL;
    public static final List<Particle> SPELLS;

    static {
        final SpigotVersion serverVersion = SpigotServer.getVersion();
        final List<XParticle> directionalParticles = new ArrayList<>();
        directionalParticles.add(XParticle.PORTAL);
        directionalParticles.add(XParticle.TOTEM_OF_UNDYING);
        directionalParticles.add(XParticle.SPIT);
        directionalParticles.add(XParticle.SQUID_INK);
        directionalParticles.add(XParticle.LARGE_SMOKE);
        directionalParticles.add(XParticle.SMOKE);
        directionalParticles.add(XParticle.FISHING);
        directionalParticles.add(XParticle.NAUTILUS);
        directionalParticles.add(XParticle.BUBBLE);
        directionalParticles.add(XParticle.BUBBLE_COLUMN_UP);
        directionalParticles.add(XParticle.BUBBLE_POP);
        directionalParticles.add(XParticle.CAMPFIRE_COSY_SMOKE);
        directionalParticles.add(XParticle.CAMPFIRE_SIGNAL_SMOKE);
        directionalParticles.add(XParticle.CLOUD);
        directionalParticles.add(XParticle.CRIT);
        directionalParticles.add(XParticle.ENCHANTED_HIT);
        directionalParticles.add(XParticle.DAMAGE_INDICATOR);
        directionalParticles.add(XParticle.DRAGON_BREATH);
        directionalParticles.add(XParticle.ENCHANT);
        directionalParticles.add(XParticle.END_ROD);
        directionalParticles.add(XParticle.POOF);
        directionalParticles.add(XParticle.FIREWORK);
        directionalParticles.add(XParticle.FLAME);

        if (serverVersion.equalsOrIsNewerThan(SpigotVersion.V1_16)) {
            directionalParticles.add(XParticle.SOUL);
            directionalParticles.add(XParticle.SOUL_FIRE_FLAME);
            directionalParticles.add(XParticle.REVERSE_PORTAL);
        }

        if (serverVersion.equalsOrIsNewerThan(SpigotVersion.V1_17)) {
            directionalParticles.add(XParticle.SCRAPE);
            directionalParticles.add(XParticle.SMALL_FLAME);
            directionalParticles.add(XParticle.ELECTRIC_SPARK);
        }

        if (serverVersion.equalsOrIsNewerThan(SpigotVersion.V1_19)) {
            directionalParticles.add(XParticle.SCULK_CHARGE);
            directionalParticles.add(XParticle.SCULK_CHARGE_POP);
            directionalParticles.add(XParticle.SCULK_SOUL);
        }

        if (serverVersion.equalsOrIsNewerThan(SpigotVersion.V1_20)) {
            directionalParticles.add(XParticle.WAX_OFF);
            directionalParticles.add(XParticle.WAX_ON);
        }

        DIRECTIONAL = directionalParticles.stream()
                .map(XParticle::get).toList();

        SPELLS = List.of(XParticle.EFFECT.get(), XParticle.ENTITY_EFFECT.get(), XParticle.INSTANT_EFFECT.get());

    }

}
