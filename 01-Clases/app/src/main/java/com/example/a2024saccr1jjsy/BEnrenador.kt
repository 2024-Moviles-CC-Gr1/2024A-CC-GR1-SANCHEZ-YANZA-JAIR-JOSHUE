package com.example.a2024saccr1jjsy

class BEnrenador (
    var id:Int,
    var nombre:String,
    var description:String?
    )
{
    override fun toString(): String {
        return "$nombre ${description}"
    }
}