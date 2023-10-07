package com.example.electratouch.utils

import com.example.electratouch.R


interface Destinations {
    val route: String
    val icon: Int
    val label: String
}

object MyDatabase : Destinations {
    override val route: String
        get() = "database"
    override val icon: Int
        get() = R.drawable.database
    override val label: String
        get() = "Database"
}

object Admin : Destinations {
    override val route: String
        get() = "Admin"
    override val icon: Int
        get() = R.drawable.admin
    override val label: String
        get() = "Admin"
}

object Election : Destinations {
    override val route: String
        get() = "Election"
    override val icon: Int
        get() = R.drawable.poll
    override val label: String
        get() = "Election"
}