package com.pettheanimals;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("pettheanimals")
public interface PetTheAnimalsConfig extends Config
{
    @ConfigItem(
            keyName = "enableChatMessage",
            name = "Enable Chat Message",
            description = "Also send the pet message to the chatbox."
    )
    default boolean enableChatMessage()
    {
        return true;
    }

    @ConfigItem(
            keyName = "enableOverheadText",
            name = "Enable Overhead Text",
            description = "Show the pet message above the pet."
    )
    default boolean enableOverheadText()
    {
        return true;
    }

    @ConfigItem(
            keyName = "petDistance",
            name = "Max Distance",
            description = "Maximum tiles away you can be to pet an NPC."
    )
    default int petDistance()
    {
        return 2;
    }

    @ConfigItem(
            keyName = "additionalNpcNames",
            name = "Additional NPCs",
            description = "Comma-separated list of extra NPC names that can be petted."
    )
    default String additionalNpcNames()
    {
        return "";
    }

    @ConfigSection(
            name = "Custom Pet Lines",
            description = "Messages that replace the built-in flavour text.",
            position = 98,
            closedByDefault = true
    )
    String customLinesSection = "customLinesSection";

    @ConfigItem(
            keyName = "customLine1",
            name = "Line 1",
            description = "Custom message used when petting.",
            section = customLinesSection,
            position = 0
    )
    default String customLine1()
    {
        return "";
    }

    @ConfigItem(
            keyName = "customLine2",
            name = "Line 2",
            description = "Custom message used when petting.",
            section = customLinesSection,
            position = 1
    )
    default String customLine2()
    {
        return "";
    }

    @ConfigItem(
            keyName = "customLine3",
            name = "Line 3",
            description = "Custom message used when petting.",
            section = customLinesSection,
            position = 2
    )
    default String customLine3()
    {
        return "";
    }

    @ConfigItem(
            keyName = "customLine4",
            name = "Line 4",
            description = "Custom message used when petting.",
            section = customLinesSection,
            position = 3
    )
    default String customLine4()
    {
        return "";
    }

    @ConfigItem(
            keyName = "customLine5",
            name = "Line 5",
            description = "Custom message used when petting.",
            section = customLinesSection,
            position = 4
    )
    default String customLine5()
    {
        return "";
    }
}

