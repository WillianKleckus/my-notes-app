package com.kleckus.mynotes.database

import android.content.Context
import com.kleckus.mynotes.system.*
import com.kleckus.mynotes.system.Util.Companion.log
import io.paperdb.Paper
import java.lang.Exception

enum class DatabaseKeys(val key : String){
    MASTER_BOOK("master-book-key")
}
enum class TopLevelBooks(val key : String){
    MAIN_BOOK("main-book-key")
}

class Database{
    companion object{
        fun initializeDatabase(context: Context){ Paper.init(context) }

        fun loadState() : Promise<Pair<Boolean,MasterBook>>{
            val ret = Promise<Pair<Boolean,MasterBook>>()
            var success = false
            var masterBook = BAD_MASTER_BOOK
            Async{
                try {
                    val newBook = MasterBook(mutableListOf(), 0)
                    masterBook = Paper.book(TopLevelBooks.MAIN_BOOK.key).read<MasterBook>(DatabaseKeys.MASTER_BOOK.key, newBook)
                    success = true
                }
                catch (e : Exception) {
                    success = false
                    log("Could not load previous state: ${e.message.toString()}")
                }
            }.andThen { ret.complete(Pair(success,masterBook)) }
            return ret
        }

        fun saveState() : Promise<Boolean>{
            val ret = Promise<Boolean>()
            var success = false
            Async{
                try {
                    Paper.book(TopLevelBooks.MAIN_BOOK.key).write(DatabaseKeys.MASTER_BOOK.key, MyNotesSystem.accessMasterBook())
                    success = true
                }
                catch (e : Exception){
                    success = false
                    log("Could not save current state: ${e.message.toString()}")
                }
            }.andThen { ret.complete(success) }
            return ret
        }
    }
}