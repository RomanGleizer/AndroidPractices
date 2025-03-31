package com.example.androidpracticesapp.data.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ann", strict = false)
data class AnimeResponse(
    @field:ElementList(inline = true, entry = "anime", required = false)
    var animeList: MutableList<Anime> = mutableListOf()
)
