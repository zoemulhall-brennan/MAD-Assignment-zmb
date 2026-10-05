package com.example.mad_assignment_zmb.models

data class YardModel (
    var id: Long = 0L,
    var title: String = "",
    var desc: String = "",
    var price: Double = 0.0,
    var websitelink: String = "",
    var phone_num: String = "",
    var age_limit: String = "",

)

data class Services(
    var service_title: String = "",
    var service_desc: String = ""
)

data class Reviews(
    var name: String = "",
    var review: String = ""
)



