package com.example.workmanager.models

data class QuoteList (
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val quotes: List<Quote>,
    val totalCount: Int,
    val totalPages: Int
)