# Quick Start Guide - Trabajo Seguro MVP

## For Developers

### Initial Setup
```bash
# Clone and open in Android Studio
cd Mexico_Proj/Nnkz3
# Open in Android Studio

# Wait for Gradle sync to complete
# Build project
./gradlew build
```

### Run the App
1. Connect Android device (API 24+) or start emulator
2. Click "Run" (Shift+F10)
3. App will launch in Speech-Based mode (Blue theme)

### Testing Both Prototypes

#### Prototype A (Speech-Based - Blue)
- App starts in this mode by default
- Large microphone button on home screen
- Tap microphone to see simulated voice commands
- Select "Buscar Empleo" to see job list
- Tap speaker icons to hear job descriptions (simulated)

#### Prototype B (Image-Based - Green)
- Tap the FAB (Floating Action Button) in bottom-right corner
- App switches to green theme instantly
- Home screen shows 2x2 grid of icon buttons
- Job cards have color-coded safety indicators:
  - Green with checkmark = Safe with benefits
  - Red with warning = Unsafe/no benefits

### Quick Test Flow
```
1. Launch app → Speech Home (Blue)
2. Tap microphone → Select "Buscar Empleo"
3. View job list → Tap speaker icon on a job
4. Tap FAB → Switch to Image mode (Green)
5. Tap "Buscar Trabajo" icon
6. View color-coded job cards
7. Tap "ACEPTAR CONTRATO" on green card
8. Review job details → Accept contract
9. Navigate to Settings → View metrics
```

## For Researchers

### Pre-Study Checklist
- [ ] Install app on test devices
- [ ] Clear previous logs (Settings → App Info → Clear Data)
- [ ] Charge devices to 80%+
- [ ] Set Do Not Disturb mode
- [ ] Test both prototypes work correctly
- [ ] Prepare consent forms
- [ ] Prepare questionnaires

### During Study
1. **Start Fresh**: Clear app data between participants
2. **Observe**: Take notes on hesitations, errors, comments
3. **Don't Intervene**: Unless participant is stuck for 2+ minutes
4. **Record**: Both automated logs and manual observations

### After Each Session
1. Settings → Ver Métricas → Screenshot summary
2. Export logs: Device Files → com.example.mexico_proj → files → usability_log.csv
3. Rename file: `participant_[ID]_[date].csv`
4. Backup immediately

### Data Export Location
```
Android/data/com.example.mexico_proj/files/usability_log.csv
```

## Key Features to Demonstrate

### 1. Mode Switching (CRITICAL)
- **FAB Location**: Bottom-right corner, always visible
- **Visual Change**: Entire UI changes color and structure
- **Icon Change**: Mic ↔ Image icon
- **Instant**: No loading, seamless transition

### 2. Speech-Based Interaction
- Microphone button (home and job list)
- Audio prompt cards (blue background)
- Minimal text philosophy
- Number-based job selection

### 3. Image-Based Interaction
- Icon-driven navigation
- Color-coded safety (green/red)
- Large touch targets
- Visual legends

### 4. Shared Features
- Receipt viewing (adapts to current mode)
- Settings/profile
- Job details
- Bottom navigation (changes labels per mode)

## Common Issues & Solutions

### App won't install
```bash
# Check SDK version
# Minimum: API 24 (Android 7.0)
# Target: API 36

# Rebuild project
./gradlew clean
./gradlew assembleDebug
```

### Logs not saving
```xml
<!-- Add to AndroidManifest.xml if needed -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

### FAB not visible
- Check if keyboard is open (hides FAB)
- Scroll up if at bottom of list
- Restart app if persists

### Wrong language
- App is in Spanish by default
- To add English: Modify strings.xml

## Study Tasks Reference

### Task 1: Find Safe Job ⏱️ Target: < 60 seconds
**Starting Point**: Home screen (either mode)  
**Goal**: Navigate to job list and identify job with benefits  
**Success**: User views details of a job with benefits (green card or audio confirmation)

### Task 2: View Receipt ⏱️ Target: < 30 seconds
**Starting Point**: Any screen  
**Goal**: Access payment receipt  
**Success**: User sees net pay amount clearly displayed

### Task 3: Accept Contract ⏱️ Target: < 90 seconds
**Starting Point**: Job list or home  
**Goal**: Navigate to specific job and accept contract  
**Success**: Contract acceptance confirmation dialog shown

## Metrics Interpretation

### Good Performance
- Task 1: < 45 seconds
- Task 2: < 25 seconds
- Task 3: < 70 seconds
- Error Rate: < 10%

### Needs Improvement
- Task 1: > 90 seconds
- Task 2: > 45 seconds
- Task 3: > 120 seconds
- Error Rate: > 25%

## Emergency Contacts
- App crashes: Restart app, note in log
- Participant distress: Stop study, provide support
- Technical issues: Contact dev team
- Ethical concerns: Contact IRB/ethics committee

## Version Info
- **App Version**: 1.0
- **Build**: Debug
- **Target SDK**: 36
- **Min SDK**: 24

## Additional Resources
- Full documentation: See README.md
- Study protocol: See STUDY_GUIDE.md
- Code structure: See /app/src/main/java/com/example/mexico_proj/

---

**Ready to Start?**
1. Build and run app
2. Test both prototypes
3. Clear data
4. Begin study!

**Questions?** Check README.md or STUDY_GUIDE.md for detailed information.

