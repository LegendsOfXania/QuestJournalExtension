# QuestJournalExtension

An extension for [TypeWriter](https://docs.typewritermc.com/) that adds a **quest journal** to your Minecraft server.  
Created by Legends of Xania.

## ✨ Features  

- 📜 **Quest Tracking**: Displays quests that are not started, in progress, or completed.  
- 🔖 **Intuitive Interface**: A journal easily accessible with a new `OpenJournalMenu` action.  
- 🔄 **Real-time Updates**: Automatic synchronization with your quests.  

## 📦 Installation  

1. Download **TypeWriter 0.8.0-beta-151**. 
2. Download **QuestJournalExtension.jar**.  
3. Place it in the `plugins/TypeWriter/extensions/` folder.  
4. Restart your server.  

## ⚙️ Configuration  

Configuration is done in the `snippet.yml` file.

```sinppet.yml
journal:
  menu:
    main:
# The name of the menu (Support MiniMessage and PlaceholderAPI)
      title: Journal de quêtes
      buttons:
        active:
# The name of the item, support MiniMessage (yes, I'M FRENCH)
          name: <green>Voir les quêtes actives
# The type of the item
          type: GREEN_BANNER
# The Custom Model Data of the item. Set to 0 if it doesn't have any
          model-data: 0
# The place of the item in the Menu
          place: 20
        inactive:
          name: <yellow>Voir les quêtes inactives
          type: YELLOW_BANNER
          model-data: 0
          place: 22
        completed:
          name: <gray>Voir les quêtes complétées
          type: GRAY_BANNER
          model-data: 0
          place: 24
    active:
      title: Quêtes Actives
    inactive:
      title: Quêtes Inactives
    completed:
      title: Quêtes Complétées
    quest:
      buttons:
        quest:
          type: WRITTEN_BOOK
          model-data: 0
        leave:
          title: <red>Retour
          type: BARRIER
          model-data: 0
          place: 49
        next:
          title: <yellow>Suivant
          type: BRICK
          model-data: 10006
          place: 53
        previous:
          title: <yellow>Précédent
          type: ARROW
          model-data: 0
          place: 45
```

## 📖 Credits

Made by [XayaTV](https://be.net/xayatv) for the Legends of Xania with the great help of [Gabber](https://github.com/gabber235) !
Don't forget to give the credits if you use the extension.
