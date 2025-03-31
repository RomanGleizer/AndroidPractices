package com.example.androidpracticesapp.data.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "info", strict = false)
data class Info(
    @field:Attribute(name = "type", required = false)
    var type: String? = null,
    @field:Text(required = false)
    var text: String = ""
)
