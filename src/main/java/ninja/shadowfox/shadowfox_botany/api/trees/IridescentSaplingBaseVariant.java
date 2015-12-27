package ninja.shadowfox.shadowfox_botany.api.trees;

import net.minecraft.block.Block;
import java.util.ArrayList;
import java.util.List;

public class IridescentSaplingBaseVariant implements IIridescentSaplingVariant {
    public Block soil;
    public Block wood;
    public Block leaves;
    public int metaMin = 0;
    public int metaMax = 15;
    public int metaShift = 0;

    public IridescentSaplingBaseVariant(Block soil, Block wood, Block leaves) {
        this.soil = soil;
        this.wood = wood;
        this.leaves = leaves;
    }

    public IridescentSaplingBaseVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax) {
        this(soil, wood, leaves);
        this.metaMin = metaMin;
        this.metaMax = metaMax;
    }

    public IridescentSaplingBaseVariant(Block soil, Block wood, Block leaves, int metaMin, int metaMax, int metaShift) {
        this(soil, wood, leaves, metaMin, metaMax);
        this.metaShift = metaShift;
    }

    public IridescentSaplingBaseVariant(Block soil, Block wood, Block leaves, int meta) {
        this(soil, wood, leaves, meta, meta);
    }

    @Override
    public List<Block> getAcceptableSoils() {
        List<Block> soils = new ArrayList();
        soils.add(this.soil);
        return soils;
    }

    @Override
    public boolean matchesSoil(Block soil, int meta) {
        return soil == this.soil && meta <= metaMax && meta >= metaMin;
    }

    @Override
    public int getMeta(Block soil, int meta, Block toPlace) {
        if (toPlace == this.wood) {
            return meta & 3;
        } else if (toPlace == this.leaves) {
            return meta - this.metaShift;
        }
        return 0;
    }

    @Override
    public Block getLeaves(Block soil, int meta) {
        return leaves;
    }

    @Override
    public Block getWood(Block soil, int meta) {
        return wood;
    }
}
