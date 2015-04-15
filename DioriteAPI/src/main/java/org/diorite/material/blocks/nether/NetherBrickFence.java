package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "NetherBrickFence" and all its subtypes.
 */
public class NetherBrickFence extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHER_BRICK_FENCE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHER_BRICK_FENCE__HARDNESS;

    public static final NetherBrickFence NETHER_BRICK_FENCE = new NetherBrickFence();

    private static final Map<String, NetherBrickFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected NetherBrickFence()
    {
        super("NETHER_BRICK_FENCE", 113, "minecraft:nether_brick_fence", "NETHER_BRICK_FENCE", (byte) 0x00);
    }

    public NetherBrickFence(final String enumName, final int type)
    {
        super(NETHER_BRICK_FENCE.name(), NETHER_BRICK_FENCE.getId(), NETHER_BRICK_FENCE.getMinecraftId(), enumName, (byte) type);
    }

    public NetherBrickFence(final int maxStack, final String typeName, final byte type)
    {
        super(NETHER_BRICK_FENCE.name(), NETHER_BRICK_FENCE.getId(), NETHER_BRICK_FENCE.getMinecraftId(), maxStack, typeName, type);
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
    public NetherBrickFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickFence getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of NetherBrickFence sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of NetherBrickFence or null
     */
    public static NetherBrickFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherBrickFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of NetherBrickFence or null
     */
    public static NetherBrickFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final NetherBrickFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        NetherBrickFence.register(NETHER_BRICK_FENCE);
    }
}
