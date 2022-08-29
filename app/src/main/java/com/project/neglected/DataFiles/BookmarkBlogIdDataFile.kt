package com.project.neglected.DataFiles

data class BookmarkBlogIdDataFile(
    val MainBlogId: Int
) {
    fun getId(): Int {
        return MainBlogId
    }
}

