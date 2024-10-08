# Task Manager App

## Introduction

The Task Manager App is a simple and intuitive Android application developed using Kotlin and XML, designed to help users efficiently manage their tasks. Users can create, update, and delete tasks, assign priorities, set due dates, and mark tasks as completed. Additionally, the app offers date preservation, ensuring that users can track tasks according to their selected dates. The app is built with the MVVM (Model-View-ViewModel) architecture and utilizes Room for local storage, making it easy to manage tasks and ensure smooth performance.

## Project Components

This project is divided into key features, ensuring a smooth and scalable task management experience:

1. **Android App**: The Android side allows users to manage tasks, including adding, editing, and deleting tasks, with support for multiple selection and dark mode.

## Demo Video
[**click here**](#)

### Screenshots & Video


### Lender's Side App Screenshots

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

## How to Use the App

1. **Download the App**: [Download APK](#)
2. **Add Task**: Use the Add button to create a new task.
3. **Edit Task**: Tap on any existing task to edit it.
4. **Select a Single Task**: Long press on any task to highlight it.
5. **Select Multiple Tasks**: After selecting one task, tap on additional tasks to select multiple.
6. **Delete Tasks**: Delete selected tasks using the Delete button.
7. **Dark Mode**: Enable dark mode in settings using the switch.
8. **Mark as Completed**: Use the checkbox in the task list to mark tasks as completed.
9. **Priority Levels**: Priority levels are color-coded as red for high, yellow for medium, and green for low.

## Features

- **Task Management**: Create, edit, delete, and prioritize tasks.
- **Dark Mode**: Toggle between dark and light themes.
- **Date Preservation**: Track tasks by due dates and preserve them for future reference.
- **Task Status**: Mark tasks as completed or pending, with dynamic status updates.

## Tech Stack

- **Kotlin**
- **XML**
- **Room Database**
- **LiveData**
- **RecyclerView**
- **Fragments**
- **JUnit**
- **Mockito**

## Meet the Developer
- Badrinath Akkala | [GitHub](https://github.com/LCB2021029-Badri)
