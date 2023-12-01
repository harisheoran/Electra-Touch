package com.example.electratouch.models

import com.example.electratouch.R
import com.example.electratouch.screens.Party

class PartyList {
    val partyList = listOf<Party>(
        Party(name = "Party A", logo = R.drawable.party01, cand = "Ramesh"),
        Party(name = "Party B", logo = R.drawable.party02, cand = "Rahul")
    )
}