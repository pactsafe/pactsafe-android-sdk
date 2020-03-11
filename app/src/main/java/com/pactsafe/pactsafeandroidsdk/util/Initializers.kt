package com.pactsafe.pactsafeandroidsdk.util

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T> injector(qualifier: Qualifier? = null, noinline parameters: ParametersDefinition? = null): T {
    return object : KoinComponent { val value: T by inject(qualifier, parameters) }.value
}