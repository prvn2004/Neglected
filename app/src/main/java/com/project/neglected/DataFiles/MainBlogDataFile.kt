package com.project.neglected.DataFiles

data class MainBlogDataFile(
    val MainBlogTitle: String? = "",
//    val UserImage: Int,
    val MainBlogCategory: String? = "",
    val MainBlogTime: String? = "",
//    val MainBlogId: Int

) {


    fun getTitle(): String? {
        return MainBlogTitle
    }

    fun getCategory(): String? {
        return MainBlogCategory
    }

    fun getTime(): String? {
        return MainBlogTime

    }

//    fun getId(): Int {
//        return MainBlogId
//    }


}
