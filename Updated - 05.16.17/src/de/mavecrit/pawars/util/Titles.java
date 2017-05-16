package de.mavecrit.pawars.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Titles {
	/* Title packet */
	private Class<?> packetTitle;
	/* Title packet actions ENUM */
	private Class<?> packetActions;
	/* Chat serializer */
	private Class<?> nmsChatSerializer;
	private Class<?> chatBaseComponent;
	/* Title text and color */
	private String title = "";
	private ChatColor titleColor = ChatColor.WHITE;
	/* Subtitle text and color */
	private String subtitle = "";
	private ChatColor subtitleColor = ChatColor.WHITE;
	/* Title timings */
	private int fadeInTime = -1;
	private int stayTime = -1;
	private int fadeOutTime = -1;
	private boolean ticks = false;

	private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();

	/**
	 * Create a new 1.8 title
	 * 
	 * @param title
	 *            Title
	 */
	public Titles(String title) {
		this.title = title;
		loadClasses();
	}

	/**
	 * Create a new 1.8 title
	 * 
	 * @param title
	 *            Title text
	 * @param subtitle
	 *            Subtitle text
	 */
	public Titles(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
		loadClasses();
	}

	/**
	 * Copy 1.8 title
	 * 
	 * @param title
	 *            Title
	 */
	public Titles(Titles title) {
		// Copy title
		this.title = title.title;
		this.subtitle = title.subtitle;
		this.titleColor = title.titleColor;
		this.subtitleColor = title.subtitleColor;
		this.fadeInTime = title.fadeInTime;
		this.fadeOutTime = title.fadeOutTime;
		this.stayTime = title.stayTime;
		this.ticks = title.ticks;
		loadClasses();
	}

	/**
	 * Create a new 1.8 title
	 * 
	 * @param title
	 *            Title text
	 * @param subtitle
	 *            Subtitle text
	 * @param fadeInTime
	 *            Fade in time
	 * @param stayTime
	 *            Stay on screen time
	 * @param fadeOutTime
	 *            Fade out time
	 */
	public Titles(String title, String subtitle, int fadeInTime, int stayTime,
			int fadeOutTime) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
		loadClasses();
	}

	/**
	 * Load spigot and NMS classes
	 */
	private void loadClasses() {
		packetTitle = getNMSClass("PacketPlayOutTitle");
        packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
		chatBaseComponent = getNMSClass("IChatBaseComponent");
        nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
	}

	/**
	 * Set title text
	 * 
	 * @param title
	 *            Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get title text
	 * 
	 * @return Title text
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set subtitle text
	 * 
	 * @param subtitle
	 *            Subtitle text
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * Get subtitle text
	 * 
	 * @return Subtitle text
	 */
	public String getSubtitle() {
		return this.subtitle;
	}

	/**
	 * Set the title color
	 * 
	 * @param color
	 *            Chat color
	 */
	public void setTitleColor(ChatColor color) {
		this.titleColor = color;
	}

	/**
	 * Set the subtitle color
	 * 
	 * @param color
	 *            Chat color
	 */
	public void setSubtitleColor(ChatColor color) {
		this.subtitleColor = color;
	}

	/**
	 * Set title fade in time
	 * 
	 * @param time
	 *            Time
	 */
	public void setFadeInTime(int time) {
		this.fadeInTime = time;
	}

	/**
	 * Set title fade out time
	 * 
	 * @param time
	 *            Time
	 */
	public void setFadeOutTime(int time) {
		this.fadeOutTime = time;
	}

	/**
	 * Set title stay time
	 * 
	 * @param time
	 *            Time
	 */
	public void setStayTime(int time) {
		this.stayTime = time;
	}

	/**
	 * Set timings to ticks
	 */
	public void setTimingsToTicks() {
		ticks = true;
	}

	/**
	 * Set timings to seconds
	 */
	public void setTimingsToSeconds() {
		ticks = false;
	}

	/**
	 * Send the title to a player
	 * 
	 * @param player
	 *            Player
	 */
	  public static void sendTitle(Player player, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut)
	  {

	    try
	    {
	      if (title != null) {
	        title = ChatColor.translateAlternateColorCodes('&', title);
	        title = title.replaceAll("%player%", player.getDisplayName());

	        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke((Object)null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
	        sendPacket(player, titlePacket);

	        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object)null);
	        chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke((Object)null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
	        titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
	        sendPacket(player, titlePacket);
	      }

	      if (subtitle != null) {
	        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	        subtitle = subtitle.replaceAll("%player%", player.getDisplayName());

	        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
	        Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke((Object)null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);

	        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object)null);
	        chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke((Object)null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
	        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	      }
	    } catch (Exception var11) {
	      var11.printStackTrace();
	    }
	  }

	  public static void sendPacket(Player player, Object packet) {
		    try {
		      Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
		      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
		      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
	/**
	 * Broadcast the title to all players
	 */

	/**
	 * Clear the title
	 *
	 * @param player
	 *            Player
	 */
	public void clearTitle(Player player) {
		try {
			// Send timings first
			Object handle = getHandle(player);
			Object connection = getField(handle.getClass(), "playerConnection")
					.get(handle);
			Object[] actions = packetActions.getEnumConstants();
			Method sendPacket = getMethod(connection.getClass(), "sendPacket");
			Object packet = packetTitle.getConstructor(packetActions,
					chatBaseComponent).newInstance(actions[3], null);
			sendPacket.invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset the title settings
	 * 
	 * @param player
	 *            Player
	 */
	public void resetTitle(Player player) {
		try {
			// Send timings first
			Object handle = getHandle(player);
			Object connection = getField(handle.getClass(), "playerConnection")
					.get(handle);
			Object[] actions = packetActions.getEnumConstants();
			Method sendPacket = getMethod(connection.getClass(), "sendPacket");
			Object packet = packetTitle.getConstructor(packetActions,
					chatBaseComponent).newInstance(actions[4], null);
			sendPacket.invoke(connection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Class<?> getPrimitiveType(Class<?> clazz) {
		return CORRESPONDING_TYPES.containsKey(clazz) ? CORRESPONDING_TYPES
				.get(clazz) : clazz;
	}

	private Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
		int a = classes != null ? classes.length : 0;
		Class<?>[] types = new Class<?>[a];
		for (int i = 0; i < a; i++)
			types[i] = getPrimitiveType(classes[i]);
		return types;
	}

	private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
		if (a.length != o.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i]))
				return false;
		return true;
	}

	private Object getHandle(Object obj) {
		try {
			return getMethod("getHandle", obj.getClass()).invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Method getMethod(String name, Class<?> clazz,
			Class<?>... paramTypes) {
		Class<?>[] t = toPrimitiveTypeArray(paramTypes);
		for (Method m : clazz.getMethods()) {
			Class<?>[] types = toPrimitiveTypeArray(m.getParameterTypes());
			if (m.getName().equals(name) && equalsTypeArray(types, t))
				return m;
		}
		return null;
	}

	private static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1) + ".";
		return version;
	}

	private static Class<?> getNMSClass(String className) {
		String fullName = "net.minecraft.server." + getVersion() + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	private Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		for (Method m : clazz.getMethods())
			if (m.getName().equals(name)
					&& (args.length == 0 || ClassListEqual(args,
							m.getParameterTypes()))) {
				m.setAccessible(true);
				return m;
			}
		return null;
	}

	private boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++)
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		return equal;
	}
}