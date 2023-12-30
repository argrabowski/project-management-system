# Project Management System

This repository contains the source code for a simple Project Management System implemented using AWS Lambda functions in Java. The system allows users to manage projects, tasks, teammates, and assignments.

## Overview

The Project Management System is designed to assist in managing projects, tasks, and teammates efficiently. It is built using AWS Lambda functions, allowing for serverless execution and easy scalability. The system leverages AWS services for database management and provides a simple yet effective interface for project management.

## Features

- **Create and Delete Projects:** Users can create new projects and delete existing projects.

- **Add and Remove Teammates:** Teammates can be added to or removed from a project.

- **Create, Decompose, Rename, and Mark Tasks:** Users can create tasks, decompose tasks into subtasks, rename tasks, and mark tasks as completed.

- **Assign and Unassign Teammates from Tasks:** Teammates can be assigned to tasks, and assignments can be removed.

- **List All Projects:** Retrieve a list of all projects along with their details.

## Setup

To set up the Project Management System locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/argrabowski/project-management-system.git
   ```

2. Open the project in your preferred Java development environment.

3. Configure AWS credentials for the AWS SDK.

4. Build and deploy the Lambda functions.

## AWS Lambda Functions

The system consists of several AWS Lambda functions, each serving a specific purpose. Here are some key functions:

- **CreateProjectHandler:** Handles requests to create a new project.

- **ListAllProjectsHandler:** Retrieves a list of all projects.

- **AddTeammateHandler:** Adds a teammate to a project.

- **RemoveTeammateHandler:** Removes a teammate from a project.

- **CreateTaskHandler:** Handles requests to create a new task.

- **DecomposeTaskHandler:** Decomposes a task into subtasks.

- **RenameTaskHandler:** Renames a task.

- **MarkTaskHandler:** Marks a task as completed.

- **UnassignTeammateFromTaskHandler:** Unassigns a teammate from a task.

- **DeleteProjectHandler:** Handles requests to delete a project.

## Database

The system uses a relational database to store information about projects, tasks, teammates, and assignments. AWS services such as RDS may be utilized for this purpose.

## Usage

To use the Project Management System, interact with the Lambda functions through an API Gateway or other interfaces. API requests should include appropriate JSON payloads based on the function being invoked.
