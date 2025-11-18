# Usability Study Guide: Trabajo Seguro MVP

## Study Overview
This guide provides instructions for conducting a comparative usability study of two interface prototypes designed for low-literacy migrant workers in Mexico.

## Study Objectives
1. Compare Task Completion Time (TCT) between voice-based and image-based interfaces
2. Measure Task Error Rate (TER) for both prototypes
3. Assess user preference and ease of use
4. Identify usability issues specific to low-literacy users

## Participant Requirements
- Low to medium literacy level
- Experience with basic smartphone usage
- Spanish-speaking
- Migrant worker or similar demographic
- No prior exposure to this application

## Study Protocol

### Pre-Study Setup (5 minutes)
1. **Device Preparation**
   - Ensure app is installed and updated
   - Clear any previous session logs
   - Set device to Do Not Disturb mode
   - Charge device to 80%+ battery

2. **Participant Briefing**
   - Explain study purpose (usability testing, not testing the participant)
   - Obtain informed consent
   - Explain think-aloud protocol
   - Answer any questions

3. **Demographic Collection**
   - Age
   - Education level
   - Smartphone experience (years)
   - Daily phone usage (hours)
   - Previous job-search app experience

### Study Session Structure

#### Phase 1: Prototype A - Speech-Based Interface (15-20 minutes)

**Introduction (2 minutes)**
- Show the blue-themed interface
- Explain voice interaction concept (simulated)
- Allow 1-2 minutes of free exploration

**Task 1: Find a Safe Job (5-7 minutes)**
```
Scenario: "Necesitas encontrar un trabajo que tenga seguro médico 
y sea seguro. Usa la aplicación para encontrar y seleccionar un trabajo 
con beneficios."

Success Criteria:
- Navigate to job list
- Identify job with benefits
- View job details
```

**Task 2: View Payment Information (3-5 minutes)**
```
Scenario: "Quieres revisar tu último pago. Encuentra y abre tu recibo 
de pago más reciente."

Success Criteria:
- Navigate to receipt screen
- View net pay
- Understand deductions
```

**Task 3: Accept a Job Contract (5-7 minutes)**
```
Scenario: "Has decidido aceptar el trabajo de 'Trabajador de Construcción'. 
Encuentra este trabajo y acepta el contrato."

Success Criteria:
- Navigate to specific job
- Review details
- Accept contract
```

**Observation Points**
- Hesitation before actions
- Confusion with voice prompts
- Reliance on audio vs visual cues
- Navigation errors
- Task abandonment attempts

#### Phase 2: Prototype B - Image-Based Interface (15-20 minutes)

**Transition (2 minutes)**
- Tap the floating action button (FAB) to switch modes
- Show the green-themed interface
- Explain icon-based navigation
- Allow 1-2 minutes of free exploration

**Repeat Tasks 1-3** (same scenarios as Phase 1)

**Additional Observation Points**
- Icon recognition
- Color-coding comprehension (green = safe, red = unsafe)
- Touch accuracy
- Visual scanning patterns

#### Post-Study (10 minutes)

**Preference Questions**
1. Which interface did you find easier to use? Why?
2. Which interface felt more comfortable?
3. Which interface would you use in real life?
4. What did you like about each interface?
5. What was confusing or difficult?

**System Usability Scale (SUS)**
Rate each statement 1-5 (Strongly Disagree to Strongly Agree):
1. I think I would like to use this system frequently
2. I found the system unnecessarily complex
3. I thought the system was easy to use
4. I would need technical support to use this system
5. The various functions were well integrated
6. There was too much inconsistency
7. Most people would learn this system quickly
8. I found the system cumbersome to use
9. I felt confident using the system
10. I needed to learn a lot before getting going

(Repeat for both prototypes)

## Data Collection

### Automated Metrics (via UsabilityLogger)
The app automatically logs:
- Task start/completion times
- Navigation paths
- Interaction events
- Error occurrences
- Mode switches

### Manual Observations
Record in separate log:
- Facial expressions (confusion, satisfaction)
- Verbal comments during think-aloud
- Physical hesitations
- Help requests
- Critical incidents

### Accessing Logged Data

**During Study:**
1. Open Settings screen
2. Tap "Ver Métricas de Usabilidad"
3. Review summary on-screen

**After Study:**
1. Navigate to device files: `/Android/data/com.example.mexico_proj/files/`
2. Locate `usability_log.csv`
3. Export to computer for analysis

**CSV Format:**
```csv
TaskID,PrototypeMode,EventType,Timestamp,Details
TASK1_FIND_JOB,SPEECH_BASED,TASK_START,1700000000000,Speech-based job search
TASK1_FIND_JOB,SPEECH_BASED,NAVIGATION,1700000005000,From: SpeechHome To: SpeechJobList
TASK1_FIND_JOB,SPEECH_BASED,INTERACTION,1700000015000,JOB_SELECT | Job: Trabajador de Construcción
TASK1_FIND_JOB,SPEECH_BASED,TASK_COMPLETE,1700000025000,Duration: 25000ms | Job accepted
```

