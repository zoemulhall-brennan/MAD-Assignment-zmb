package com.example.mad_assignment_zmb.models

import java.util.concurrent.atomic.AtomicLong

class YardMemStore {
    private val yards = ArrayList<YardModel>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<YardModel> {
        return yards
    }

    fun create(yard: YardModel) {
        yard.id = lastId.incrementAndGet()
        yards.add(yard)
    }

    fun update(yard: YardModel): Boolean {
        val foundYard = findOne(yard.id)
        return if (foundYard != null) {
            foundYard.title = yard.title
            foundYard.desc = yard.desc
            foundYard.price = yard.price
            foundYard.websitelink = yard.websitelink
            foundYard.phone_num = yard.phone_num
            foundYard.age_limit = yard.age_limit
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val found = findOne(id)
        return if (found != null) {
            yards.remove(found)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): YardModel? {
        return yards.find { p -> p.id == id }
    }
}
