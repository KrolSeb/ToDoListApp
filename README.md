<h1 align="left"> ToDoListApp </h1>
<h4 align="left">
  To-do-list application created with Android SDK and written in Java.
</h4>

<br/>

## Table of contents

- [General info](#general-info)
- [Screenshots](#screenshots)
- [Technologies](#technologies)
- [Setup](#setup)
- [Features](#features)
- [Status](#status)
- [Contact](#contact)

## General info

Project created on the basis of a recruitment task for the Junior Android Developer position. 

## Screenshots

<p align="middle">
  <img src="https://drive.google.com/uc?export=view&id=1-WsorYaxsoZobcLdwz88enUbXoxvQjU8" width="33%" />
  <img src="https://drive.google.com/uc?export=view&id=1ngO15yztDTBbmXDu5oYpzQgIMy_lNe_v" width="33%" />
</p>

## Technologies

- Java 8
- Android SDK (API level 28 - Android 9.0)
- Gradle ver. 5.1.1
- IDE - Android Studio ver. 3.x

## Setup

Application is available for devices with Android OS version 5.0 (Lollipop) and above.

Clone this repository via Android Studio startup menu. IDE should recognize all dependencies and download them automatically. Build project and run via default Android emulator.

## Features

Below I conclude task content. 
I did the most of them excluding dependency injection:

Title: Write simple TODO APP.

1. The application will enable you to add tasks to to-do list.
2. After we launch an application we see list of scheduled tasks and button used to add new task.
3. In case that we didn't add any task yet, appropriate statement should be displayed.
4. If we activate add task button, an application takes us to the new window, where we complete name, date and category of task (one of work, shopping or other). Every position should be entered or selected in a separate control.
5. On the screen, we additionally support Add task and Cancel buttons. After clicking them an application takes us back to the task list window.
6. After we add task to list successfully, message about operation success (implemented as Toast or Snackbar) will be displayed.
7. If add task process was successful, list with task will refresh automatically.
8. In case of add task error, the Dialog with retry option is displayed.
9. Tasks must be saved in the device storage.
10. An application must work properly in both screen orientations (horizontal and vertical).
11. MVP or MVVM pattern usage will be an additional advantage.
12. Dependency Injection (Dagger2, Kodein or Koin) usage will be an additional advantage.
13. You can write code in Java or Kotlin language.

## Status

The basic version of a project (meeting the requirements) has been **completed**. During development I learned MVP pattern.

In the future I want to:

- change way of storing and retrieving tasks from text file to SQLite database,
- learn and implement dependency injection technique in code.

## Contact

Created by [@KrolSeb](https://krolseb.github.io/) - please feel free to contact me if you need any further information.
