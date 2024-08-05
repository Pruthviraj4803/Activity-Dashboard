# Activity-Dashboard

## Overview
The Activity Dashboard is a simple and efficient application designed to help users manage their tasks. The app allows users to add, edit, mark complete, and delete tasks, providing an organized and clutter-free interface.

## Features
- **Add Tasks:** Easily create new tasks with titles and optional details.
- **Edit Tasks:** Modify existing tasks by changing the title, due date, priority, or notes.
- **Mark Tasks Complete:** Check off tasks as they are finished.
- **Delete Tasks:** Remove unwanted tasks from the list.
- **Set Due Dates:** Assign due dates to tasks for better organization.
- **Task Lists:** Categorize similar tasks into a list for simplification.
- **Google Voice Assist:** Add tasks by speaking after clicking the microphone button.
- **Search and Filter:** Search tasks by various criteria (date, priority) and filter by completion status.

## UI Designs
### Main Screen
- Clean and clutter-free layout.
- Prominent "Add Task" button.
- List of tasks displayed clearly using a RecyclerView for efficient scrolling and updates.
- Each task item shows details like title, due date (optional), and completion status (checkbox).

### Add Task Screen
- Input field for task title.
- Optional fields for due date (calendar picker), priority (dropdown menu), and notes (text editor).
- Time set for the task.
- Repeat times dropdown.
- Button to save the new task.
- Option to add to an already existing list of tasks or create a new list.

### Edit Task Screen
- Similar to Add Task Screen, pre-filled with existing task details.
- Option to change completion status.
- Button to save changes or discard.

## App Flow Diagram
1. **Launch App:** Main screen displays existing tasks.
2. **Add Task:** User taps the "Add Task" button.
3. **Create Task:** User enters task details on the Add Task screen.
4. **Save Task:** User taps the save button, and the new task is added to the list on the main screen.
5. **Edit Task:** User swipes on an existing task and selects "Edit."
6. **Modify Task:** User makes changes on the Edit Task screen.
7. **Save Changes:** User taps the save button, and changes are reflected on the main screen.
8. **Mark Complete:** User taps the checkbox on a task to mark it complete (optional visual change for completed tasks).
9. **Delete Task:** User swipes on a task and selects "Delete" or uses a dedicated "Delete" button.
10. **Confirmation:** User confirms deletion (optional).
11. **Task Removed:** Task is removed from the list.

## Data Management
- **Task Object:** A class representing a task with attributes like title, due date (if applicable), priority (if applicable), notes (if applicable), and a completion flag (boolean).
- **Data Persistence:** When a user adds, edits, or deletes a task, the app updates the underlying storage (SharedPreferences or Room database) accordingly.

## Additional Considerations
- **Notifications (optional):** Implement reminders for tasks with due dates using the Android notification system.
- **Backup and Restore (optional):** Allow users to back up their tasks to the cloud and restore them on a new device.
- **Themes (optional):** Offer different themes for the app's UI to personalize the experience.
- **Security (optional):** If the app handles sensitive tasks, consider implementing password protection or encryption.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


