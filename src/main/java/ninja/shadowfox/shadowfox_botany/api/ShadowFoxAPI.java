package ninja.shadowfox.shadowfox_botany.api;

import net.minecraft.block.Block;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.EnumHelper;
import ninja.shadowfox.shadowfox_botany.api.item.ThrowableCollidingItem;
import ninja.shadowfox.shadowfox_botany.api.recipe.RecipeTreeCrafting;
import ninja.shadowfox.shadowfox_botany.api.trees.IIridescentSaplingVariant;
import ninja.shadowfox.shadowfox_botany.api.trees.IridescentSaplingBaseVariant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShadowFoxAPI {

    public static Item.ToolMaterial RUNEAXE = EnumHelper.addToolMaterial("RUNEAXE", 4, 1561, 8f, 4f, 15);

    public static List<RecipeTreeCrafting> treeRecipes = new ArrayList();
    public static List<IIridescentSaplingVariant> treeVariants = new ArrayList();
    public static Map<String, ThrowableCollidingItem> collidingItemHashMap = new LinkedHashMap();
    public static ThrowableCollidingItem fallbackTcl = new ThrowableCollidingItem("shadowfox_fallback", new ItemStack(Items.blaze_rod),
            new ThrowableCollidingItem.OnImpactEvent() {
                @Override
                public void onImpact(EntityThrowable throwable, MovingObjectPosition movingObject) {

                }
            });

    /**
     * Adds a tree crafting recipe to the registry.
     *
     * @param recipe - The recipe to add.
     * @return The recipe that was added to the registry.
     */
    public static RecipeTreeCrafting addTreeRecipe(RecipeTreeCrafting recipe) {
        treeRecipes.add(recipe);
        return recipe;
    }

    /**
     * Adds a tree crafting recipe with the specified parameters to the registry.
     *
     * @param mana        - The mana cost for the recipe.
     * @param outputBlock - The block that is created from the recipe.
     * @param meta        - The metadata of the created block.
     * @param inputs      - The items used in the infusion.
     * @return The recipe that was added to the registry.
     */
    public static RecipeTreeCrafting addTreeRecipe(int mana, Block outputBlock, int meta, Object... inputs) {
        return addTreeRecipe(new RecipeTreeCrafting(mana, outputBlock, meta, inputs));
    }

    /**
     * Adds a tree crafting recipe with the specified parameters to the registry.
     *
     * @param mana        - The mana cost for the recipe.
     * @param outputBlock - The block that is created from the recipe.
     * @param meta        - The metadata of the created block.
     * @param inputs      - The items used in the infusion.
     * @param throttle    - The maximum mana that can be absorbed per tick for this recipe.
     * @return The recipe that was added to the registry.
     */
    public static RecipeTreeCrafting addTreeRecipe(int mana, Block outputBlock, int meta, int throttle, Object... inputs) {
        return addTreeRecipe(new RecipeTreeCrafting(mana, outputBlock, meta, throttle, inputs));
    }

    /**
     * Adds an Iridescent Sapling variant to the registry.
     *
     * @param variant - The variant to add.
     * @return The variant added to the registry.
     */
    public static IIridescentSaplingVariant addTreeVariant(IIridescentSaplingVariant variant) {
        treeVariants.add(variant);
        return variant;
    }

    /**
     * Adds an Iridescent Sapling variant with the specified parameters to the registry, ignoring metadata.
     *
     * @param soil   - The soil block the variant uses.
     * @param wood   - The wood block the variant uses.
     * @param leaves - The leaves block the variant uses.
     * @return The variant that was added to the registry.
     */
    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves));
    }

    /**
     * Adds an Iridescent Sapling variant with the specified parameters to the registry, with a specific metadata.
     *
     * @param soil   - The soil block the variant uses.
     * @param wood   - The wood block the variant uses.
     * @param leaves - The leaves block the variant uses.
     * @param meta   - The metadata of the soil the variant uses.
     * @return The variant that was added to the registry.
     */
    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int meta) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, meta));
    }

    /**
     * Adds an Iridescent Sapling variant with the specified parameters to the registry, using a range of metadata.
     *
     * @param soil    - The soil block the variant uses.
     * @param wood    - The wood block the variant uses.
     * @param leaves  - The leaves block the variant uses.
     * @param metaMin - The minimum meta value of the soil the variant uses.
     * @param metaMax - The maximum meta value of the soil the variant uses.
     * @return The variant that was added to the registry.
     */
    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, metaMin, metaMax));
    }

    /**
     * Adds an Iridescent Sapling variant with the specified parameters to the registry, using a range of metadata.
     *
     * @param soil      - The soil block the variant uses.
     * @param wood      - The wood block the variant uses.
     * @param leaves    - The leaves block the variant uses.
     * @param metaMin   - The minimum meta value of the soil the variant uses.
     * @param metaMax   - The maximum meta value of the soil the variant uses.
     * @param metaShift - The amount to subtract from the soil's metadata value to make the leaf metadata.
     * @return The variant that was added to the registry.
     */
    public static IIridescentSaplingVariant addTreeVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax, int metaShift) {
        return addTreeVariant(new IridescentSaplingBaseVariant(soil, wood, leaves, metaMin, metaMax, metaShift));
    }

    public static ThrowableCollidingItem registerThrowable(ThrowableCollidingItem tcl) {
        collidingItemHashMap.put(tcl.getKey(), tcl);
        return tcl;
    }

    public static ThrowableCollidingItem getThrowableFromKey(String key) {
        return collidingItemHashMap.containsKey(key) ? collidingItemHashMap.get(key) : fallbackTcl;
    }

    /**
     * Gets a list of all acceptable Iridescent Sapling soils.
     *
     * @return A list of all Iridescent Sapling soils.
     */
    public static List<Block> iridescentSoils() {
        List<Block> soils = new ArrayList<Block>();
        for (IIridescentSaplingVariant variant : treeVariants) {
            soils.addAll(variant.getAcceptableSoils());
        }
        return soils;
    }

    /**
     * Gets the variant for a given soil.
     *
     * @param soil - The block the sapling is placed on.
     * @param meta - The meta of the block the sapling is on.
     * @return The variant, if there is one.
     */
    public static IIridescentSaplingVariant getTreeVariant(Block soil, int meta) {
        for (IIridescentSaplingVariant variant : treeVariants) {
            if (variant.matchesSoil(soil, meta)) return variant;
        }
        return null;
    }

}
