package net.swofty.commons.skyblock.enchantment;

import lombok.Getter;
import lombok.SneakyThrows;
import net.swofty.commons.skyblock.enchantment.impl.EnchantmentEfficiency;
import net.swofty.commons.skyblock.enchantment.impl.EnchantmentSharpness;
import net.swofty.commons.skyblock.enchantment.abstr.Ench;
import net.swofty.commons.skyblock.enchantment.abstr.EnchFromTable;
import net.swofty.commons.skyblock.enchantment.impl.EnchantmentProtection;
import net.swofty.commons.skyblock.enchantment.impl.EnchantmentScavenger;
import net.swofty.commons.skyblock.utility.StringUtility;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public enum EnchantmentType {
    SHARPNESS(EnchantmentSharpness.class),
    EFFICIENCY(EnchantmentEfficiency.class),
    SCAVENGER(EnchantmentScavenger.class),
    PROTECTION(EnchantmentProtection.class);

    private final Class<? extends Ench> clazz;
    private final List<EnchantmentType> conflicts;

    private final Ench ench;

    @SneakyThrows
    EnchantmentType(Class<? extends Ench> ench, EnchantmentType... conflicts) {
        this.clazz = ench;
        this.conflicts = List.of(conflicts);

        this.ench = ench.getConstructor().newInstance();
    }

    public int getApplyCost(int level) {
        if (level < 1 || level > ench.getLevelsToApply().maximumLevel())
            throw new IllegalArgumentException("level cannot be less than 1 and more than " +
                    ench.getLevelsToApply().maximumLevel() + " for " + name());
        return ench.getLevelsToApply().get(level);
    }

    public String getDescription(int level) {
        if (level < 1 || level > ench.getLevelsToApply().maximumLevel())
            return ("level cannot be less than 1 and more than " +
                    ench.getLevelsToApply().maximumLevel() + " for " + name());
        return ench.getDescription(level);
    }

    public @Nullable EnchFromTable getEnchFromTable() {
        if (ench instanceof EnchFromTable)
            return (EnchFromTable) ench;
        return null;
    }

    public String getName() {
        return StringUtility.toNormalCase(this.name());
    }
}