# 2D RPG Sandbox Game - Design Document

## Working Title
*[To Be Determined]*

**Genre:** 2D Sandbox RPG / Survival Crafting  
**Inspirations:** Minicraft + Minecraft + Stardew Valley

---

## High Concept
A charming 2D pixel-art sandbox where players survive, build, and thrive in a procedurally generated world. Blend the creative freedom of Minecraft, the cozy community-building of Stardew Valley, and the compact survival loop of Minicraft into a unique exploration and crafting experience.

---

## Core Pillars

### 1. **Explore**
- Procedurally generated world with diverse biomes (forests, deserts, tundra, caves, etc.)
- Hidden secrets: dungeons, ruins, treasure chests, rare resources
- Day-night cycle affecting gameplay (certain mobs spawn at night, some fish only appear during specific times)
- Dynamic weather system influencing farming, fishing, and exploration

### 2. **Gather & Craft**
- Resource collection: wood, stone, ores, plants, food
- Tiered crafting system: basic wooden tools → advanced metal equipment
- Recipe discovery through exploration and experimentation
- Inventory management with limited slots (encourages planning and prioritization)

### 3. **Build**
- From resources gathered, players construct worktables, stoves, and more
- No building of houses or structures yet, but players can create functional items (stoves, worktables for crafting, chests for storage)

### 4. **Survive & Thrive**
- Health and hunger systems (must eat to stay alive, health depletes from hunger)
- Combat against hostile animal mobs (wolves, bears), and cave monsters (spiders)
- Weapon variety: pickaxes (used for mining and combat), axes (used for chopping trees and combat), rakes (used for farming and combat)
- Tool durability: tools wear down with use, encouraging crafting and resource management
- Death: lose all items and die - no respawning, you die forever (permadeath) - encourages careful play and risk management
- Character progression: gain experience from combat, crafting, and exploration = contributes to stats

### 5. **Cultivate**
- **Farming:** Plant seeds, water crops, harvest yields, seasonal crops
- **Animal Husbandry:** Raise chickens, cows, sheep for eggs, milk, wool
- **Fishing:** Catch different fish species based on biome, time, and weather
- Economic loop: sell products, buy rare seeds and blueprints

---

## Core Gameplay Loop

**Early Game (Survival Focus):**
1. Spawn in random biome
2. Gather basic resources (wood, stone, berries)
3. Craft primitive tools (wooden axe, pickaxe, rake)
4. Build a worktable and storage chest
5. Hunt animals for food, defend against night mobs
6. Focus on not dying—permadeath means every decision matters

**Mid Game (Expansion Focus):**
1. Explore new biomes, discover caves and dungeons
2. Mine ores, craft upgraded tools with better durability
3. Establish farming area, plant crops, raise animals
4. Build functional items: stoves for cooking, advanced worktables for crafting
5. Unlock advanced recipes through exploration

**Late Game (Mastery Focus):**
1. Challenge difficult caves with spider infestations
2. Master all crafting tiers, create the best tools
3. Establish efficient farming and animal husbandry systems
4. Uncover the world's deepest secrets and rare resources
5. Achieve complete self-sufficiency while managing permadeath risk

---

## Key Systems Breakdown

### Resource Gathering
- **Trees/Bushes:** Chop for wood (with axe), shake for berries/fruits
- **Mining:** Dig underground for coal, iron, gold, gems (with pickaxe)
- **Foraging:** Collect wild plants, mushrooms, flowers
- **Hunting:** Kill animals for meat, hide, bones (wolves, bears are hostile)

### Crafting Tiers
1. **Wood Tier:** Basic tools (wooden pickaxe, axe, rake), worktables, storage chests
2. **Stone Tier:** Improved durability, stone tools, stoves for cooking
3. **Iron Tier:** Efficient tools with longer durability, advanced worktables
4. **Gold/Diamond Tier:** Specialized equipment, highest durability, rare crafting materials

### Combat System
- **Multi-purpose tools as weapons:** Pickaxes (mining/combat), axes (chopping/combat), rakes (farming/combat)
- **Tool durability in combat:** Using tools as weapons degrades them faster
- **Enemy variety:** 
  - Hostile animals: wolves, bears (surface biomes)
  - Cave monsters: spiders (underground)
- **XP system:** Gain experience from combat, crafting, and exploration
- **Permadeath:** Death is permanent—lose all items and character progress, encouraging careful strategic play

### Farming & Food
- **Crops:** Wheat, carrots, tomatoes, etc. (growth time varies)
- **Irrigation:** Water crops manually using tools
- **Seasons:** Some crops only grow in specific seasons
- **Cooking:** Use stoves to combine ingredients for food that restores health and hunger
- **Hunger system:** Must eat regularly to survive; health depletes when starving

### Progression Systems
- **Character Stats:** Gain experience from all activities (combat, crafting, exploration)
- **Stat Growth:** Experience contributes to stat improvements (strength, endurance, efficiency)
- **Tool Upgrades:** Progress from wood → stone → iron → gold/diamond tiers
- **Tool Durability Management:** Tools wear down with use, requiring constant crafting and resource management
- **Recipe Unlocks:** Discover new recipes through exploration and experimentation

---

## World & Atmosphere

### Biomes
- **Forest:** Dense trees, wildlife (wolves), mushrooms, abundant wood
- **Plains:** Open fields, ideal for farming, moderate animal spawns
- **Desert:** Cacti, scarce water, heat challenges, unique resources
- **Tundra:** Snow-covered, hardy crops, ice fishing, cold-resistant animals
- **Mountains:** High altitude, rare ores, dangerous terrain, bears
- **Caves/Underground:** Multi-layered mining, ore veins, spider infestations

