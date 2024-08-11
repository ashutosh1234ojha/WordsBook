package com.mywordsbook

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mywordsbook.db.WordDatabase
import com.mywordsbook.di.RepositoryModule
import kotlinx.coroutines.supervisorScope

import javax.inject.Inject


class NetworkCallWorkManager(
    context: Context,
    userParameters: WorkerParameters,
) :
    CoroutineWorker(context, userParameters) {

    val firestore = Firebase.firestore

    val wordDatabase: WordDatabase = RepositoryModule.getWordDatabase(context)


    init {
        Log.d("Tag", "worker")
    }


    override suspend fun doWork(): Result {
        val firebaseUserId = inputData.getString("User_id") ?: ""

        val wordDao = wordDatabase.wordDao()

        return try {
            val allWords = wordDao?.fetchAllTasks()
            val collectionRef =
                firestore.collection("Users").document(firebaseUserId).collection("Words")



            allWords?.collect {
                it.forEach { entity ->
                    val document = collectionRef.document(entity.id.toString())
                    document.set(entity).addOnSuccessListener {
                        Log.d("Tag", "worker")

                    }.addOnFailureListener {
                        Log.d("Tag", "worker")

                    }.addOnCompleteListener {
                        Log.d("Tag", "worker")

                    }
                }
            }



            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
