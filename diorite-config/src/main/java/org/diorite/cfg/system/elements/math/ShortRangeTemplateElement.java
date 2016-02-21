/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.cfg.system.elements.math;

import java.io.IOException;
import java.util.Arrays;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.elements.StringTemplateElement;
import org.diorite.cfg.system.elements.TemplateElement;
import org.diorite.utils.math.ShortRange;

/**
 * Template handler for all short range objects.
 *
 * @see ShortRange
 */
public class ShortRangeTemplateElement extends TemplateElement<ShortRange>
{
    /**
     * Instance of template to direct-use.
     */
    public static final ShortRangeTemplateElement INSTANCE = new ShortRangeTemplateElement();

    /**
     * Construct new default template handler.
     */
    public ShortRangeTemplateElement()
    {
        super(ShortRange.class, obj -> {
            if (obj instanceof String)
            {
                final ShortRange shortRange = ShortRange.valueOf((String) obj);
                if (shortRange == null)
                {
                    throw new UnsupportedOperationException("Can't convert string to ShortRange: " + obj);
                }
                return shortRange;
            }
            if (obj instanceof byte[])
            {
                final byte[] array = (byte[]) obj;
                if ((array.length != 2) || (array[0] > array[1]))
                {
                    throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
                }
                return new ShortRange(array[0], array[1]);
            }
            if (obj instanceof short[])
            {
                final short[] array = (short[]) obj;
                if ((array.length != 2) || (array[0] > array[1]))
                {
                    throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
                }
                return new ShortRange(array[0], array[1]);
            }
            if (obj instanceof int[])
            {
                final int[] array = (int[]) obj;
                if ((array.length != 2) || (array[0] > array[1]))
                {
                    throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
                }
                return new ShortRange(array[0], array[1]);
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to ShortRange: " + obj);
        }, c -> ShortRange.class.isAssignableFrom(c) || String.class.isAssignableFrom(c));
    }

    @Override
    protected ShortRange convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof ShortRange)
        {
            return (ShortRange) def;
        }
        if (def instanceof String)
        {
            final ShortRange shortRange = ShortRange.valueOf((String) def);
            if (shortRange == null)
            {
                throw new UnsupportedOperationException("Can't convert string to ShortRange: " + def);
            }
            return shortRange;
        }
        if (def instanceof byte[])
        {
            final byte[] array = (byte[]) def;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
            }
            return new ShortRange(array[0], array[1]);
        }
        if (def instanceof short[])
        {
            final short[] array = (short[]) def;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
            }
            return new ShortRange(array[0], array[1]);
        }
        if (def instanceof int[])
        {
            final int[] array = (int[]) def;
            if ((array.length != 2) || (array[0] > array[1]))
            {
                throw new UnsupportedOperationException("Can't convert array to ShortRange: " + Arrays.toString(array));
            }
            return new ShortRange(array[0], array[1]);
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final ShortRange element = (elementRaw instanceof ShortRange) ? ((ShortRange) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.getMin() + ", " + element.getMax()), level, elementPlace);
    }

}