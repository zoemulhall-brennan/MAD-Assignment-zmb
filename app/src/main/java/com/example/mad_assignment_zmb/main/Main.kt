package com.example.mad_assignment_zmb.main

import com.example.mad_assignment_zmb.models.YardModel
import com.example.mad_assignment_zmb.models.YardMemStore

    val store = YardMemStore()

    fun main() {
        println("=== EquiFind: Livery Yard & Horse Riding School Finder ===")
        var input: Int
        do {
            input = menu()
            when (input) {
                1 -> addYard()
                2 -> listYards()
                3 -> updateYard()
                4 -> deleteYard()
                5 -> searchYard()
                0 -> println("\nExiting EquiFind. Goodbye!")
                else -> println("\nInvalid option. Please try again.")
            }
        } while (input != 0)
    }

    fun menu(): Int {
        println("\n----------------------------------")
        println(" MAIN MENU")
        println("----------------------------------")
        println(" 1. Add Yard")
        println(" 2. List All Yards")
        println(" 3. Update a Yard")
        println(" 4. Delete a Yard")
        println(" 5. Search Yard by ID")
        println(" 0. Exit")
        print("\nEnter option: ")
        return readlnOrNull()?.toIntOrNull() ?: -1
    }

    fun addYard() {
        println("\n--- Add Yard ---")
        print("Enter Yard Name: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter Description: ")
        val desc = readlnOrNull()?.trim().orEmpty()
        print("Enter Price: ")
        val price = readlnOrNull()?.trim().orEmpty()
        print("Enter Website Link: ")
        val websiteLink = readlnOrNull()?.trim().orEmpty()
        print("Enter Phone Number: ")
        val phone_num = readlnOrNull()?.trim().orEmpty()
        print("Enter Age Limit for Horse Riding lessons: ")
        val age_limit = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val yard = YardModel(title = title, desc = desc, price = 0.0, websitelink = websiteLink, phone_num = phone_num, age_limit = age_limit)
            store.create(yard)
            println("Yard added successfully with ID: ${yard.id}")
        } else {
            println("Title cannot be empty. Creation cancelled.")
        }
    }

    fun listYards() {
        println("\n--- All Yards ---")
        val yards = store.findAll()
        if (yards.isEmpty()) {
            println("No yards stored yet.")
        } else {
            yards.forEach { println("ID: ${it.id} | Title: ${it.title} | Description: ${it.desc} | Price €${it.price} | Phone Number: ${it.phone_num} | Age Limit for Horse Riding Lessons: ${it.age_limit}") }
        }
    }

    fun updateYard() {
        println("\n--- Update Yard ---")
        listYards()
        if (store.findAll().isEmpty()) return

        print("\nEnter ID of Yard to update: ")
        val id = readlnOrNull()?.toLongOrNull()

        if (id != null && store.findOne(id) != null) {
            print("Enter New Title: ")
            val title = readlnOrNull()?.trim().orEmpty()
            print("Enter New Description: ")
            val description = readlnOrNull()?.trim().orEmpty()
            print("Enter New Price: ")
            val price = readlnOrNull()?.trim().orEmpty()
            print("Enter New Website Link: ")
            val websiteLink = readlnOrNull()?.trim().orEmpty()
            print("Enter New Phone Number: ")
            val phone_num = readlnOrNull()?.trim().orEmpty()
            print("Enter New Age Limit for Horse Riding Lessons: ")
            val age_limit = readlnOrNull()?.trim().orEmpty()

            if (title.isNotEmpty()) {
                val updated = store.update(YardModel(id = id, title = title, desc = description, price = 0.0, websitelink = websiteLink, phone_num = phone_num, age_limit = age_limit))
                if (updated) println("Yard updated successfully.")
            } else {
                println("Title cannot be empty. Update cancelled.")
            }
        } else {
            println("Yard with ID $id not found.")
        }
    }


    fun deleteYard() {
            println("\n--- Delete Yard ---")
            listYards()
            if (store.findAll().isEmpty()) return

            print("\nEnter ID of Yard to delete: ")
            val id = readlnOrNull()?.toLongOrNull()

            if (id != null) {
                val deleted = store.delete(id)
                if (deleted) {
                    println("Yard with ID $id deleted successfully.")
                } else {
                    println("Yard with ID $id not found.")
                }
            } else {
                println("Invalid ID entered.")
            }
        }



    fun searchYard() {
        println("\n--- Search Yard ---")
        print("Enter ID: ")
        val id = readlnOrNull()?.toLongOrNull()

        if (id != null) {
            val yard = store.findOne(id)
            if (yard != null) {
                println("Found: ID: ${yard.id} | Title: ${yard.title} | Description: ${yard.desc} | Price €${yard.price} | Website Link: ${yard.websitelink} | Phone Number: ${yard.phone_num} | Age Limit for Horse Riding Lessons: ${yard.age_limit}")
            } else {
                println("No yard found with ID $id.")
            }
        } else {
            println("Invalid ID entered.")
        }
    }
