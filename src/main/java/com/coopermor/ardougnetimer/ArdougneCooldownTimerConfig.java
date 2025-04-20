package com.coopermor.ardougnetimer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Notification;

@ConfigGroup("ardougnecooldowntimer")
public interface ArdougneCooldownTimerConfig extends Config
{
	@ConfigItem(
		keyName = "sendNotification",
		name = "Notify on timer expired",
		description = "Notify when the timer hits 0",
		position = 1
	)
	default Notification sendNotification()
	{
		return Notification.OFF;
	}
}
