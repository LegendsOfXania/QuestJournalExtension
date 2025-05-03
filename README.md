# QuestJournalExtension

An extension for [TypeWriter](https://docs.typewritermc.com/) that adds a **quest journal** to your Minecraft server.  
Created by Legends of Xania.

## ✨ Features  

- **Quest Tracking**: Displays quests that are not started, in progress, or completed.  
- **Intuitive Interface**: A journal easily accessible with a new `OpenJournalMenu` action.  
- **Real-time Updates**: Automatic synchronization with your quests.  

## 📦 Installation  

1. Download **TypeWriter 0.8.0**. 
2. Download **QuestJournalExtension.jar**.  
3. Place it in the `plugins/TypeWriter/extensions/` folder.  
4. Restart your server.  

## ⚙️ Configuration  

Configuration is done in the `snippet.yml` file.

```sinppet.yml
journal:
  menu:
    main:
      title: Quests Journal
      buttons:
        active:
          name: <green>See active quests
          type: GREEN_BANNER
          model-data: 0
          place: 20
          lore:
          - <gray>Click <i>to open your quests</i>
          - <gray>and see your progress
        inactive:
          name: <yellow>See inactive quests
          type: YELLOW_BANNER
          model-data: 0
          place: 22
          lore:
          - <gray>Click to open your quests
          - <gray>and see your progress
        completed:
          name: <gray>See completed quests
          type: GRAY_BANNER
          model-data: 0
          place: 24
          lore:
          - <gray>Click to open your quests
          - <gray>and see your progress
    quest:
      title: Quests
      buttons:
        quest:
          type: WRITTEN_BOOK
          model-data: 0
          lore:
          - <red>No description available
        leave:
          title: <red>Leave
          type: BARRIER
          model-data: 0
          place: 49
          lore:
          - <red>Click to leave the menu
        next:
          title: <yellow>Next
          type: ARROW
          model-data: 0
          place: 53
          lore:
          - <red>Click to go to the next page
        previous:
          title: <yellow>Previous
          type: ARROW
          model-data: 0
          place: 45
          lore:
          - <red>Click to go to the previous page

```

## 📖 Credits

Made by [XayaTV](https://be.net/xayatv) for the Legends of Xania with the great help of [Gabber](https://github.com/gabber235) !
Don't forget to give the credits if you use the extension.
