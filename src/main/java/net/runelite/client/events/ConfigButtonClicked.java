package net.runelite.client.events;

/**
 * Minimal stub of RuneLite's ConfigButtonClicked event.
 */
public class ConfigButtonClicked
{
    private final String group;
    private final String key;
    private final Object config;

    public ConfigButtonClicked(Object config, String group, String key)
    {
        this.config = config;
        this.group = group;
        this.key = key;
    }

    public Object getConfig()
    {
        return config;
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
