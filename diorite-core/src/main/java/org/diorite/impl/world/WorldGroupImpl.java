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

package org.diorite.impl.world;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.WorldGroup;

public class WorldGroupImpl implements WorldGroup
{
    private final String name;
    private final File   dir;
    private final File   playersDir;
    private final Set<WorldImpl> worlds = new HashSet<>(5);

    public WorldGroupImpl(final String name, final File dir)
    {
        this.name = name;
        this.dir = dir;
        this.playersDir = new File(dir, "_PlayerData_");
        this.playersDir.mkdirs();
    }

    public void addWorld(final WorldImpl world)
    {
        this.worlds.add(world);
    }

    @Override
    public Set<WorldImpl> getWorlds()
    {
        return this.worlds;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public File getDataFolder()
    {
        return this.dir;
    }

    @Override
    public File getPlayerDataFolder()
    {
        return this.playersDir;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("dir", this.dir).append("playersDir", this.playersDir).append("worlds", this.worlds).toString();
    }
}
