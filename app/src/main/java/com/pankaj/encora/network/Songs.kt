package com.pankaj.encora.network

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter

@Xml(name = "feed")
data class Feed(
    @Element(name = "entry") val entryList: List<Entry>?
)

@Xml(name = "entry", writeNamespaces = arrayOf("im=itunes.apple.com/rss"))
data class Entry(
    @PropertyElement(name = "id ") val id: String?,
    @PropertyElement(
        name = "im:name",
        converter = HtmlEscapeStringConverter::class
    ) val name: String?,
    @PropertyElement(
        name = "title",
        converter = HtmlEscapeStringConverter::class
    ) val title: String?,
    @PropertyElement(name = "im:image") val imageUrl: String?,
    @PropertyElement(
        name = "im:artist",
        converter = HtmlEscapeStringConverter::class
    ) val artist: String?,
    @PropertyElement(name = "im:price") val price: String?,
    @PropertyElement(
        name = "content",
        converter = HtmlEscapeStringConverter::class
    ) val content: String,
    @Element val link: Link,
    @Element val category: Category,
    @PropertyElement(name = "cover") val cover: String?,
    @PropertyElement(name = "duration") val duration: Int
)

@Xml(name = "category")
data class Category(
    @Attribute(name = "label") val label: String?
)

@Xml(name = "link", writeNamespaces = arrayOf("im=itunes.apple.com/rss"))
data class Link(
    @PropertyElement(name = "im:duration ") val duration: String?,
    @Attribute(name = "type") val type: String?,
    @Attribute(name = "href") val audioLink: String?
)