## Analysis Guidelines

### Task Completion Time (TCT)
```
TCT = Task_Complete_Timestamp - Task_Start_Timestamp
```

**Metrics to Calculate:**
- Mean TCT per task per prototype
- Median TCT (more robust to outliers)
- Standard deviation
- 95% confidence intervals

**Comparison:**
- Paired t-test (same participants, different prototypes)
- Effect size (Cohen's d)
- Statistical significance (p < 0.05)

### Task Error Rate (TER)
```
TER = (Number of Errors / Total Task Attempts) × 100
```

**Error Categories:**
1. Navigation errors (wrong screen)
2. Selection errors (wrong job/button)
3. Comprehension errors (misunderstanding task)
4. Recovery errors (unable to recover from mistake)

### Success Rate
```
Success Rate = (Successful Completions / Total Attempts) × 100
```

**Success Criteria:**
- Task completed within reasonable time (< 2 minutes)
- Correct outcome achieved
- No moderator intervention required

## Expected Results

### Hypotheses
1. **H1**: Low-literacy users will complete tasks faster with image-based interface
2. **H2**: Image-based interface will have lower error rates
3. **H3**: Users will prefer image-based interface
4. **H4**: Voice-based interface will cause more navigation errors

### Sample Size Recommendations
- Minimum: 15 participants per group
- Recommended: 25-30 participants per group
- Power analysis: 80% power to detect medium effect size

## Ethical Considerations

### Informed Consent
- Explain data usage
- Right to withdraw
- Anonymity guaranteed
- No personal data collected beyond demographics

### Participant Comfort
- Emphasize "testing the app, not the user"
- Allow breaks
- Provide encouragement
- Stop if participant shows distress

### Data Privacy
- Anonymize all data (use participant IDs)
- Secure storage of logs
- Delete video/audio after transcription
- GDPR/local privacy law compliance

## Troubleshooting

### Common Issues

**App crashes:**
- Restart app
- Clear app data if persistent
- Note incident in manual log

**Logger not recording:**
- Check Settings → Metrics viewer
- Verify storage permissions
- Export logs after each session as backup

**Participant confusion:**
- Provide hints after 2 minutes of struggle
- Note as "moderator intervention" in log
- Consider task failure if excessive help needed

**Mode switch not working:**
- Tap FAB multiple times
- Restart app if necessary
- Verify correct mode by checking color theme

## Post-Study Data Processing

### Data Export Workflow
1. Collect CSV files from all devices
2. Merge into master spreadsheet
3. Clean data (remove test runs)
4. Anonymize participant IDs
5. Calculate aggregate metrics

### Statistical Software
Recommended tools:
- R with ggplot2 (for visualization)
- SPSS (for statistical tests)
- Excel/Google Sheets (for basic analysis)
- Python with pandas/scipy (for advanced analysis)

### Visualization Suggestions
1. **TCT Comparison**: Box plots per prototype per task
2. **Error Rates**: Stacked bar charts
3. **Success Rates**: Grouped bar charts
4. **User Preference**: Pie charts or bar charts
5. **Learning Curves**: Line graphs showing TCT over repeated tasks

## Report Template

### Structure
1. **Executive Summary**
   - Key findings
   - Recommendations

2. **Introduction**
   - Study objectives
   - Research questions

3. **Methodology**
   - Participants (N, demographics)
   - Procedures
   - Measures

4. **Results**
   - Quantitative findings (TCT, TER, Success Rate)
   - Qualitative findings (observations, preferences)
   - Statistical analysis

5. **Discussion**
   - Interpretation of results
   - Limitations
   - Design recommendations

6. **Conclusion**
   - Summary
   - Future work

### Example Finding
```
Task 1 (Find Safe Job):
- Prototype A (Speech): Mean TCT = 45.3s (SD = 12.1)
- Prototype B (Image): Mean TCT = 32.7s (SD = 8.4)
- Difference: 12.6s (p = 0.003, d = 1.2)
- Result: Prototype B significantly faster (large effect size)
```

## Next Steps After Study

### Immediate Actions
1. Thank participants
2. Provide compensation (if applicable)
3. Backup all data
4. Begin transcription (if audio recorded)

### Short-term (1-2 weeks)
1. Complete data analysis
2. Identify critical usability issues
3. Draft initial findings report

### Medium-term (1 month)
1. Finalize comprehensive report
2. Design recommendations for improvements
3. Plan iteration/Phase 2 if needed

## Contact Information
- Principal Investigator: [Name]
- Technical Support: [Contact]
- IRB/Ethics Committee: [Contact]
- Data Security Officer: [Contact]

## Appendices

### Appendix A: Consent Form Template
[Include your consent form]

### Appendix B: Demographic Questionnaire
[Include your demographic form]

### Appendix C: Post-Study Questionnaire
[Include your post-study questions]

### Appendix D: Data Coding Manual
[Include your coding scheme for qualitative data]

---

**Version**: 1.0  
**Date**: November 2024  
**Last Updated**: [Current Date]

