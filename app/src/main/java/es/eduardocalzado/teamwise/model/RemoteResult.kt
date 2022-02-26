package es.eduardocalzado.teamwise.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RemoteResult(
    val errors: List<String>,
    val results: Int,
    @SerializedName("response") val teams: List<Team>
)

data class Team(
    @SerializedName("team") val details: Details,
    val venue: Venue
)

@Parcelize
data class Details(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String,
    // @SerializedName("vote_count") val voteCount: Int
) : Parcelable

@Parcelize
data class Venue(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val surface: String,
    val image: String,
) : Parcelable