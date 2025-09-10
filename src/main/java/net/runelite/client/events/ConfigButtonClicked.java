package net.runelite.client.events;

/**
 * Minimal stub event used for tests.
 */
public class ConfigButtonClicked
{
    private final String group;
    private final String key;

    public ConfigButtonClicked(String group, String key)
    {
        this.group = group;
        this.key = key;
    }

    public String getGroup()
    {
        return group;
    }

    public String getKey()
    {
        return key;
    }
}

