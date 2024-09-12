EnglishWordQuest: Learn English the Fun Way!

EnglishWordQuest is a Kotlin-based mobile app that transforms learning English into an engaging and fun experience by combining word learning with a turn-based JRPG battle system. Instead of traditional attacks, players defeat Pokémon by solving word puzzles, making language learning exciting and interactive!

Gameplay Features:
Pokémon Battles with a Twist: Fight Pokémon downloaded from the PokéAPI using Retrofit. Instead of using conventional attacks, engage in word-based puzzles to defeat your opponents.
Character Progression: Earn experience points (EXP) and in-game currency by winning battles. Upgrade your character and purchase equipment to enhance your abilities.
Turn-based Combat: Classic JRPG mechanics fused with language puzzles, turning learning English into a strategic adventure.
Tech Stack:
Kotlin: The app is developed natively for Android in Kotlin.
MVVM Architecture: Following the Model-View-ViewModel pattern for a clean, maintainable, and scalable structure.
Hilt (Dependency Injection): Leveraging Hilt for managing dependencies efficiently.
Room: Local database implementation for storing game progress and user data.
Jetpack Compose: Modern UI development with a declarative approach for building responsive and sleek interfaces.
Coil: Image loading library for handling Pokémon images efficiently.
API Integration:
Using PokéAPI to dynamically retrieve Pokémon data for each battle, ensuring variety and unpredictability in gameplay.
