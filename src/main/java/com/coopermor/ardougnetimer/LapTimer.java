package com.coopermor.ardougnetimer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import net.runelite.client.ui.overlay.infobox.InfoBoxPriority;
import net.runelite.client.ui.overlay.infobox.Timer;

public class LapTimer extends Timer
{
	private final ArdougneCooldownTimerPlugin plugin;

	int ticks;

	LapTimer(Duration duration, BufferedImage image, ArdougneCooldownTimerPlugin plugin)
	{
		super(duration.toMillis(), ChronoUnit.MILLIS, image, plugin);
		this.plugin = plugin;
		setPriority(InfoBoxPriority.MED);
	}

	@Override
	public Color getTextColor()
	{
		return Color.WHITE;
	}
}
