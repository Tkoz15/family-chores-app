# Chores App - Requirements Document

## Overview
An Android application designed to help children track their chores, upload proof of completion, and monitor their earned allowance. Parents can review completed chores and manage allowance payments.

**Technical Stack:** Kotlin-based Android application with modern Android development practices and a simple, intuitive user interface.

---

## Technical Specifications

### Development Environment
- **IDE:** Android Studio (Latest Stable Version)
- **Language:** Kotlin 1.9+
- **Minimum Android SDK:** API 24 (Android 7.0 Nougat)
- **Target Android SDK:** API 34 (Android 14)
- **Build System:** Gradle with Kotlin DSL

### Architecture Pattern
**MVVM (Model-View-ViewModel)** - Recommended for Android/Kotlin apps

- **Model:** Database entities and repository classes
- **View:** Activities and Fragments with XML layouts
- **ViewModel:** Business logic and UI state management

**Benefits:**
- Separates UI from business logic
- Easier testing and maintenance
- Lifecycle-aware components
- Recommended by Google for Android development

### Core Technology Stack

#### Database Layer
- **Room Database** (Kotlin-friendly SQLite wrapper)
  - Type-safe database access
  - Compile-time verification of SQL queries
  - Automatic DAO (Data Access Object) generation
  - Built-in support for LiveData and Coroutines
  
#### Dependency Injection
- **Hilt** (Dagger wrapper for Android)
  - Simplifies dependency management
  - Reduces boilerplate code
  - Easy testing and modularity

#### Asynchronous Programming
- **Kotlin Coroutines**
  - Clean, readable asynchronous code
  - Native Kotlin support
  - Better than callbacks or RxJava for Android

#### Image Handling
- **Coil** (Kotlin-first image loading library)
  - Fast and lightweight
  - Coroutine support
  - Easy caching and transformations
  
#### UI Components
- **Material Design 3** (Material You)
  - Modern Android design system
  - Pre-built UI components
  - Consistent look and feel
  
- **XML Layouts** (Traditional approach - simpler to learn)
  - Visual layout editor in Android Studio
  - Easier for beginners than Jetpack Compose
  - Mature ecosystem with many examples

#### Navigation
- **Jetpack Navigation Component**
  - Type-safe navigation between screens
  - Handles back stack automatically
  - Deep linking support

#### Camera Integration
- **CameraX**
  - Simplified camera API
  - Works across all Android devices
  - Easy image capture and preview

### Kotlin-Specific Features to Utilize

1. **Data Classes** - For database entities
   ```kotlin
   data class Chore(
       val id: Int,
       val name: String,
       val reward: Double
   )
   ```

2. **Null Safety** - Prevents null pointer exceptions
   ```kotlin
   var userName: String? = null  // Nullable
   var userId: String = ""       // Non-nullable
   ```

3. **Extension Functions** - Add functionality to existing classes
   ```kotlin
   fun Double.toCurrency(): String {
       return "$${"%.2f".format(this)}"
   }
   ```

4. **Sealed Classes** - For chore status management
   ```kotlin
   sealed class ChoreStatus {
       object Available : ChoreStatus()
       object InProgress : ChoreStatus()
       object Completed : ChoreStatus()
       object Approved : ChoreStatus()
   }
   ```

5. **Coroutines & Flow** - For reactive data streams
   ```kotlin
   viewModelScope.launch {
       choreRepository.getAllChores().collect { chores ->
           // Update UI
       }
   }
   ```

### Project Structure
```
app/
├── data/
│   ├── database/
│   │   ├── ChoresDatabase.kt
│   │   ├── entities/
│   │   │   ├── ChoreEntity.kt
│   │   │   ├── UserEntity.kt
│   │   │   └── ChoreCompletionEntity.kt
│   │   └── dao/
│   │       ├── ChoreDao.kt
│   │       ├── UserDao.kt
│   │       └── ChoreCompletionDao.kt
│   └── repository/
│       ├── ChoreRepository.kt
│       ├── UserRepository.kt
│       └── CompletionRepository.kt
├── ui/
│   ├── child/
│   │   ├── ChoreListFragment.kt
│   │   ├── ChoreDetailFragment.kt
│   │   └── EarningsFragment.kt
│   ├── parent/
│   │   ├── DashboardFragment.kt
│   │   ├── ReviewChoresFragment.kt
│   │   └── ManageChoresFragment.kt
│   └── common/
│       ├── LoginFragment.kt
│       └── CameraFragment.kt
├── viewmodel/
│   ├── ChoreViewModel.kt
│   ├── UserViewModel.kt
│   └── CompletionViewModel.kt
├── util/
│   ├── ImageUtils.kt
│   ├── CurrencyFormatter.kt
│   └── Constants.kt
└── di/
    ├── DatabaseModule.kt
    └── RepositoryModule.kt
```

