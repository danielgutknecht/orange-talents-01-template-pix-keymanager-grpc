package br.com.zup.pix.exception.types

import java.lang.RuntimeException

open class CustomException(message: String): RuntimeException(message)