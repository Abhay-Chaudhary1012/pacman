<h1 align="center">
  <img src="src/assets/icon.png" width="100" alt="PacMan Icon"/><br/>
  PacMan — Java Edition
</h1>

<p align="center">
  A classic PacMan arcade game built from scratch in Java using Swing.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk"/>
  <img src="https://img.shields.io/badge/Platform-macOS%20%7C%20Windows%20%7C%20Linux-blue?style=flat-square"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square"/>
</p>

---

## 🎮 Gameplay

- Navigate PacMan through the maze eating all the dots
- Avoid the 4 ghosts — Blue, Red, Pink, and Orange
- Eat all pellets to advance to the next level
- You start with **3 lives** — lose them all and it's game over
- Press any arrow key after Game Over to restart

---

## 🕹️ Controls

| Key | Action |
|-----|--------|
| ⬆️ Arrow Up | Move Up |
| ⬇️ Arrow Down | Move Down |
| ⬅️ Arrow Left | Move Left |
| ➡️ Arrow Right | Move Right |
| Any arrow key (on Game Over) | Restart game |

---

## ⬇️ Download & Play

> **Requires Java 21 or later** — [Download Java](https://adoptium.net/)

1. Go to the [**Releases**](https://github.com/dev-kvt/pacman/releases) page
2. Download `PacMan.jar`
3. Run it:
   ```bash
   java -jar PacMan.jar
   ```
   Or simply **double-click** `PacMan.jar` if Java is set as the default handler.

---

## 🛠️ Build from Source

### Prerequisites
- Java 21+ JDK
- `make` (comes pre-installed on macOS/Linux; on Windows use WSL or Git Bash)

### Steps
```bash
# Clone the repo
git clone https://github.com/dev-kvt/pacman.git
cd pacman

# Compile and package
make jar

# Run
java -jar PacMan.jar
```

Or run directly without packaging:
```bash
make build
java -cp bin App
```

---

## 📁 Project Structure

```
PacMan/
├── src/
│   ├── App.java          # Entry point — sets up JFrame
│   ├── PacMan.java       # Game logic, rendering, input
│   └── assets/           # PNG sprites & icon
├── bin/                  # Compiled .class files (git-ignored)
├── Makefile              # Build & package commands
├── MANIFEST.MF           # JAR entry point declaration
└── .gitignore
```

---

## 🤖 Technical Highlights

- **HashSet** used for walls, foods and ghosts for O(1) lookup
- **javax.swing.Timer** drives the 20 FPS game loop
- AABB collision detection for walls, ghosts and food
- Ghost AI: random direction changes on wall collisions; forced upward escape from spawn room

---

## 📜 License

MIT — free to use, fork, and build upon.

---

<p align="center">Made with ☕ and Java by <a href="https://github.com/dev-kvt">Divyansh</a></p>
