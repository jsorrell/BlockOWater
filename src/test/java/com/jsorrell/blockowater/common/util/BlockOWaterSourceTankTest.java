package com.jsorrell.blockowater.common.util;

import com.jsorrell.blockowater.common.config.ConfigSettings;
import net.minecraft.init.Bootstrap;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlockOWaterSourceTankTest {
  @Test
  void testCapacity() {
    // Weird formula so kinda hard to test
    ConfigSettings.waterGeneration.generationAmount = 1;
    ConfigSettings.waterGeneration.generationTimer = Integer.MAX_VALUE;
    Assertions.assertEquals(1000, BlockOWaterSourceTank.calculateCapacity());

    ConfigSettings.waterGeneration.generationAmount = 1000;
    ConfigSettings.waterGeneration.generationTimer = 20;
    Assertions.assertEquals(1000, BlockOWaterSourceTank.calculateCapacity());

    ConfigSettings.waterGeneration.generationAmount = Integer.MAX_VALUE;
    ConfigSettings.waterGeneration.generationTimer = 1;
    Assertions.assertEquals(Integer.MAX_VALUE, BlockOWaterSourceTank.calculateCapacity());

    ConfigSettings.waterGeneration.generationAmount = 3000;
    ConfigSettings.waterGeneration.generationTimer = 2;
    int capacity = BlockOWaterSourceTank.calculateCapacity();
    Assertions.assertTrue(1000 <= capacity && capacity <= 150000);
  }

  @Test
  void testTick() {
    Bootstrap.register();
    BlockOWaterSourceTank tank1 = new BlockOWaterSourceTank(null);
    FluidStack fluid1 = tank1.getFluid();
    Assertions.assertNotNull(fluid1);
    Assertions.assertEquals(FluidRegistry.WATER, fluid1.getFluid());
    Assertions.assertEquals(0, fluid1.amount);

    ConfigSettings.waterGeneration.generationTimer = 1;
    ConfigSettings.waterGeneration.generationAmount = 1;
    tank1.tick();
    Assertions.assertEquals(1, tank1.getFluidAmount());
    tank1.tick();
    Assertions.assertEquals(2, tank1.getFluidAmount());

    ConfigSettings.waterGeneration.generationAmount = 2;
    tank1.tick();
    Assertions.assertEquals(4, tank1.getFluidAmount());

    ConfigSettings.waterGeneration.generationTimer = 5;
    for (int i = 0; i < 5; ++i) {
      tank1.tick();
    }
    Assertions.assertEquals(6, tank1.getFluidAmount());

    ConfigSettings.waterGeneration.generationTimer = 1;
    ConfigSettings.waterGeneration.generationAmount = 1;

    for (int i = 0; i < 10000; ++i) {
      tank1.tick();
    }

    Assertions.assertEquals(1000, tank1.getFluidAmount());
  }

  @Test
  void testCapability() {
    Bootstrap.register();
    BlockOWaterSourceTank tank1 = new BlockOWaterSourceTank(null);

    ConfigSettings.waterGeneration.generationTimer = 1;
    ConfigSettings.waterGeneration.generationAmount = 1000;
    tank1.tick(); // Add 1 bucket

    int amount1 = tank1.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
    Assertions.assertEquals(0, amount1);

    FluidStack stack1 = tank1.drain(new FluidStack(FluidRegistry.WATER, 92), false);
    Assertions.assertNotNull(stack1);
    Assertions.assertEquals(FluidRegistry.WATER, stack1.getFluid());
    Assertions.assertEquals(92, stack1.amount);
    Assertions.assertEquals(1000, tank1.getFluidAmount());

    FluidStack stack2 = tank1.drain(new FluidStack(FluidRegistry.WATER, 92), true);
    Assertions.assertNotNull(stack2);
    Assertions.assertEquals(FluidRegistry.WATER, stack2.getFluid());
    Assertions.assertEquals(92, stack2.amount);
    Assertions.assertEquals(908, tank1.getFluidAmount());

    FluidStack stack3 = tank1.drain(new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE), true);
    Assertions.assertNotNull(stack3);
    Assertions.assertEquals(FluidRegistry.WATER, stack3.getFluid());
    Assertions.assertEquals(908, stack3.amount);
    Assertions.assertEquals(0, tank1.getFluidAmount());
  }
}
