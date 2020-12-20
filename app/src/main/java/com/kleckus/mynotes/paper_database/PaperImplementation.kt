package com.kleckus.mynotes.paper_database

import com.kleckus.mynotes.domain.services.PaperDatabase
import io.paperdb.Paper

class PaperImplementation : PaperDatabase {
    override suspend fun save(key: String, item: Any) {
        Paper.book().write(key,item)
    }

    override suspend fun load(key: String): Any =
        Paper.book().read<Any>(key)

    override suspend fun delete(key: String) {
        Paper.book().delete(key)
    }
}