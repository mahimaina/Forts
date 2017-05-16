package de.mavecrit.pawars.spectator;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpectateScrollEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player scroller;
  private ArrayList<Player> scrollList;
  private ScrollDirection direction;

  public SpectateScrollEvent(Player scroller, ArrayList<Player> scrollList, ScrollDirection direction)
  {
    this.scroller = scroller;
    this.scrollList = scrollList;
    this.direction = direction;
  }

  public Player getPlayer() {
    return this.scroller;
  }

  public ArrayList<Player> getSpectateList() {
    return this.scrollList;
  }

  public ScrollDirection getDirection() {
    return this.direction;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}