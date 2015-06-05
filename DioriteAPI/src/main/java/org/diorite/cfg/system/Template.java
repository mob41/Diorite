package org.diorite.cfg.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.diorite.cfg.system.elements.TemplateElement.ElementPlace;
import org.diorite.utils.reflections.ReflectElement;

/**
 * Object representation of class yaml template, used to generate
 * string with yaml, from given object.
 *
 * @param <T> type of object.
 */
public interface Template<T>
{
    /**
     * @return name of template.
     */
    String getName();

    /**
     * @return type of template.
     */
    Class<T> getType();

    /**
     * @return header comment, may be null.
     */
    String getHeader();

    /**
     * @return footer comment, may be null.
     */
    String getFooter();

    /**
     * @return copy of map contains all fields data.
     */
    Map<ConfigField, ReflectElement<?>> getFields();

    /**
     * @return copy of map with mapping key->field.
     */
    Map<String, ConfigField> getFieldsNameMap();

    /**
     * Load object from string using this template.
     *
     * @param str string with object data in yaml.
     *
     * @return object created from yaml data and template.
     */
    T load(String str);

    /**
     * Load object from {@link Reader} using this template.
     *
     * @param reader {@link Reader} with object data in yaml.
     *
     * @return object created from yaml data and template.
     */
    T load(Reader reader);

    /**
     * Load object from {@link InputStream} using this template.
     *
     * @param is {@link InputStream} with object data in yaml.
     *
     * @return object created from yaml data and template.
     */
    T load(InputStream is);

    /**
     * Load object from {@link File} using this template.
     *
     * @param file    {@link File} with object data in yaml.
     * @param charset encoding of file.
     *
     * @return object created from yaml data and template.
     *
     * @throws IOException if file can't be found or it can't be opened.
     */
    T load(File file, Charset charset) throws IOException;

