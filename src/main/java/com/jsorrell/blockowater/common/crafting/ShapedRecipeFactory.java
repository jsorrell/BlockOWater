package com.jsorrell.blockowater.common.crafting;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.Map;
import java.util.Set;

/**
 * Copied and modified from {@link CraftingHelper}
 */
public class ShapedRecipeFactory implements IRecipeFactory {
  @Override
  public IRecipe parse(JsonContext context, JsonObject json) {
    String group = JsonUtils.getString(json, "group", "");

    Map<Character, Ingredient> ingMap = Maps.newHashMap();
    for (Map.Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet()) {
      if (entry.getKey().length() != 1)
        throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
      if (" ".equals(entry.getKey()))
        throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

      ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
    }

    ingMap.put(' ', Ingredient.EMPTY);

    JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");

    if (patternJ.size() == 0)
      throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");

    if (patternJ.size() > 3)
      throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");

    String[] pattern = new String[patternJ.size()];
    for (int x = 0; x < pattern.length; ++x) {
      String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
      if (line.length() > 3)
        throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
      if (x > 0 && pattern[0].length() != line.length())
        throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
      pattern[x] = line;
    }

    CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
    primer.height = pattern.length;
    primer.width = pattern[0].length();
    primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
    primer.input = NonNullList.withSize(primer.width * pattern.length, Ingredient.EMPTY);

    Set<Character> keys = Sets.newHashSet(ingMap.keySet());
    keys.remove(' ');

    int x = 0;
    for (String line : pattern) {
      for (char chr : line.toCharArray()) {
        Ingredient ing = ingMap.get(chr);
        if (ing == null)
          throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
        primer.input.set(x++, ing);
        keys.remove(chr);
      }
    }

    if (!keys.isEmpty())
      throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);

    ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
    return new ShapedRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
  }
}
