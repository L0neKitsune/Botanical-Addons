package ninja.shadowfox.shadowfox_botany.api.trees;

import net.minecraft.block.Block;
import java.util.List;

public interface IIridescentSaplingVariant {
    public int getMeta(Block soil, int meta, Block toPlace);
    public List<Block> getAcceptableSoils();
    public boolean matchesSoil(Block soil, int meta);
    public Block getLeaves();
    public Block getWood();
}
