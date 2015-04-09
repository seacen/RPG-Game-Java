# RPG-Game-Java
A game about getting the Elixir to cure the king. Monsters need to be defeated along the way.

##Game Manual
It has been seven days since my father, King Lathran, came down with a terrible illness.
To date, the best eorts of our court physicians and magi have been in vain. The Elven
priestess, Lady Elvira suspects this is no ordinary sickness, but a magical curse.
If she is correct, then there is only one way to cure the king, and restore order to the land:
the Elixir of Life, a potion rumoured to have been created centuries ago, with the power to heal any
wound, and undo any curse.
Unfortunately, the potion is found in the Land of Shadows to the east, held by the Necromancer,
Draelic, who commands a vile army of the undead. He will not give it up easily. I only hope that
someone shall come forth, with the will to retrieve the elixir before all is lost...
| Prince Aldric
##Game overview
Shadow Quest is a role-playing game where one player battles against computer-controlled
enemies on a quest to retrieve the Elixir of Life. There are two areas in the game:
 The town, where friendly characters live. The player can walk around and talk to the
villagers. Elvira is an Elven priestess, who will heal the player if he or she is wounded.
 The wilderness, where evil creatures live. The player can freely explore these areas,
ght monsters, and collect useful items.

The goal of the game is to defeat the Necromancer Draelic, retrieve the Elixir of Life,
and return it to Prince Aldric in the town.

###The game map
In this game, the \world" is a two-dimensional grid of tiles. The player is able to
move about freely to explore the entire world (but of course, the player cannot walk
on trees, mountains, water, and other types of terrain).

##Units and stats
A unit is our term for a game character or person. There are three general unit types in the game:
 The player. Controlled by you; able to talk to villagers and ght monsters.

 Villagers. Friendly units; do nothing until the player talks to them.

 Monsters. Enemy units; either passive or aggressive. Passive monsters wander around the
map, and run away when attacked. Aggressive monsters try to kill the player.


Each unit's position in the world is stored as an (x; y) coordinate in pixels. This means that units
can stand at any point on the map, rather than being constrained to the tiles of the map grid. All
measurements are given in pixels.
Internally, a unit has no \size"; it is just a single point. When displaying a unit, the image should
be centered around the unit's position.
All units have a \HP" (short for \hit points") eld which is an integer value that determines their
current level of health. Units who are wounded in battle lose HP, and units who are \healed" gain
HP. Units who lose all of their HP are \dead". (See \Combat" later).

A stat (or attribute) is an integer value which determines how powerful a unit is in some way. A
unit's \stats" never change (except in the case of the player picking up items; see \Items" later).
 Max-HP { (Short for \maximum hit points"). This stat determines the maximum amount
of HP the unit has when fully healed.
 Damage { This stat determines the maximum amount of damage the unit can do when
attacking (see \Combat").
 Cooldown { This stat determines the minimum length of time the character has to wait
between attacks, in milliseconds (see \Combat").

Note that units' health is often shown as a percentage. This can be calculated as HP
Max-HP. For
example, if the player's HP is 15 and their Max-HP is 20, that player's \health" is at 75%.
As shown in Figure 1, all units except the player have a health/name bar 
oating above their heads.
This bar shows the unit's health percentage as a red bar on a black background, overlayed with
the unit's name in white text.
See the data le data/units.txt for details on the stats and locations of the units.
##Gameplay
This is a real-time game, meaning that events happen continuously, even if you do not press any
keys. For example, monsters may move or attack the player even if the player stands still.
The game takes place in frames. Much like a movie, many frames occur per second, so the animation
is smooth. On each frame:
1. All game units have a chance to move. Monsters move automatically towards or away from
the player if they are nearby. The player moves if the keyboard is being pressed.
2. The \camera" is updated so that it is centered around the player's position.
3. The entire screen is \rendered", so the display on-screen re
ects the new state of the world.
##Controls
The game is controlled entirely using the arrow keys on the keyboard. The left, right, up and
down keys move the player. Each frame, the player moves by a tiny amount in the direction of the
keys being pressed, if any. It is possible to move diagonally by holding down two keys at a time
(for example, move north-east by holding the up and right keys).
The player moves at a rate of one quarter of a pixel per millisecond, in each direction.
You can attack a monster simply by moving close to it (within 50 pixels) and holding A. Similarly,
you can talk to a villager by moving close (within 50 pixels) and pressing T.
