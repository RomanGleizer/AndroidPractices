package com.example.androidpracticesapp.data.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Обёртка для XML-ответа с корневым элементом <ann>
 */
@Root(name = "ann", strict = false)
data class AnimeResponse(
    @field:ElementList(inline = true, entry = "anime", required = false)
    var animeList: MutableList<Anime> = mutableListOf()
)
