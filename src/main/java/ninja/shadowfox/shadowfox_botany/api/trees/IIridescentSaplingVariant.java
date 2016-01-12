package ninja.shadowfox.shadowfox_botany.api.trees;

import net.minecraft.block.Block;

import java.util.List;

public interface IIridescentSaplingVariant {
    int getMeta(Block soil, int meta, Block toPlace);

    List<Block> getAcceptableSoils();

    boolean matchesSoil(Block soil, int meta);

    Block getLeaves(Block soil, int meta);

    Block getWood(Block soil, int meta);
}
