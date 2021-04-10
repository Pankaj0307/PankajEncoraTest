package com.pankaj.encora.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pankaj.encora.network.Entry

@Entity(tableName = "songs")
class SongsEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?,
    @ColumnInfo(name = "audioLink") var audioLink: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "cover") var cover: String?,
    @ColumnInfo(name = "artist") var artist: String?,
    @ColumnInfo val duration: Int?
) : Parcelable {
    constructor(
        name: String,
        title: String,
        imageUrl: String?,
        audioLink: String?,
        content: String?,
        cover: String?,
        artist: String?,
        duration: Int?
    ) :
            this(0, name, title, imageUrl, audioLink, content, cover, artist, duration)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeInt(id)
        writeString(title)
        writeString(imageUrl)
        writeString(audioLink)
        writeString(content)
        writeString(cover)

    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SongsEntity> = object : Parcelable.Creator<SongsEntity> {
            override fun createFromParcel(source: Parcel): SongsEntity = SongsEntity(source)
            override fun newArray(size: Int): Array<SongsEntity?> = arrayOfNulls(size)
        }
    }
}

//extension function to covert model to DTO and change image path
fun Entry.mapDto() = SongsEntity(
    name = "$name", title = "$title", imageUrl = "$imageUrl",
    audioLink = "${link.audioLink}",
    content = "$content",
    cover = "$cover",
    duration = duration,
    artist = "$artist"
)