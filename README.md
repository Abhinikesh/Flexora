<div align="center">

<img src="screenshots/login.png" width="180"/>

# 💪 Flexora
### Smart Workout Tracker — Built for Athletes, Designed for Everyone

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-blueviolet?style=for-the-badge)
![Room](https://img.shields.io/badge/Database-Room-orange?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

*Track your workouts. Build your streaks. Own your progress.*

</div>

---

## 📸 Screenshots

<div align="center">

| Login | Home | Add Workout | Analytics | Profile |
|-------|------|-------------|-----------|---------|
| <img src="screenshots/login.png" width="160"/> | <img src="screenshots/home.png" width="160"/> | <img src="screenshots/add_workout.png" width="160"/> | <img src="screenshots/analytics.png" width="160"/> | <img src="screenshots/history.png" width="160"/> |

</div>

---

## 🚀 Features

### 🏠 Home Dashboard
- View total workouts completed
- Track daily streaks 🔥
- Monitor total time spent training
- Real-time activity updates via Flow

### ➕ Add Workout
- Add exercise name, sets, reps, and duration
- Input validation to prevent empty/invalid entries
- Data saved instantly to local Room Database

### 📊 Analytics
- Visual performance overview
- Average duration and total reps tracking
- Weekly workout frequency insights

### 🕒 History
- Browse all past workout sessions
- Real-time updates using Kotlin Flow
- Clean RecyclerView with smooth scrolling

### 👤 Profile
- Update username
- Change profile picture from gallery
- Persistent storage with SharedPreferences + Room

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| Architecture | MVVM (Model-View-ViewModel) |
| Database | Room Database |
| Async | Kotlin Coroutines + Flow |
| UI | XML + Material Design 3 |
| Navigation | Android Navigation Component |
| Dependency Injection | Hilt |

---

---

## ⚙️ How It Works

User adds workout
↓
Room Database stores it locally
↓
ViewModel observes DB via Kotlin Flow
↓
UI updates automatically in real-time
↓
Data persists even after app restart

---

## 🏃 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Minimum SDK: 24
- Target SDK: 34
- Kotlin 1.9+

### Installation
```bash
# Clone the repository
git clone https://github.com/Abhinikesh/Flexora.git

# Open in Android Studio
# Let Gradle sync complete

# Run on emulator or physical device
```

---

## 🔥 Key Highlights

- ✅ **Offline-First** — Works 100% without internet using Room DB
- ✅ **Real-Time UI** — Kotlin Flow ensures instant UI updates
- ✅ **Clean Architecture** — MVVM with Repository pattern
- ✅ **Production-Ready** — Scalable folder structure with DI
- ✅ **Modern UI** — Material Design 3 with dark theme

---

## 🚀 Upcoming Features

- [ ] Firebase cloud sync for backup
- [ ] AI-based workout suggestions
- [ ] Calorie tracking system
- [ ] Dynamic dark/light theme toggle
- [ ] Social sharing and leaderboard
- [ ] Progress charts with MPAndroidChart

---

## 🧑‍💻 Author

<div align="center">

**Abhinikesh Kumar**
Android Developer | Kotlin Enthusiast

[![GitHub](https://img.shields.io/badge/GitHub-Abhinikesh-181717?style=for-the-badge&logo=github)](https://github.com/Abhinikesh)

</div>

---

## 📄 License

---

<div align="center">

Made with ❤️ using Kotlin & Android Studio

⭐ Star this repo if you found it helpful!

</div>
