package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.ContainerBlock;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EnchantingTable" and all its subtypes.
 */
public class EnchantingTable extends BlockMaterialData implements ContainerBlock
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ENCHANTING_TABLE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ENCHANTING_TABLE__HARDNESS;

    public static final EnchantingTable ENCHANTING_TABLE = new EnchantingTable();

    private static final Map<String, EnchantingTable>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EnchantingTable> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EnchantingTable()
    {
        super("ENCHANTING_TABLE", 116, "minecraft:enchanting_table", "ENCHANTING_TABLE", (byte) 0x00);
    }

    public EnchantingTable(final String enumName, final int type)
    {
        super(ENCHANTING_TABLE.name(), ENCHANTING_TABLE.getId(), ENCHANTING_TABLE.getMinecraftId(), enumName, (byte) type);
    }

    public EnchantingTable(final int maxStack, final String typeName, final byte type)
    {
        super(ENCHANTING_TABLE.name(), ENCHANTING_TABLE.getId(), ENCHANTING_TABLE.getMinecraftId(), maxStack, typeName, type);
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
    public EnchantingTable getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EnchantingTable getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTable getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EnchantingTable sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of EnchantingTable or null
     */
    public static EnchantingTable getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final EnchantingTable element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EnchantingTable.register(ENCHANTING_TABLE);
    }
}
