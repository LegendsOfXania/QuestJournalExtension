# QuestJournalExtension

> [!Warning]
> This extension is depreciated. As a result, it will no longer receive new content/bugs fixes and wont be updated to Typewriter V1. Only updates to support new Typewriter's betas will take place.

An extension for [TypeWriter](https://docs.typewritermc.com/) that adds a **quest journal** to your Minecraft server.  
Created by Legends of Xania.

## ✨ Features  

- **Quest Tracking**: Displays quests that are not started, in progress, or completed.  
- **Intuitive Interface**: A journal easily accessible with a new `OpenJournalMenu` action.  
- **Real-time Updates**: Automatic synchronization with your quests.  

## 📦 Installation  

1. Download **TypeWriter**. 
2. Download **QuestJournalExtension**.  
3. Place it in the `plugins/TypeWriter/extensions/` folder.  
4. Restart your server.  

## ⚙️ Configuration  

Configuration is done in the `snippet.yml` file.

```sinppet.yml
journal:
  menu:
    main:
      title: Quests Journal
      # The number of slot in the menu, need to be a multiple of 9.
      rows: 54
      button:
        active:
          enabled: true
          name: Active Quests
          material: GREEN_BANNER
          lore: []
          slot: 20
          custom-model-data: 0
        inactive:
          enabled: true
          name: Inactive Quests
          material: RED_BANNER
          lore: []
          slot: 22
          custom-model-data: 0
        completed:
          enabled: true
          name: Completed Quests
          material: YELLOW_BANNER
          lore: []
          slot: 24
          custom-model-data: 0
    quest:
      title: Quests Journal
      # The number of slot in the menu, need to be a multiple of 9.
      rows: 54
      button:
        previous:
          name: Previous Page
          material: ARROW
          lore: []
          slot: 45
          custom-model-data: 0
        next:
          name: Next Page
          material: ARROW
          lore: []
          slot: 53
          custom-model-data: 0
        quest:
          material: BOOK
          # The lore of the quest button if the quest has not any description or objectives.
          lore: <red>No description available
          custom-model-data: 0
```

## 📖 Credits

Made by [XayaTV](https://be.net/xayatv) for the Legends of Xania with the great help of [Gabber](https://github.com/gabber235) !
Don't forget to give the credits if you use the extension.
