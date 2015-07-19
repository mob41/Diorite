package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class PumpkinSeedsMat extends ItemMaterialData implements PlaceableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final PumpkinSeedsMat PUMPKIN_SEEDS = new PumpkinSeedsMat();

    private static final Map<String, PumpkinSeedsMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PumpkinSeedsMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected PumpkinSeedsMat()
    {
        super("PUMPKIN_SEEDS", 361, "minecraft:pumpkin_seeds", "PUMPKIN_SEEDS", (short) 0x00);
    }

    protected PumpkinSeedsMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected PumpkinSeedsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public PumpkinSeedsMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PumpkinSeedsMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of PumpkinSeeds sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinSeeds or null
     */
    public static PumpkinSeedsMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of PumpkinSeeds sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinSeeds or null
     */
    public static PumpkinSeedsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinSeedsMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinSeedsMat[] types()
    {
        return PumpkinSeedsMat.pumpkinSeedsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinSeedsMat[] pumpkinSeedsTypes()
    {
        return byID.values(new PumpkinSeedsMat[byID.size()]);
    }

    static
    {
        PumpkinSeedsMat.register(PUMPKIN_SEEDS);
    }
}

