package com.coopermor.ardougnetimer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ArdougneCooldownTimerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ArdougneCooldownTimerPlugin.class);
		RuneLite.main(args);
	}
}