### Dynamic Elements
- **Day/Night Cycle:** 20-minute real-time cycle (adjustable)
  - Day: Safe exploration, farming, building
  - Night: Increased hostile mob spawns, higher danger
- **Weather:** Rain (boosts crops), storms (dangerous), snow, fog
- **Seasons:** Spring, Summer, Fall, Winter (affects farming and biome aesthetics)

### NPCs & Creatures
- **Passive Animals:** Chickens, cows, sheep, pigs, fish
- **Hostile Surface Mobs:** Wolves, bears (spawn in specific biomes)
- **Cave Monsters:** Spiders (underground threat)
- **Future NPCs:** Merchants, craftsmen, quest givers (post-launch)

---

## Technical Stack

### Phase 1: Prototype (Current)
- **Language:** Java
- **Framework:** Swing (UI, rendering, input)
- **Game Loop:** Delta time with `System.nanoTime()`
- **Art:** Aseprite (pixel art sprites & tiles)
- **Audio:** Bfxr (SFX), Bosca Ceoil / LMMS (music)
- **Fonts:** Pixel fonts (dafont, 1001fonts)
- **Version Control:** Git + GitHub
- **Distribution:** itch.io (.jar file)

### Phase 2: Full Release (Future)
- **Framework Upgrade:** LibGDX (better performance, cross-platform)
- **Platforms:** Windows, macOS, Linux, Android, iOS
- **Distribution:** itch.io, Steam (if greenlit), mobile app stores
- **Optimization:** Texture atlases, efficient rendering, save/load systems

---

## Unique Selling Points

1. **Handcrafted Procedural Generation:** No two worlds the same, but always playable and balanced
2. **High-Stakes Permadeath:** Every decision matters—death means starting over completely
3. **Multi-Purpose Tools:** Pickaxes, axes, and rakes serve dual roles (gathering + combat)
4. **Functional Building Focus:** Create useful items (worktables, stoves, chests) rather than decorative structures
5. **Meaningful Progression:** Every action contributes to character stats and survival capability
6. **Rich Pixel Art Style:** Nostalgic 16-bit aesthetic with modern polish

---

## Development Roadmap

### Milestone 1: Core Prototype ✓
- Basic movement and controls
- Tile-based world generation
- Resource gathering (trees with axes, rocks with pickaxes)
- Simple crafting menu (worktable system)
- Day/night cycle

### Milestone 2: Survival Mechanics
- Health and hunger systems
- Basic combat (multi-purpose tools: pickaxe, axe, rake)
- Tool durability and degradation
- Hostile mob AI (wolves, bears, spiders)
- Permadeath system and game over screen

### Milestone 3: Building & Crafting
- Worktable construction and functionality
- Stove creation for cooking
- Storage chest system
- Expanded crafting recipes (wood → stone → iron → gold/diamond)
- Tool tier progression
- Inventory UI improvements

### Milestone 4: Farming & Animals
- Crop planting and growth (using rake)
- Manual watering system
- Animal spawning and interactions (chickens, cows, sheep)
- Fishing mechanics (biome-dependent)
- Cooking system (stove-based food preparation)

### Milestone 5: Exploration & Content
- Multiple biomes (forest, plains, desert, tundra, mountains)
- Cave systems and underground mining
- Spider cave encounters
- Rare resource locations
- Hidden secrets and treasure

### Milestone 6: Polish & Progression
- Character stat system and XP tracking
- Experience from combat, crafting, exploration
- Music and sound effects
- Death screen with permadeath messaging
- Balance tuning (tool durability, hunger rate, combat difficulty)

### Milestone 7: Release Preparation
- Bug fixing and optimization
- Tutorial and onboarding (explaining permadeath)
- Settings menu (controls, audio, graphics)
- Playtesting and balancing
- Marketing materials (trailer emphasizing permadeath stakes, screenshots)

---

## Open Questions & Future Ideas

**Needs Decision:**
- Game title (brainstorm list)
- Exact stat progression formula
- Balance: How harsh should permadeath feel? (encouraging vs. frustrating)
- Should there be limited respawn items? (phoenix feathers, totems)
- Multiplayer? (co-op permadeath could be interesting)

**Post-Launch Expansions:**
- New biomes (jungle, swamp, ocean)
- Additional cave monsters and surface threats
- Seasonal events
- Rare equipment with special abilities
- NPC merchants and trading system
- Structure building (unlockable after certain milestones)
- Boss encounters in deep caves

---

## Design Philosophy Notes

**Permadeath Justification:**
- Creates tension and meaningful choices
- Makes survival feel earned, not guaranteed
- Encourages careful resource management
- Rewards planning and strategy over recklessness
- Each playthrough becomes a unique story

**Multi-Purpose Tools:**
- Streamlines inventory management
- Every tool matters in multiple contexts
- Durability loss from combat creates strategic decisions (save tool for gathering or use in emergency fight?)
- Ties progression directly to crafting tiers

**Functional Building Only (Initial Scope):**
- Keeps focus on survival and exploration
- Prevents feature creep early in development
- Worktables, stoves, and chests provide clear gameplay benefits
- Can expand to decorative building in future updates if desired

---

## Closing Thoughts
This game emphasizes meaningful survival through permadeath, multi-purpose tool usage, and functional crafting. The permadeath mechanic raises the stakes of every encounter and decision, while the simplified building system keeps the focus on exploration, resource management, and character progression. Start with a tight prototype focused on the core survival loop, then expand biomes and content based on playtesting feedback.

**Next Steps:** Implement tool durability system, refine combat with multi-purpose tools, test permadeath flow, and create core biome assets for Phase 1 prototype.