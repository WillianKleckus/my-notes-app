package com.kleckus.mynotes.ui.master

import androidx.lifecycle.ViewModel
import cafe.adriel.dalek.Dalek
import cafe.adriel.dalek.DalekEvent
import com.kleckus.mynotes.domain.models.Item
import com.kleckus.mynotes.domain.models.ModularItem
import com.kleckus.mynotes.domain.services.Logger
import com.kleckus.mynotes.domain.services.Storage
import kotlinx.coroutines.flow.Flow

class MasterBookViewModel(
    private val service : Storage,
    private val logger : Logger
) : ViewModel() {

    fun getItemById(id : String) : Flow<DalekEvent<Item>> =
        Dalek{ service.load(id) }

    fun getItemsFromIds(idList : List<String>) : Flow<DalekEvent<List<Item>>> =
        Dalek{ getItemListById(idList) }

    fun save(item : Item) : Flow<DalekEvent<Unit>> =
        Dalek{ service.save(item) }

    fun deleteById(id : String) : Flow<DalekEvent<Unit>> =
        Dalek{ service.delete(id) }

    fun getNextAvailableId() : Flow<DalekEvent<String>> =
        Dalek{ service.generateId() }

    private suspend fun getItemListById(idList : List<String>) : List<Item> =
        idList.map { service.load(it) }
}