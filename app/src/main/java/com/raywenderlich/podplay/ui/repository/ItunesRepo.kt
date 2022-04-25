package com.raywenderlich.podplay.ui.repository

import com.raywenderlich.podplay.ui.service.ItunesService

class ItunesRepo(private val itunesService: ItunesService) {

    suspend fun searchByTerm(term: String) = itunesService.searchPodcastByTerm(term)

}