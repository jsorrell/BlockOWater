package com.jsorrell.blockowater.common.tileentity;

import com.jsorrell.blockowater.common.config.ConfigSettings;
import net.minecraft.init.Biomes;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileEntityBlockOWaterTest {
  @Test
  void testBiomeValid() {
    Bootstrap.register();

    ConfigSettings.workingConditions.requiresWaterBiome = false;
    ConfigSettings.workingConditions.worksInNether = true;

    for (ResourceLocation key : Biome.REGISTRY.getKeys()) {
      Biome biome = Biome.REGISTRY.getObject(key);
      Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(biome));
    }


    ConfigSettings.workingConditions.requiresWaterBiome = true;
    ConfigSettings.workingConditions.worksInNether = true; // This should be ignored b/c water biome cancels out

    Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(Biomes.RIVER));
    Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(Biomes.OCEAN));
    Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(Biomes.DEEP_OCEAN));
    Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(Biomes.FROZEN_OCEAN));

    Assertions.assertFalse(TileEntityBlockOWater.checkBiomeValid(Biomes.BIRCH_FOREST));
    Assertions.assertFalse(TileEntityBlockOWater.checkBiomeValid(Biomes.BEACH));
    Assertions.assertFalse(TileEntityBlockOWater.checkBiomeValid(Biomes.HELL));


    ConfigSettings.workingConditions.requiresWaterBiome = false;
    ConfigSettings.workingConditions.worksInNether = false;

    for (ResourceLocation key : Biome.REGISTRY.getKeys()) {
      Biome biome = Biome.REGISTRY.getObject(key);
      if (biome == Biomes.HELL) {
        Assertions.assertFalse(TileEntityBlockOWater.checkBiomeValid(biome));
      } else {
        Assertions.assertTrue(TileEntityBlockOWater.checkBiomeValid(biome));
      }
    }
  }
}
