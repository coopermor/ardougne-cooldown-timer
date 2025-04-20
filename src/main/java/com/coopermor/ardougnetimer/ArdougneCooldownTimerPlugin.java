package com.coopermor.ardougnetimer;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import static net.runelite.api.Skill.AGILITY;
import net.runelite.client.Notifier;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@Slf4j
@PluginDescriptor(
	name = "Ardougne Cooldown Timer"
)
public class ArdougneCooldownTimerPlugin extends Plugin
{
	private static final Duration LAP_COOLDOWN = Duration.ofMinutes(3);
	@Inject
	private Client client;
	@Inject
	private ItemManager itemManager;
	@Inject
	private InfoBoxManager infoBoxManager;
	@Inject
	private Notifier notifier;

	@Inject
	private ArdougneCooldownTimerConfig config;

	@Getter
	private Instant endTime;
	private boolean notifyOnce;

	@Override
	protected void startUp() throws Exception
	{
		removeTimer();
	}

	@Override
	protected void shutDown() throws Exception
	{
		removeTimer();
	}

	private void removeTimer()
	{
		infoBoxManager.removeIf(t -> t instanceof LapTimer);
		endTime = null;
		notifyOnce = false;
	}

	private void createTimer()
	{
		removeTimer();
		endTime = Instant.now().plus(LAP_COOLDOWN);
		BufferedImage image = itemManager.getImage(ItemID.GRACE);
		infoBoxManager.addInfoBox(new LapTimer(LAP_COOLDOWN, image, this));
		notifyOnce = true;
	}


	@Subscribe
	public void onStatChanged(StatChanged statChanged)
	{
		if (statChanged.getSkill() != AGILITY)
		{
			return;
		}
		if(client.getLocalPlayer().getWorldLocation().equals(new WorldPoint(2668, 3297, 0)))
		{
			createTimer();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if(Instant.now().isAfter(endTime))
		{
			if(notifyOnce )
			{
				notifier.notify(config.sendNotification(), "Ardougne lap cooldown expired.");
				notifyOnce = false;
			}
			removeTimer();
		}
	}

	@Provides
	ArdougneCooldownTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ArdougneCooldownTimerConfig.class);
	}
}
