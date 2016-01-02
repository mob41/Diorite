package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.BannerMeta;

/**
 * Represent builder of banner item meta data.
 */
public class BannerMetaBuilder implements IBannerMetaBuilder<BannerMetaBuilder, BannerMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final BannerMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected BannerMetaBuilder(final BannerMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected BannerMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(BannerMeta.class);
    }

    @Override
    public BannerMeta meta()
    {
        return this.meta;
    }

    @Override
    public BannerMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public BannerMetaBuilder start()
    {
        return new BannerMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public BannerMetaBuilder start(final BannerMeta meta)
    {
        return new BannerMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}