# Mexico Migrant Worker Usability Study MVP

## Overview
This Android application is designed for a comparative usability study to test two different interface prototypes for low-literacy migrant workers in Mexico. The app allows users to complete critical tasks related to job searching, payment review, and contract acceptance while accurately logging usability metrics.

## Technical Specifications
- **Platform**: Android Native (Kotlin)
- **Target SDK**: 36 (exceeds requirement of 34+)
- **Minimum SDK**: 24
- **Architecture**: Single-Activity with Jetpack Compose UI
- **Data**: Hardcoded mock data (offline-first, no external APIs)
- **Navigation**: Navigation Compose with fragment-like screen management

## Features

### Prototype A: Speech-Based Interface (Blue Theme)
**Color**: Blue (#2563EB - Blue 600)  
**Interaction Model**: Voice-command simulation with audio feedback

#### Screens:
1. **Speech Home Screen**
   - Large microphone button for voice interaction
   - Audio prompt: "Bienvenido, Juan. ¿Qué quieres hacer hoy?"
   - Simulated voice commands for navigation
   - Minimal text, maximum audio cues

2. **Speech Job List Screen**
   - Three job listings with minimal text
   - Audio playback icons for job descriptions (simulated TTS)
   - Voice-based job selection (number-based)
   - Large numbered badges for each job

3. **Receipt Screen (Blue Mode)**
   - Audio instruction prompts
   - Large, clear display of net pay and deductions
   - "Pedir por Voz" button for voice-based receipt request

### Prototype B: Image-Based Interface (Green Theme)
**Color**: Green (#16A34A - Green 600)  
**Interaction Model**: Icon-driven navigation with visual safety indicators

#### Screens:
1. **Image Home Screen**
   - 2x2 grid of large, icon-driven buttons
   - Icons: Briefcase, Wallet, Checkmark, User
   - High-contrast, touchable cards
   - Clear visual hierarchy

2. **Image Job List Screen**
   - Visual safety indicators with color coding:
     - **Green with Checkmark**: Safe job with benefits
     - **Red with Warning Icon**: Job without benefits/unsafe conditions
   - Large, distinct cards with prominent icons
   - "ACEPTAR CONTRATO" buttons on each card
   - Visual legend for safety indicators

3. **Receipt Screen (Green Mode)**
   - Same layout with green color theme
   - "Enviar por WhatsApp" button for sharing
   - Icon-rich presentation of payment details

### Shared Screens
1. **Job Detail Screen**
   - Comprehensive job information
   - Safety indicators
   - Contract acceptance functionality
   - Works with both prototypes (adapts to current theme)

2. **Settings Screen**
   - User profile display
   - Current mode indicator
   - Usability metrics viewer
   - Mock user data (Name: Juan, Literacy Level: Bajo)

### Mode Switching
- **Floating Action Button (FAB)**: Always visible in bottom-right corner
- Instantly switches between Prototype A (Blue/Speech) and Prototype B (Green/Image)
- Switches entire UI theme, navigation, and interaction patterns
- Icon changes based on current mode:
  - Blue mode shows Image icon (to switch to image mode)
  - Green mode shows Mic icon (to switch to speech mode)

## Usability Logging System

The `UsabilityLogger` singleton tracks:

### Task Completion Time (TCT)
- Records start and completion timestamps for each task
- Automatically calculates duration in milliseconds
- Tasks tracked:
  - `TASK1_FIND_JOB`: Finding and selecting a job with benefits
  - `TASK2_VIEW_RECEIPT`: Viewing payment receipt
  - `TASK3_ACCEPT_CONTRACT`: Accepting a job contract

### Task Error Rate (TER)
- Logs errors during task execution
- Counts errors per task
- Records error details and context

### Additional Metrics
- Navigation events (screen transitions)
- User interactions (button clicks, voice commands)
- Mode switches
- Timestamp for all events

### Accessing Logs
1. Navigate to Settings screen
2. Click "Ver Métricas de Usabilidad" button
3. View summary of all recorded metrics
4. Logs can be exported to CSV format for analysis

### Log Export
The logger can save data to CSV format with the following fields:
- Task ID
- Prototype Mode
- Event Type
- Timestamp
- Details

## Mock Data

### User Profile
- **Name**: Juan
- **Literacy Level**: Bajo (Low)

### Jobs (3 Available)
1. **Trabajador de Construcción**
   - Pay: $200 MXN/día
   - Location: Ciudad de México
   - Benefits: ✓ Yes
   - Safe: ✓ Yes
   - Hours: 40/week
   - Contract: Temporary - 3 months

2. **Trabajador Agrícola**
   - Pay: $150 MXN/día
   - Location: Sinaloa
   - Benefits: ✗ No
   - Safe: ✗ No
   - Hours: 60+/week
   - Contract: Temporary - Seasonal

3. **Ayudante de Almacén**
   - Pay: $180 MXN/día
   - Location: Monterrey
   - Benefits: ✓ Yes
   - Safe: ✓ Yes
   - Hours: 48/week
   - Contract: Permanent

### Payment Receipt
- Gross Pay: $4,200 MXN
- Net Pay: $3,600 MXN
- Deductions: $600 MXN
- Date: 15 de Noviembre, 2024

## User Study Tasks

### Task 1: Find a Job with Benefits
**Objective**: Navigate to job listings and identify the job with safety benefits.

**Success Criteria**:
- User navigates to job list
- User identifies job with benefits (visual or audio cues)
- User views job details

**Logged Metrics**: Time from task start to job selection, errors/wrong selections

### Task 2: View Payment Receipt
**Objective**: Access and understand the payment receipt.

**Success Criteria**:
- User navigates to receipt screen
- User views net pay and deductions
- User understands the breakdown

**Logged Metrics**: Time to access receipt, navigation errors

### Task 3: Accept a Job Contract
**Objective**: Review job details and accept a contract.

**Success Criteria**:
- User views full job details
- User clicks "Accept Contract" button
- User confirms acceptance

**Logged Metrics**: Time from job detail view to acceptance, hesitation indicators

## Navigation Structure

```
Main Activity (with FAB for mode switching)
│
├── Prototype A (Speech-Based)
│   ├── Speech Home
│   ├── Speech Job List
│   └── (Shared screens)
│
├── Prototype B (Image-Based)
│   ├── Image Home
│   ├── Image Job List
│   └── (Shared screens)
│
└── Shared Screens
    ├── Receipt Screen (adapts to current mode)
    ├── Job Detail Screen (adapts to current mode)
    └── Settings Screen
```

## Bottom Navigation
Adapts based on current prototype mode:
- **Prototype A**: Inicio | Empleos | Pago | Ajustes
- **Prototype B**: Inicio | Empleos | Pago | Datos

## Running the Application

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or later
- Android SDK 34+

### Build and Run
1. Open project in Android Studio
2. Sync Gradle files
3. Select a device/emulator (API 24+)
4. Click Run

### For Testing
1. Start with Prototype A (Speech-Based, Blue theme)
2. Complete all three tasks
3. Use FAB to switch to Prototype B (Image-Based, Green theme)
4. Complete all three tasks again
5. View metrics in Settings screen
6. Export logs for analysis

## Key Design Decisions

### For Low-Literacy Users
1. **Large Touch Targets**: All interactive elements are 48dp+ for easy tapping
2. **High Contrast**: Clear visual separation between elements
3. **Consistent Patterns**: Repeated interaction patterns throughout
4. **Minimal Text**: Essential information only in Prototype A
5. **Universal Icons**: Internationally recognized symbols in Prototype B
6. **Audio Simulation**: TTS simulation for Prototype A (actual TTS can be added)

### For Usability Study
1. **Non-Intrusive Logging**: All logging happens in background
2. **Precise Timing**: Millisecond accuracy for TCT
3. **Context Preservation**: All events include contextual details
4. **Easy Export**: CSV format for statistical analysis
5. **A/B Testing Ready**: Instant switching between prototypes

## Future Enhancements

### Phase 2 Considerations
- Real TTS integration (Android Speech API)
- Real speech recognition for voice commands
- Firebase/Firestore integration for remote logging
- Multi-language support (Spanish, Indigenous languages)
- Real job data API integration
- Authentication system
- Payment history
- Contract management
- Push notifications for job updates

## Code Structure

```
app/src/main/java/com/example/mexico_proj/
│
├── MainActivity.kt                 # Main entry point with navigation
├── AppModels.kt                    # Data models and mock data
├── AppState.kt                     # Global state management
├── AppSettings.kt                  # Settings manager
├── UsabilityLogger.kt              # Logging system
│
├── ui/
│   ├── components/
│   │   ├── AppBottomBar.kt        # Adaptive bottom navigation
│   │   └── BottomTab.kt           # Tab data class
│   │
│   ├── screens/
│   │   ├── SpeechHomeScreen.kt    # Prototype A home
│   │   ├── SpeechJobListScreen.kt # Prototype A job list
│   │   ├── ImageHomeScreen.kt     # Prototype B home
│   │   ├── ImageJobListScreen.kt  # Prototype B job list
│   │   ├── ReceiptScreen.kt       # Shared receipt (adaptive)
│   │   ├── JobDetailScreen.kt     # Shared job detail
│   │   └── SettingsScreen.kt      # User profile & metrics
│   │
│   └── theme/
│       ├── Color.kt               # Color definitions
│       ├── Theme.kt               # Theme switching logic
│       └── Type.kt                # Typography
```

## License
This is a research project for usability studies. All rights reserved.

## Contact
For questions about the study or technical implementation, please contact the research team.

