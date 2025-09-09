🏖️ Resort Room Booking Mobile App
📌 Project Overview

A mobile application developed in Android Studio (Java) with Firebase as the backend. The app allows guests to view available rooms, check details, and make bookings seamlessly. Room details are managed by the admin in Firestore, while users only interact with booking features.

⚙️ Tech Stack

Frontend: Android Studio (Java)

Backend: Firebase Firestore & Firebase Authentication

UI Components: RecyclerView, Booking Form

🔍 Features

📋 Room Listing – Displays available rooms (Deluxe & Beach View)

🖼️ Room Details View – Shows images and descriptions of each room

🏷️ Booking System – Users can book rooms via a simple form

🔑 Auto-filled Fields – Logged-in user’s email, username, and RoomId are pre-filled in the booking form

☑️ Booking Storage – All booking data is saved to Firestore under bookings collection

🚫 Admin-Controlled – Only admin can upload room images and details

📊 Firestore Data Structure
rooms Collection

roomId (unique ID)

roomType (Deluxe / Beach View)

imageUrl

description

availability (true/false)

bookings Collection

CheckinDate

ClientId (username)

FullName

Email

contactNumber

RoomId

✅ Impact

This app simplifies holiday resort bookings, reduces manual work for the resort, and provides a smooth user experience for guests.
