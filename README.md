# Pā
Pā is a new minigame for the Mahimaina Network.

NOTE. [sic] means ‘Set In Config’

OVERVIEW
Pā wars will be a minigame based around the attacking of each others pā (Māori fortifications). The players will utilise mob groups to attack the walls of the enemy Pā, breaching it to break the Pou (a pole that marks the centre of a Pā) in the enemies Pā. Once broken, enemy players cannot respawn, and the player must defeat all remaining enemy to win. Players will not be allowed to place standard blocks down. 

LOADS OF EGGS
The players start with 3 Mob spawn eggs in their inventory [sic] which are undroppable or unmovable (all different colors [sic]). When a player throws an egg (a chicken egg will be thrown) a group of mobs [sic] will spawn in the area that the egg lands in. The monster egg will then change into a ‘Ghast Mob Egg’ [sic], which is uncolored. This will indicate that the egg is empty, with a cooldown timer in the lore [sic].

The mob group will initially stand still and attack any enemy players, or enemy player mobs, that enter its attack range [sic]. 

A player can direct a group of their mobs to move to another place by having the empty egg selected and right clicking (or left maybe?) when their cross hair is on the block they wish that mob group to go. If they select a space that is unreachable (top of a tall tree) the mob will move to a reachable space closest to the vertical position of the selected space. 

The empty eggs will refill after a period of time and the player may ‘cast’ out another group of mobs again. There may only be one group of mobs from one egg, cast out on the field at one time. A player must wait the mob group is completely destroyed before they may throw an egg again 

MOBS AND MORE MOBS
Mob groups will have configurable speed, type, attack strength, and health [sic]. When an egg is selected, and that egg has a mob out in the field, then that Mob group will be highlighted, like with a spectral arrow.

The mobs can also spawn in custom groups of different types of mobs [sic] (eg. 3 Skeleton Knights, and 2 Skeleton Archers). 
Only some mob types will be able to attack the Pā wall blocks [sic]. Other mobs will have special abilities (eg. a Shaman mob group might throw healing potions at friendly mob groups that are under attack). Mobs in Mob groups should not bunch together but maintain a 1 - 2 block distance between each other.

SHOPS AND GIFTS
An NPC will act as a shop at the Pā of each team. The shop will offer more mob eggs, upgrades to their current eggs upgrades for the players equipment or upgrades to the Pā walls [sic]. The shop will also offer stronger mobs with more time, or captured ‘waypoints’. The shop will trade in Emeralds and diamonds that the player collects from waypoints and defeated enemies.

MAP AND WAYPOINTS
Players Pā will be pre-built, with shop NPC’s, Pou, and a wall made of a single type of block [sic]. The Pā wall block is configurable [sic] and should enclose each Pou from each team. The maps should have several waypoints that players can control (just by guarding them) while collecting the emeralds that spawn there. The waypoints should have an area around [sic] which no one can spawn mob groups in. The map should have 2 levels of waypoint, with the one type producing emeralds and another diamonds. The map should also have a boundary. Floating islands are okay, but mobs falling may be a problem. Better to just have a cliff face, wall or invisible barrier. The maps would be large enough to encourage a sole player to sneak up to a pā to breach it. 

COMMANDS
		The commands should follow the standard minigame command list including:
			/pa arena create <name>
			/pa arena remove <name>
			/pa arena wand (for marking out the arena boundaries)
			/pa arena enable <name>
			/pa arena disable <name>
			/pa <arenaname> setTeams <colors eg. &a, &c, &7>
			/pa <arenaname> setTeamsPlayers <number> (single is SOLO)
			/pa <arenaname> setSpawn <team eg. &7>
			/pa <arenaname> setPou <team eg. &7>
			/pa <arenaname> setShop <team eg. &7>
			/pa <arenaname> wayPoint <type [sic]>

The arena should only be allowed to be enabled if all the parameters are set. A list of parameters that need to be completed should be printed into chat if the admin tries to enable an incomplete arena.

There should also be some user commands within the game such as:
			/leave
			/replay	
