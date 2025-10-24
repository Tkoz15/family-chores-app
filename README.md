# Family Chores App

A simple Android application for tracking children's chores, photo proof of completion, and allowance management.

## Features

### For Children
- View available chores with rewards
- Start chores and mark as complete
- Take photos as proof of completion
- Track earnings and view chore history

### For Parents  
- Review completed chores with photos
- Approve or reject chore completions
- Manage available chores (add/edit/delete)
- Track children's earnings and pay out balances

## Quick Start

### Prerequisites
- Android Studio (latest stable version)
- Android device or emulator (API level 24+)

### Installation
1. Open the project in Android Studio
2. Let Gradle sync complete
3. Connect your Android device or start an emulator
4. Click "Run" to build and install the app

### Default Setup
The app comes pre-configured with:
- **Parent PIN:** 1234
- **Sample Children:** Sarah, Michael
- **Sample Chores:** Clean Bedroom ($5.00), Take Out Trash ($2.00), etc.

## Usage

### Getting Started
1. Launch the app
2. Select a child user or "Parent" from the main screen
3. If selecting Parent, enter PIN: **1234**

### Child Flow
1. Select your name from the user selection screen
2. Browse available chores
3. Tap "START" on a chore to begin
4. Complete the chore in real life
5. Take a photo as proof when done
6. Wait for parent approval
7. Check your earnings in the "Earnings" section

### Parent Flow
1. Select "Parent" and enter PIN (1234)
2. View the dashboard with children overview
3. **Review Chores:** See pending completions with photos
   - Tap "Approve" to add money to child's balance
   - Tap "Reject" to decline the completion
4. **Manage Chores:** Add, edit, or delete available chores
5. **Pay Out:** Reset a child's balance to $0 after paying them

## Technical Details

### Architecture
- **MVVM Pattern** with Repository
- **Room Database** for local storage
- **CameraX** for photo capture
- **Navigation Component** for screen flow
- **Material Design 3** components

### Database
- **Users:** Children and parents with balances
- **Chores:** Available tasks with rewards
- **Completions:** Track chore progress and status

### Photos
- Stored locally in app's private directory
- Automatically compressed for storage efficiency
- Displayed in review screens for parent approval

## Customization

### Adding Children
Currently hardcoded. To add more children, modify the `DatabaseCallback.populateDatabase()` method in `ChoresDatabase.kt`:

```kotlin
userDao.insertUser(
    UserEntity(
        userName = "New Child Name",
        userType = UserType.CHILD
    )
)
```

### Changing Parent PIN
Modify the default parent user in `DatabaseCallback.populateDatabase()`:

```kotlin
userDao.insertUser(
    UserEntity(
        userName = "Parent",
        userType = UserType.PARENT,
        pin = "your-new-pin"
    )
)
```

### Adding Default Chores
Add more chores in the `populateDatabase()` method:

```kotlin
choreDao.insertChore(ChoreEntity(name = "New Chore", reward = 3.50))
```

## File Structure

```
app/src/main/java/com/family/choresapp/
├── data/
│   ├── database/
│   │   ├── entities/        # Room entities
│   │   ├── dao/            # Database access objects
│   │   └── ChoresDatabase.kt
│   └── repository/         # Data repository
├── ui/
│   ├── child/             # Child user interface
│   ├── parent/            # Parent user interface
│   └── common/            # Shared UI components
├── viewmodel/             # ViewModels for UI state
└── MainActivity.kt
```

## Building for Release

1. In Android Studio: **Build → Generate Signed Bundle / APK**
2. Choose **APK**
3. Create a keystore or use existing one
4. Select **release** build variant
5. Build APK

## Installation on Device

### Via USB
1. Enable "Developer Options" on your Android device
2. Enable "USB Debugging"
3. Connect device to computer
4. Install APK via Android Studio or ADB

### Via File Transfer
1. Enable "Install Unknown Apps" in device settings
2. Transfer APK file to device (email, cloud storage, etc.)
3. Open APK file on device to install

## Troubleshooting

### Camera Not Working
- Ensure camera permissions are granted
- Check that device has a camera
- Restart the app if camera preview is black

### Database Issues
- Clear app data to reset database
- Reinstall app for fresh start

### Navigation Issues
- Use back button to return to previous screens
- Restart app if navigation gets stuck

## Future Enhancements

Potential features for future versions:
- Recurring/scheduled chores
- Push notifications for pending reviews
- Cloud backup and sync
- Multiple family support
- Gamification with achievements
- Export data to CSV/PDF

## Support

This is a family-use application. For technical issues:
1. Check the troubleshooting section above
2. Clear app data and restart
3. Reinstall the application

## License

This project is for personal/family use. Feel free to modify and customize for your family's needs.