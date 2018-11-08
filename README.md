The ToXiiC Community is a developing one pushing to keep some of its favorite games entertaining for those
who still wish to play it. Keeping old plugins up to date and creating new ones for those in need
at responible prices; if a payment is required at all.

If you interested in joining our communiy or just need help with one of our plugins
join our discord at http://discord.toxiic.net


---------------------------------------------------------------------------------
Placeholders (Must have PlaceholderAPI installed)
Messages.yml:
- %playerrank || Player's rank.
- %prestigecost || Price to prestige.
- %rankcost || Current rank price.
- %nextrank || Player's next rank.
- %lastrank || Last rank in the config file.
- %nextprestige || Next prestige.
- %player || Player's name.
- %balance || Player's balance.
- %prestige || Player's prestige.

(for setting other people's prestige/rank)
- %targetplayer || Player targetted.
- %targetplayerrank || Targetted player's rank.
- %targetplayerprestige || Targetted player's prestige.

Config.yml:
Rank commands:
- %player || Player username.
- %nextrank || Next rank (one they are ranking up to)
- %currentrank || Current rank
Prestige commands:
- %player || Player username
- %currentrank || Current rank

Chat:
- %rankformatted || Player's rank formatted.
- %displayname || Includes the player's pex group prefix, nick, and suffix
- %prestige || Player's prestige. (Does not work in the vanilla chat)
- %rank || Player's rank without being formatted.

PlaceholderAPI:
- %toxiicranks_rank%
- %toxiicranks_rankformatted%
- %toxiicranks_prestige%

---------------------------------------------------------------------------------
Permissions:
- toxiicxranks.chatcolor | Enables chat color.
- toxiicxranks.rank.set | Set your rank.
- toxiicxranks.rank.set.other | Set another player's rank.
- toxiicxranks.prestige.set | Set your prestige.
- toxiicxranks.prestige.set.other | Set another player's prestige.

Commands:
- /rankup | Ranks the player up if they have enough money.
- /prestige | Lets the player prestige (need to know if it needs to cost money or not).
- /prestige set <number> | Sets your prestige.
- /prestige set <player> <number> | Sets the player's prestige.
- /ranks | Lists ranks.
- /ranks set <character> | Sets your rank.
- /ranks set <player> <character> | Sets the player's rank.(edited)
