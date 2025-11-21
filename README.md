**SocialSphere**
Social Media Management System (Java Swing + Lambda Expressions + Stream API)
LAB 2 – Functional Programming in Java

This project is a Java Swing–based Social Media Management System designed to demonstrate the concepts of:

Lambda Expressions

Functional Interfaces

Stream API Operations

Event-driven GUI Programming

The application works on a social-media-like dataset and allows users to perform various operations related to Arithmetic, Strings, Number Checking, Stream API, and different types of Lambda Expressions.

🚀 Features
1. **SocialSphere** 

Displays a database of social media posts in a JTable.

Shows username, content, likes, shares, category, publish status.

Automatically loads 10 sample posts on startup.

🧩 Part A – Lambda Expressions
Arithmetic Operations

Performed using the ArithmeticOperation functional interface:

Addition

Subtraction

Multiplication

Division

String Operations

Uses StringOperation lambda expressions:

Reverse string

Count vowels

Convert to uppercase

Number Check

Uses NumberCheck functional interface:

Check EVEN

Check ODD

Check PRIME

🧪 Part B – Types of Lambda Expressions

This program demonstrates:

✔ Lambda with no argument

Example: return a welcome message or timestamp.

✔ Lambda with one argument

Examples:

Capitalize text

Add a hashtag

Square a number

✔ Lambda with two arguments

Examples:

Create a formatted post

Calculate engagement score

✔ Block Lambda Expression

Multi-line lambdas:

Post performance analysis

Average likes by category

🧠 Part C – Java Stream API Operations

The application performs multiple Stream API operations on the social posts list, including:

filter() → Filter published posts

sorted() → Sort posts by likes

map() → Transform posts into usernames

max() → Highest liked post

count() → Count posts

distinct() → Unique categories

limit() → First 3 posts

skip() → Skip first 2 posts

anyMatch() → Any post with >200 likes

allMatch() → Are all posts >50 likes?

collect() → Group posts by category

reduce() → Calculate total engagement

All results are displayed in the Output Console.

🖥 User Interface Overview

The UI contains:

🔹 Input Panel

Arithmetic calculator

String operations

Number checking tools

🔹 Posts Database Table

Displays all sample social media posts

🔹 Stream API Panel

Buttons for each stream operation

🔹 Output Console

Shows results of all operations

📦 Technologies Used

Java 8+ (Lambda & Stream API)

Java Swing (GUI)

OOP + Functional Programming

Collections Framework

▶️ How to Run

Compile the program:

javac LAB2.java


Run it:

java LAB2


The GUI will appear with all features available.

📄 Functional Interfaces Used

ArithmeticOperation

StringOperation

NumberCheck

NoArgOperation

OneArgOperation

TwoArgOperation

These represent custom functional interfaces used to implement lambda expressions across the project.

🎯 Purpose of the Project

This project demonstrates how functional programming can be effectively integrated into:

Real-world applications

Data processing tasks

GUI-driven interactions

It bridges Java Swing (OOP) with Lambda + Stream API (Functional Programming).

📝 Author

Ann mariya Sabu
Topic: Lambda Expressions & Stream API in Java
Project Name: SocialSphere – Social Media Management System
