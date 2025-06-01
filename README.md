# 🇧🇩 Simulating Operations of Bangladesh Military Academy

This is a Java-based Object-Oriented Programming (OOP) project designed to simulate the key operations and workflows of the **Bangladesh Military Academy (BMA)**. The project covers roles like cadets, instructors, medical officers, and administrators to mimic real-life academy functions.

## 📘 Project Overview

Our simulation focuses on how different roles within the academy interact with the system, such as:

- Managing users and system settings
- Tracking cadet training and medical records
- Issuing equipment and monitoring logistics
- Assigning instructors and scheduling drills

This project follows the basic structure taught in **CSE213 (Java Programming)** and helps us practice real-world object-oriented programming.

---

## 👥 Group Members

| Name               | ID        | Assigned Users                      |
|--------------------|-----------|-------------------------------------|
| Shamsad Shanin     | 2331130   | System Administrator, Commandant    |
| Fatema Tug Juhora  | 2331270   | Instructor, Logistics Officer       |
| Afifa Naz          | 2331649   | Cadet, Cadet Supervisor             |
| Muaz Ibne Omar     | 2331529   | Medical Officer, Drill Master       |

---

## 🧑‍🏫 OOP Structure

We simulate the system using **Object-Oriented Programming**. Each user is modeled as a class with methods that perform specific tasks (goals).

Example classes:
- `SystemAdministrator`
- `Cadet`
- `Instructor`
- `MedicalOfficer`
- `DrillMaster`
- `LeaveRequest`, `MedicalReport`, `Equipment`

Common actions like `login()`, `viewReports()`, `submitRequest()` are shared across users using inheritance or interfaces.

---

## 🛠️ Features (Per Role)

Each user has **8 goals/tasks**, such as:

- **System Administrator**: Add/remove users, manage access, backup system
- **Cadet**: Mark attendance, request leave, view performance
- **Instructor**: Upload materials, schedule classes, view feedback
- **Medical Officer**: Record checkups, manage health reports
- **Logistics Officer**: Issue equipment, track supplies
- **Drill Master**: Monitor attendance, evaluate drills

➡️ Total Goals: `4 members × 2 users × 8 goals = 64 goals`

---

## 💡 Technologies Used

- **Language:** Java (OOP Concepts)
- **IDE:** IntelliJ IDEA / Eclipse / VS Code
- **Data Storage:** In-memory (using arrays or ArrayLists)
- **No external libraries or database** (beginner-level scope)

---

## 🚀 How to Run

1. Clone the repository or download the `.zip`
2. Open in your Java IDE
3. Compile and run `HelloApplication.java`
4. Follow the console instructions to simulate user workflows

---

## 📊 Example Workflow

```text
Role: Cadet
→ Login
→ View daily routine
→ Mark attendance
→ Submit progress report
→ Request leave
