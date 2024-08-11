package com.mywordsbook.db

fun Word.toWordBackend() =
    WordBackend(
        wording = wording,
        meaning = meaning,
        id = id,
        isImportant = isImportant,
        createdDateTime = createdDateTime
    )


fun WordBackend.toWord() =
    Word(
        wording = wording,
        meaning = meaning,
        id = id,
        isImportant = isImportant,
        createdDateTime = createdDateTime, isSynced = true
    )