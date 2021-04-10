package com.pankaj.encora.interfaces

interface HomeInterface {
    fun showError(errorTitle: String, errorMessage: String)
    fun updateData(value: Boolean = true)
}