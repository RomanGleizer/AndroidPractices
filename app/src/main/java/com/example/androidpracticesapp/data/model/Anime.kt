package com.example.androidpracticesapp.data.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Модель, представляющая аниме
 */
@Root(name = "anime", strict = false)
data class Anime(
    @field:Attribute(name = "id")
    var id: Int = 0,
    @field:Attribute(name = "name", required = false)
    var title: String = "",
    @field:Attribute(name = "type", required = false)
    var type: String? = null,
    @field:ElementList(inline = true, required = false)
    var info: MutableList<Info> = mutableListOf()
) {
    val plotSummary: String
        get() = info.firstOrNull { it.type == "Plot Summary" }?.text ?: ""
}
