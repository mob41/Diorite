package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemCommandImpl extends MainCommandImpl
{
    public SystemCommandImpl(final String name, final Collection<String> aliases, final byte priority)
    {
        super(name, aliases, priority);
    }

    public SystemCommandImpl(final String name, final Pattern pattern, final byte priority)
    {
        super(name, pattern, priority);
    }

    @Override
    public final String getFullName()
    {
        return "diorite" + COMMAND_PLUGIN_SEPARATOR + this.getName();
    }

    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith("diorite" + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(name.replace("diorite" + COMMAND_PLUGIN_SEPARATOR, ""));
        }
        else
        {
            return super.matcher(name);
        }
    }
}
