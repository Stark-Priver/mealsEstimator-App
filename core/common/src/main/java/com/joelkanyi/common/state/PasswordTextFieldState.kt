
package com.joelkanyi.common.state

data class PasswordTextFieldState(
    val text: String = "",
    val error: String? = null,
    val isPasswordVisible: Boolean = true
)
