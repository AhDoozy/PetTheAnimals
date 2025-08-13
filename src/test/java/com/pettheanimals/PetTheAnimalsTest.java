package com.pettheanimals;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PetTheAnimalsTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(com.pettheanimals.PetTheAnimalsPlugin.class);
		RuneLite.main(args);
	}
}