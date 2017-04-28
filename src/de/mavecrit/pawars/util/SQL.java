package de.mavecrit.pawars.util;


import java.sql.ResultSet;
import java.sql.SQLException;

import de.mavecrit.pawars.Main;



public class SQL
{
  public static boolean playerExists(String uuid)
  {
    try
    {
      ResultSet rs = MySQL.getResult("SELECT * FROM minez WHERE UUID='" + uuid + "'");
      if (rs.next()) {
        return rs.getString("UUID") != null;
      }
      return false;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void createPlayer(String uuid)
  {
    if (!playerExists(uuid))
      MySQL.update("INSERT INTO minez (UUID, CURRENCY) VALUES ('" + uuid + "', '" +Main.getPlugin().getConfig().getInt("General.Currency_Start")+"');");
  }
  
  //GET
  public static Integer getCurrency(String uuid)
  {
    Integer i = Integer.valueOf(0);

    if (playerExists(uuid)) {
      try {
        ResultSet rs = MySQL.getResult("SELECT * FROM minez WHERE UUID='" + uuid + "'");
        if ((rs.next()) && (Integer.valueOf(rs.getInt("CURRENCY")) == null));
        i = Integer.valueOf(rs.getInt("CURRENCY"));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      createPlayer(uuid);
      getCurrency(uuid);
    }
    return i;
  }
  
  
  //SET
  public static void setCurrency(String uuid, Integer curr) {
	    if (playerExists(uuid)) {
	      MySQL.update("UPDATE minez SET 	CURRENCY='" + curr + "' WHERE UUID='" + uuid + "'");
	    } else {
	      createPlayer(uuid);
	      setCurrency(uuid, curr);
	    }
	  }
  
  //ADD
  public static void addCurrency(String uuid, Integer curr)
  {
    if (playerExists(uuid)) {
      setCurrency(uuid, Integer.valueOf(getCurrency(uuid).intValue() + curr.intValue()));
    } else {
      createPlayer(uuid);
      addCurrency(uuid, curr);
    }
  }

  //REMOVE
  public static void removeCurrency(String uuid, Integer curr) {
    if (playerExists(uuid)) {
      setCurrency(uuid, Integer.valueOf(getCurrency(uuid).intValue() - curr.intValue()));
    } else {
      createPlayer(uuid);
      removeCurrency(uuid, curr);
    }
  }
}