package com.picpay.desafio.android.ui.users_list.view

sealed class UiAction {
    object RefreshUsers : UiAction()
    object LoadUsers : UiAction()
}