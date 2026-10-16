package com.example.mad_assignment_zmb

import com.example.mad_assignment_zmb.models.YardModel

interface OnYardListener {

    fun onYardClick(yard: YardModel)

    fun onYardLongClick(yard: YardModel): Boolean

}