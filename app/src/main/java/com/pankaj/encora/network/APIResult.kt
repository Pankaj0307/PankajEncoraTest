package com.pankaj.encora.network

/*class to check if api response is success or failure*/
data class APIResult<out T>(var status: Status, val data: T?, var message: String? = "") {
    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): APIResult<T> {
            return APIResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): APIResult<T> {
            return APIResult(Status.ERROR, data, message)
        }
    }
}