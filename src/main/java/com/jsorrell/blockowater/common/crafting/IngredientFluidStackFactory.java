package com.jsorrell.blockowater.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class IngredientFluidStackFactory implements IIngredientFactory {
  @Nonnull
  @Override
  public Ingredient parse(JsonContext context, JsonObject json) {
    String fluidName = JsonUtils.getString(json, "name", "water");
    int amount = JsonUtils.getInt(json, "amount", 1000);

    Fluid fluid = FluidRegistry.getFluid(fluidName);

    if (fluid == null) {
      throw new JsonSyntaxException("Fluid " + fluidName + " not registered.");
    }

    return new IngredientFluidStack(fluid, amount);
  }
}
