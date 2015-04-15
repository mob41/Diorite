package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MelonStem" and all its subtypes.
 */
public class MelonStem extends PlantStem
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MELON_STEM__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MELON_STEM__HARDNESS;

    public static final MelonStem MELON_STEM = new MelonStem();

    private static final Map<String, MelonStem>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MelonStem> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MelonStem()
    {
        super("MELON_STEM", 105, "minecraft:melon_stem", "MELON_STEM", (byte) 0x00);
    }

    public MelonStem(final String enumName, final int type)
    {
        super(MELON_STEM.name(), MELON_STEM.getId(), MELON_STEM.getMinecraftId(), enumName, (byte) type);
    }

    public MelonStem(final int maxStack, final String typeName, final byte type)
    {
        super(MELON_STEM.name(), MELON_STEM.getId(), MELON_STEM.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public MelonStem getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MelonStem getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MelonStem sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of MelonStem or null
     */
    public static MelonStem getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MelonStem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of MelonStem or null
     */
    public static MelonStem getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final MelonStem element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MelonStem.register(MELON_STEM);
    }
}
