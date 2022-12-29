package com.ezatpanah.flowdatabase.utils


data class DataStatus<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val isEmpty: Boolean? = false
) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        fun <T> loading(): DataStatus<T> {
            return DataStatus(Status.LOADING)
        }

        fun <T> success(data: T? ,isEmpty: Boolean?): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, isEmpty = isEmpty)
        }

        fun <T> error(error: String): DataStatus<T> {
            return DataStatus(Status.ERROR, message = error)
        }

    }
}
