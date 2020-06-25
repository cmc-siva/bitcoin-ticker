package au.cmcmarkets.ticker.utils

import io.reactivex.Scheduler

/**
 * Created by Sivaraj on 25/6/20.
 */

interface SchedulersProvider {
    fun mainUiThread(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun immediate(): Scheduler
}