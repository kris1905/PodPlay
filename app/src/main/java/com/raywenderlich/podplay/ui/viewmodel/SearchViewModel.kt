package com.raywenderlich.podplay.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raywenderlich.podplay.ui.repository.ItunesRepo
import com.raywenderlich.podplay.ui.service.PodcastResponse
import com.raywenderlich.podplay.ui.util.DateUtils

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    var iTunesRepo: ItunesRepo? = null

    data class PodcastSummaryViewData(
        var name: String? = "",
        var lastUpdated: String? = "",
        var imageUrl: String? = "",
        var feedUrl: String? = "")

    private fun itunesPodcastToPodcastSummaryView(
        itunesPodcast: PodcastResponse.ItunesPodcast):
            PodcastSummaryViewData {
        return PodcastSummaryViewData(
            itunesPodcast.collectionCensoredName,
            DateUtils.jsonDateToShortDate(itunesPodcast.releaseDate),
            itunesPodcast.artworkUrl30,
            itunesPodcast.feedUrl)
    }

    suspend fun searchPodcasts(term: String): List<PodcastSummaryViewData> {
        val results = iTunesRepo?.searchByTerm(term)

        if (results != null && results.isSuccessful) {
            val podcasts = results.body()?.results
            if (!podcasts.isNullOrEmpty()) {
                return podcasts.map { podcast ->
                    itunesPodcastToPodcastSummaryView(podcast)
                }
            }
        }
        return emptyList()
    }
}