    /**
     * Load object from {@link File} using this template. (UTF-8)
     *
     * @param file {@link File} with object data in yaml.
     *
     * @return object created from yaml data and template.
     *
     * @throws IOException if file can't be found or it can't be opened.
     * @see #load(File, Charset)
     */
    default T load(final File file) throws IOException
    {
        return this.load(file, StandardCharsets.UTF_8);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer             {@link Appendable} to use, all data will be added here.
     * @param object             object to dump. (will be represented as YAML string in writer)
     * @param level              current indent level.
     * @param writeComments      if comments should be added to node.
     * @param elementPlace       element place, used in many templates to check current style and choose valid format.
     * @param forceDefaultValues if true, all values will be set to default ones.
     * @param <E>                exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}
     */
    <E extends Appendable> E dump(E writer, T object, int level, boolean writeComments, ElementPlace elementPlace, boolean forceDefaultValues) throws IOException;

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer        {@link Appendable} to use, all data will be added here.
     * @param object        object to dump. (will be represented as YAML string in writer)
     * @param level         current indent level.
     * @param writeComments if comments should be added to node.
     * @param elementPlace  element place, used in many templates to check current style and choose valid format.
     * @param <E>           exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}
     */
    default <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments, final ElementPlace elementPlace) throws IOException
    {
        return this.dump(writer, object, level, writeComments, elementPlace, false);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer        {@link Appendable} to use, all data will be added here.
     * @param object        object to dump. (will be represented as YAML string in writer)
     * @param level         current indent level.
     * @param writeComments if comments should be added to node.
     * @param <E>           exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    default <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments) throws IOException
    {
        return this.dump(writer, object, level, writeComments, ElementPlace.NORMAL);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param object object to dump. (will be represented as YAML string in writer)
     * @param level  current indent level.
     * @param <E>    exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    default <E extends Appendable> E dump(final E writer, final T object, final int level) throws IOException
    {
        return this.dump(writer, object, level, true, ElementPlace.NORMAL);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer             {@link Appendable} to use, all data will be added here.
     * @param object             object to dump. (will be represented as YAML string in writer)
     * @param forceDefaultValues if true, all values will be set to default ones.
     * @param <E>                exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    default <E extends Appendable> E dump(final E writer, final T object, final boolean forceDefaultValues) throws IOException
    {
        return this.dump(writer, object, 0, true, ElementPlace.NORMAL, forceDefaultValues);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param object object to dump. (will be represented as YAML string in writer)
     * @param <E>    exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    default <E extends Appendable> E dump(final E writer, final T object) throws IOException
    {
        return this.dump(writer, object, 0, true, ElementPlace.NORMAL, false);
    }

    /**
     * dump object to selected {@link File} in YAML text format.
     *
     * @param file               file to use, it will be created if needed.
     * @param object             object to dump. (will be represented as YAML string in file)
     * @param charset            encoding of file. * @param forceDefaultValues if true, all values will be set to default ones.
     * @param forceDefaultValues if true, all values will be set to default ones.
     *
     * @throws IOException if file can't be created/edited.
     */
    void dump(File file, T object, Charset charset, boolean forceDefaultValues) throws IOException;

    /**
     * dump object to selected {@link File} in YAML text format.
     *
     * @param file    file to use, it will be created if needed.
     * @param object  object to dump. (will be represented as YAML string in file)
     * @param charset encoding of file. * @param forceDefaultValues if true, all values will be set to default ones.
     *
     * @throws IOException if file can't be created/edited.
     */
    default void dump(final File file, final T object, final Charset charset) throws IOException
    {
        this.dump(file, object, charset, false);
    }

    /**
     * dump object to selected {@link File} in YAML text format.
     *
     * @param file               file to use, it will be created if needed.
     * @param object             object to dump. (will be represented as YAML string in file)
     * @param forceDefaultValues if true, all values will be set to default ones.
     *
     * @throws IOException if file can't be created/edited.
     */
    default void dump(final File file, final T object, final boolean forceDefaultValues) throws IOException
    {
        this.dump(file, object, StandardCharsets.UTF_8, forceDefaultValues);
    }

    /**
     * dump object to selected {@link File} in YAML text format.
     *
     * @param file   file to use, it will be created if needed.
     * @param object object to dump. (will be represented as YAML string in file)
     *
     * @throws IOException if file can't be created/edited.
     */
    default void dump(final File file, final T object) throws IOException
    {
        this.dump(file, object, StandardCharsets.UTF_8, false);
    }
    /**
     * dump object to YAML string.
     *
     * @param object             object to dump. (will be represented as YAML string in writer)
     * @param forceDefaultValues if true, all values will be set to default ones.
     *
     * @return string yaml representation of given object.
     */
    String dumpAsString(T object, boolean forceDefaultValues);

    /**
     * dump object to YAML string.
     *
     * @param object object to dump. (will be represented as YAML string in writer)
     *
     * @return string yaml representation of given object.
     */
    default String dumpAsString(final T object)
    {
        return this.dumpAsString(object, false);
    }

    /**
     * Fill object with default values
     *
     * @param obj object to fill.
     *
     * @return this same - given - object.
     */
    T fillDefaults(T obj);

    /**
     * Append indent (2 spaces per level)
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param level  current indent level.
     *
     * @throws IOException from {@link Appendable}
     */
    static void spaces(final Appendable writer, final int level) throws IOException
    {
        if (level <= 0)
        {
            return;
        }
        for (int i = 0; i < level; i++)
        {
            writer.append("  ");
        }
    }

    /**
     * Append comment to given {@link Appendable}
     *
     * @param writer   {@link Appendable} to use, all data will be added here.
     * @param string   comment string to add, may be multi-line.
     * @param level    current indent level.
     * @param addSpace if true, additional space will be added before first # and no indent for first line.
     *
     * @throws IOException from {@link Appendable}
     */
    static void appendComment(final Appendable writer, final String string, final int level, final boolean addSpace) throws IOException
    {
        if (string != null)
        {
            final String[] split = StringUtils.split(string, '\n');
            boolean first = true;
            for (int i = 0, splitLength = split.length; i < splitLength; i++)
            {
                final String str = split[i];
                if (! first || ! addSpace)
                {
                    spaces(writer, level);
                    writer.append("# ");
                }
                else
                {
                    first = false;
                    writer.append(" # ");
                }
                writer.append(str);
                if ((i + 1) < splitLength)
                {
                    writer.append('\n');
                }
            }
        }
    }
}
