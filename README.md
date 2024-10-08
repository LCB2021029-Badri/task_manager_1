# Task Manager App

## Introduction

The Task Manager App is a simple and intuitive Android application developed using Kotlin and XML, designed to help users efficiently manage their tasks. Users can create, update, and delete tasks, assign priorities, set due dates, and mark tasks as completed. Additionally, the app offers date preservation, ensuring that users can track tasks according to their selected dates. The app is built with the MVVM (Model-View-ViewModel) architecture and utilizes Room for local storage, making it easy to manage tasks and ensure smooth performance.


## How to Use the App

1. **Download the App**: [click here](https://github.com/LCB2021029-Badri/task_manager_1/blob/main/APK/taskManager.apk)
2. **Add Task**: Use the Add button to create a new task.
3. **Edit Task**: Tap on any existing task to edit it.
4. **Select a Single Task**: Long press on any task to highlight it.
5. **Select Multiple Tasks**: After selecting one task, tap on additional tasks to select multiple.
6. **Delete Tasks**: Delete selected tasks using the Delete button.
7. **Dark Mode**: Enable dark mode in settings using the switch.
8. **Mark as Completed**: Use the checkbox in the task list to mark tasks as completed.
9. **Priority Levels**: Priority levels are color-coded as red for high, yellow for medium, and green for low.

## Demo Video
https://github.com/user-attachments/assets/e906d448-bdef-4296-9f19-2ca656eef62f

### App Screenshots
<p align="center">
  <img src="https://github.com/user-attachments/assets/bc300e9d-89b0-4eb1-89e5-190b5cd9ef2b" width="200" />
  <img src="https://github.com/user-attachments/assets/47070456-a975-4ada-b4ee-b18a2d2072c8" width="200" />
  <img src="https://github.com/user-attachments/assets/5d522e7b-6f07-4f97-a5e0-00f8701cd0c9" width="200" />
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/9b15e413-4fde-4f50-91a8-58d2a77fe45c" width="200" />
  <img src="https://github.com/user-attachments/assets/7d96ef5d-43e1-433c-8c28-43d160c48ba0" width="200" />
  <img src="https://github.com/user-attachments/assets/91b8abea-7178-441f-afba-5b0d97248b3d" width="200" />
</p>

## Key Features

- **MVVM**: Implements the Model-View-ViewModel architecture to maintain separation of concerns and ensure ease of testing.
- **LiveData**: Uses LiveData for dynamically updating the task list in the UI when the data changes.
- **RecyclerView**: Displays the task list efficiently using RecyclerView for optimal performance with large datasets.
- **Fragments**: Organizes the UI with Fragments for better modularity and flexibility across different screens.
- **Room**: Manages local storage with Room, supporting efficient CRUD operations for tasks.
- **SharedPreferences**: Provides dark mode settings persistence using SharedPreferences.
- **ActionBar Menu**: Displays an info section about the app through an ActionBar menu option.
- **JUnit**: Utilizes JUnit for writing unit tests to verify the correctness of the app’s functionality.
- **Mockito**: Employs Mockito to mock dependencies for unit testing without relying on real objects.
  

## Meet the Developer
- Badrinath Akkala | [GitHub](https://github.com/LCB2021029-Badri)
