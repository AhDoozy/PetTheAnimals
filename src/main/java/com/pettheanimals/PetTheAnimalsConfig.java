package com.pettheanimals;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

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
		description = "Show the pet message above your player."
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
}