### Required Dependencies (build.gradle.kts)
```kotlin
dependencies {
    // Core Android & Kotlin
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // Material Design
    implementation("com.google.android.material:material:1.11.0")
    
    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    
    // Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    
    // Image Loading (Coil)
    implementation("io.coil-kt:coil:2.5.0")
}
```

---

## Simple Interface Design Guidelines

### Design Principles
1. **Minimize Clicks** - Any action should take 3 taps or less
2. **Large Touch Targets** - Minimum 48dp for buttons (Google recommendation)
3. **Clear Visual Hierarchy** - Most important items are largest/brightest
4. **Consistent Navigation** - Same navigation pattern throughout app
5. **Immediate Feedback** - Visual confirmation for every action

### Color Scheme (Simple & Kid-Friendly)
- **Primary Color:** Bright, cheerful blue (#2196F3)
- **Secondary Color:** Encouraging green (#4CAF50) for positive actions
- **Warning Color:** Soft orange (#FF9800) for pending/in-progress
- **Background:** White or very light gray (#FAFAFA)
- **Text:** Dark gray (#212121) for readability

### Typography
- **Headers:** Bold, 24sp minimum
- **Body Text:** Regular, 16sp minimum
- **Button Text:** Bold, 18sp minimum
- **Use System Fonts:** Roboto (default Android font)

### Icon Usage
- **Material Icons** - Free, recognizable, consistent
- Examples:
  - Checkmark for completed
  - Star for rewards
  - Camera for photo capture
  - Dollar sign for earnings

### Screen Layouts

#### Child View - Main Screen
```
┌─────────────────────────┐
│  👤 [Child Name]        │
│  💰 Earned: $25.50      │
├─────────────────────────┤
│  Available Chores       │
│                         │
│  ┌───────────────────┐  │
│  │ 🧹 Clean Bedroom  │  │
│  │ Reward: $5.00     │  │
│  │ [START] Button    │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │ 🗑️ Take Out Trash │  │
│  │ Reward: $2.00     │  │
│  │ [START] Button    │  │
│  └───────────────────┘  │
│                         │
│  [My Chores] [Earnings] │
└─────────────────────────┘
```

#### Parent View - Dashboard
```
┌─────────────────────────┐
│  Parent Dashboard       │
├─────────────────────────┤
│  Children Overview      │
│                         │
│  Sarah: $15.50 ✓       │
│  Michael: $8.00 ⏳      │
│                         │
│  ┌───────────────────┐  │
│  │ 📋 Review Chores  │  │
│  │ (3 pending)       │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │ ⚙️ Manage Chores   │  │
│  └───────────────────┘  │
│                         │
│  ┌───────────────────┐  │
│  │ 👥 Manage Kids    │  │
│  └───────────────────┘  │
└─────────────────────────┘
```

### UI Components to Use

#### For Child Interface:
- **RecyclerView with CardView** - For chore lists (displays items in scrollable list)
- **Large FAB (Floating Action Button)** - For camera/primary actions (big round button)
- **ProgressBar** - Visual earnings progress (shows how much earned)
- **Bottom Navigation** - Switch between sections (menu at bottom of screen)

#### For Parent Interface:
- **TabLayout** - Switch between children or sections (tabs at top)
- **Expandable Cards** - Show/hide chore details (tap to expand)
- **Dialog Fragments** - Confirmations and input forms (popup windows)
- **Switch/Toggle** - For approve/reject actions (on/off switches)

### Navigation Flow

**Child Flow:**
1. Login/Select Profile → 2. Chore List → 3. Chore Detail → 4. Camera Capture → 5. Confirmation

**Parent Flow:**
1. Login/PIN Entry → 2. Dashboard → 3. Review/Manage Section → 4. Detail Screen → 5. Action/Confirmation

### Accessibility Features
- **High Contrast Mode Support**
- **Large Text Compatibility**
- **Content Descriptions** for screen readers
- **Color-blind Friendly** - Don't rely solely on color for status

---

## Database Schema

### Room Database Implementation

#### 1. Chores Table
Stores the available chores and their associated rewards.

**Kotlin Entity:**
```kotlin
@Entity(tableName = "chores")
data class ChoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "reward")
    val reward: Double
)
```

| Field Name | Data Type | Description |
|------------|-----------|-------------|
| id | Integer (Primary Key) | Unique identifier for each chore |
| name | String | The name/description of the chore |
| reward | Double | The amount to be paid for completing the chore |

**Example Data:**
- "Clean bedroom" - $5.00
- "Take out trash" - $2.00
- "Wash dishes" - $3.00

---

#### 2. User Table
Stores information about all users (both children and parents).

**Kotlin Entity:**
```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "user_name")
    val userName: String,
    
    @ColumnInfo(name = "allowance_balance")
    val allowanceBalance: Double = 0.0,
    
    @ColumnInfo(name = "user_type")
    val userType: UserType
)

enum class UserType {
    CHILD,
    ADULT
}
```

| Field Name | Data Type | Description |
|------------|-----------|-------------|
| id | Integer (Primary Key) | Unique identifier for each user |
| userName | String | Name of the user |
| allowanceBalance | Double | Current balance owed to the child |
| userType | Enum | Either "CHILD" or "ADULT" |

**User Types:**
- **CHILD**: Can select and complete chores, upload pictures
- **ADULT**: Can review chores, approve completions, zero out balances

---

#### 3. Chores Completed Table
Tracks all completed chores with proof of completion.

**Kotlin Entity:**
```kotlin
@Entity(
    tableName = "chores_completed",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["child_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChoreEntity::class,
            parentColumns = ["id"],
            childColumns = ["chore_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("child_id"), Index("chore_id")]
)
data class ChoreCompletionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "child_id")
    val childId: Int,
    
    @ColumnInfo(name = "chore_id")
    val choreId: Int,
    
    @ColumnInfo(name = "picture_path")
    val picturePath: String,
    
    @ColumnInfo(name = "price")
    val price: Double,
    
    @ColumnInfo(name = "status")
    val status: ChoreStatus,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

enum class ChoreStatus {
    AVAILABLE,
    IN_PROGRESS,
    COMPLETED,
    APPROVED,
    REJECTED
}
```

| Field Name | Data Type | Description |
|------------|-----------|-------------|
| id | Integer (Primary Key) | Unique identifier for each completion |
| childId | Integer (Foreign Key) | References User ID from User Table |
| choreId | Integer (Foreign Key) | References Chore ID from Chores Table |
| picturePath | String | File path to the photo proof |
| price | Double | Amount earned for this completion |
| status | Enum | Current status of the chore |
| timestamp | Long | Unix timestamp when chore was completed |

**Chore Status Options:**
- AVAILABLE - Chore is available to be claimed
- IN_PROGRESS - Child has started the chore
- COMPLETED - Child has marked as done and uploaded photo
- APPROVED - Parent has approved the chore
- REJECTED - Parent has rejected the chore

---

## Functional Requirements

### Child User Features

#### 1. Browse Available Chores
- Children can view a list of all available chores
- Each chore displays the name and reward amount
- Interface should be simple and kid-friendly

#### 2. Select and Start a Chore
- Child can select a chore from the available list
- Upon selection, chore status changes to "IN_PROGRESS"
- Chore is assigned to that specific child

#### 3. Complete a Chore
- Child marks the chore as complete
- Child uploads a picture of the completed chore as proof
- Picture should be stored and associated with the completion record
- Chore status changes to "COMPLETED"

#### 4. Track Earnings
- Child can view their current allowance balance
- Display shows total amount earned from all approved chores
- Visual representation of earnings (e.g., progress bar, total amount)

#### 5. View Chore History
- Child can see all their completed chores
- Shows pictures of completed chores
- Displays status of each chore (approved/pending)

---

### Parent User Features

#### 1. Review Completed Chores
- Parents can view all chores marked as "COMPLETED"
- View the uploaded picture proof for each chore
- See which child completed each chore

#### 2. Approve or Reject Chores
- Parents can approve a completed chore
  - Approval adds the reward amount to the child's allowance balance
  - Status changes to "APPROVED"
- Parents can reject a chore if not completed properly
  - Status changes to "REJECTED"
  - Chore becomes available again
  - Child does not receive payment

#### 3. Zero Out Allowance Balance
- Parents can reset a child's allowance balance to $0.00
- Used after paying the child their physical allowance
- Should include a confirmation dialog to prevent accidental resets
- May optionally include a notes field (e.g., "Paid on 10/23/2025")

#### 4. Manage Chores
- Parents can add new chores to the system
- Parents can edit existing chore names and rewards
- Parents can delete chores (with confirmation)

#### 5. Manage Users
- Parents can add new child users
- Parents can view all children and their current balances
- Dashboard view showing all children's earnings

---

## User Experience Requirements

### Child Interface
- Large, easy-to-tap buttons (minimum 48dp)
- Visual indicators for chore status (icons, colors)
- Simple camera integration for taking pictures
- Clear display of their current earnings
- Encouraging messages/animations when chores are completed
- Bottom navigation for easy switching between screens

### Parent Interface
- Quick overview dashboard of all children's activities
- Easy-to-review chore completions with picture preview
- Batch approval option (approve multiple chores at once)
- Clear confirmation dialogs for important actions
- Tabs for switching between children or management sections

### General
- Intuitive navigation between screens
- Offline capability (all data stored locally)
- Simple login/profile selection screen
- Age-appropriate design for children (5+ years old)
- Consistent back button behavior
- Loading indicators for any operations that take time

---

## Development Phases

### Phase 1: Core Setup (Week 1-2)
- Set up Android Studio project with Kotlin
- Configure Gradle dependencies
- Set up Room Database with all entities
- Implement Hilt for dependency injection
- Create basic navigation structure

### Phase 2: Database & Repository Layer (Week 2-3)
- Create DAO interfaces for all tables
- Implement Repository classes
- Write basic unit tests for database operations
- Set up ViewModels with LiveData/Flow

### Phase 3: User Interface - Child Views (Week 3-5)
- Create login/profile selection screen
- Build chore list screen (RecyclerView)
- Implement chore detail screen
- Integrate CameraX for photo capture
- Create earnings tracking screen
- Add bottom navigation

### Phase 4: User Interface - Parent Views (Week 5-7)
- Create parent dashboard
- Build chore review screen with image viewing
- Implement approve/reject functionality
- Create chore management screens (add/edit/delete)
- Create user management screens
- Add balance reset functionality

### Phase 5: Polish & Testing (Week 7-8)
- Implement animations and transitions
- Add confirmation dialogs
- Test on multiple devices
- Fix bugs and edge cases
- Optimize image storage and loading
- Add accessibility features

### Phase 6: Enhancement (Optional)
- Add data export functionality
- Implement backup/restore
- Add notifications
- Create onboarding tutorial

---

## Testing Strategy

### Unit Tests
- Test all Repository methods
- Test ViewModel logic
- Test data transformations and calculations

### Integration Tests
- Test Database operations end-to-end
- Test Room queries with actual database
- Test image saving and retrieval

### UI Tests (Espresso)
- Test navigation flows
- Test user inputs and form validation
- Test camera integration
- Test list scrolling and item selection

### Manual Testing Checklist
- [ ] Child can select and complete chores
- [ ] Photos upload and display correctly
- [ ] Earnings calculate accurately
- [ ] Parent can review and approve/reject
- [ ] Balance resets work correctly
- [ ] App works offline
- [ ] App works on different screen sizes
- [ ] App is usable by children ages 5+

---

## Security Considerations

### Data Protection
- All data stored locally on device (Room Database)
- Images stored in app-private storage directory
- No cloud sync (for initial version) = no external security risks

### Access Control
- Simple PIN protection for parent features
- Separate login profiles for children and parents
- Session management (stay logged in as child)

### Input Validation
- Validate all user inputs (chore names, rewards, etc.)
- Prevent SQL injection (Room handles this automatically)
- Limit image file sizes
- Validate image file types

---

## Performance Considerations

### Image Optimization
- Compress images when captured (reduce file size)
- Generate thumbnails for list views (faster loading)
- Use Coil's caching for efficient image loading
- Limit max image resolution (e.g., 1920x1080)

### Database Optimization
- Use indices on foreign keys (already included in schema)
- Implement pagination for large chore lists
- Use appropriate data types (Int, Double vs String)

### Memory Management
- Recycle bitmaps when not needed
- Use ViewHolder pattern in RecyclerView (automatic)
- Implement proper lifecycle management

---

## Future Enhancement Ideas

### Short-term (Phase 2)
- Recurring chores (daily/weekly tasks)
- Chore categories (cleaning, outdoor, pet care)
- Custom chore icons
- Dark mode support

### Medium-term (Phase 3)
- Multiple parent accounts
- Notifications/reminders for pending chores
- Chore scheduling/assignment
- Bonus multipliers for consecutive completions

### Long-term (Phase 4)
- Cloud backup and sync across devices
- Goal setting (saving for specific items)
- Rewards marketplace (virtual items kids can "buy")
- Gamification (levels, badges, achievements)
- Export reports for tax purposes or record-keeping
- Integration with payment apps for actual allowance transfer

---

## Success Metrics

### Usability
- Children ages 5+ can use app independently within 5 minutes
- Parents can review and approve chores in under 30 seconds each
- 95% of picture uploads are successful
- App launches in under 2 seconds

### Reliability
- Zero allowance calculation errors
- No crashes during normal operation
- Data persists across app restarts
- Images load within 1 second

### Adoption
- Children complete at least 3 chores per week
- Parents review chores within 24 hours
- Family uses app for at least 4 weeks continuously

---

## Deployment & Installation

### Building the APK
1. In Android Studio: Build → Generate Signed Bundle / APK
2. Select APK option
3. Create a keystore (for signing the APK)
4. Choose "release" build variant
5. Build APK

### Installing on Device
1. Enable "Developer Options" on Android device
2. Enable "USB Debugging"
3. Connect device to computer via USB
4. Transfer APK to device
5. Open APK file on device to install

### Alternative: Sideloading
1. Enable "Install Unknown Apps" in device settings
2. Transfer APK via email, cloud storage, or direct transfer
3. Open and install on device

**Note:** For personal/family use, you don't need to publish to Google Play Store. The APK can be installed directly on your family's devices.

---

## Support & Maintenance

### Documentation
- Code comments for complex logic
- README file with setup instructions
- User manual for parents (simple PDF)
- Visual guide for children (with pictures)

### Backup Strategy
- Regular database backups (manual export)
- Keep APK file safe for reinstallation
- Consider implementing automated backup to external storage

### Update Process
- Test updates thoroughly before deploying
- Keep previous APK version as backup
- Inform family members of new features
- Plan updates during low-usage times

---

## Appendix

### Useful Resources

#### Official Android Documentation
- [Android Developer Guide](https://developer.android.com)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [CameraX Documentation](https://developer.android.com/training/camerax)

#### Learning Resources
- [Kotlin for Android Beginners](https://developer.android.com/courses/android-basics-kotlin/course)
- [Android Architecture Components](https://developer.android.com/topic/architecture)
- [Material Design Guidelines](https://m3.material.io)

#### Community & Support
- Stack Overflow (Android/Kotlin tags)
- Reddit: r/androiddev
- Android Developers Slack

### Glossary of Terms

- **APK:** Android Package - the installable file for Android apps
- **DAO:** Data Access Object - interface for database operations
- **Entity:** A data class representing a database table
- **Fragment:** A reusable portion of UI in an Android app
- **ViewModel:** Manages UI-related data in a lifecycle-aware way
- **Repository:** Mediates between data sources and ViewModels
- **Coroutine:** Kotlin's way of handling asynchronous operations
- **LiveData:** Observable data holder that's lifecycle-aware
- **RecyclerView:** Efficient list display component

---

**Document Version:** 2.0  
**Last Updated:** October 23, 2025  
**Status:** Technical Specifications Added  
**Target Audience:** Developers and Technical Architects
