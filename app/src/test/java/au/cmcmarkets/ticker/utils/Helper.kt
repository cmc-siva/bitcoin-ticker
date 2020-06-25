package au.cmcmarkets.ticker.utils

import org.mockito.Mockito

/**
 * Created by Sivaraj on 25/6/20.
 */
object Helper {
    inline fun <reified T> lambdaMock(): T = Mockito.mock(T::class.java)
}