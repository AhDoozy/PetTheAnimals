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
            name = "Pet Responses",
            description = "Customize animal dialogue.",
            position = 98,
            closedByDefault = true
    )
    String petLinesSection = "petLinesSection";

    @ConfigItem(
            keyName = "linesSummary",
            name = "Summary",
            description = "Overview of customised animals.",
            section = petLinesSection,
            position = 0
    )
    default String linesSummary()
    {
        return "Using defaults";
    }

    @ConfigItem(
            keyName = "editPetLines",
            name = "Edit Responses",
            description = "Open a popup to edit pet lines.",
            section = petLinesSection,
            position = 1
    )
    default net.runelite.client.config.Button editPetLines()
    {
        return new net.runelite.client.config.Button();
    }
}

