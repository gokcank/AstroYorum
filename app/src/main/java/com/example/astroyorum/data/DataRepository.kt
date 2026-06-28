package com.example.astroyorum.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Eski şablon dosyası - artık kullanılmıyor
// Gerçek veriler ZodiacDatabase, TarotDatabase ve UserPreferencesRepository'de
interface DataRepository {
    val data: Flow<List<String>>
}

class DefaultDataRepository : DataRepository {
    override val data: Flow<List<String>> = flow { emit(listOf("AstroYorum")) }
}
