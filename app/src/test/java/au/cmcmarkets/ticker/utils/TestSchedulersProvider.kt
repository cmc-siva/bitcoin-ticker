package au.cmcmarkets.ticker.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sivaraj on 25/6/20.
 */

open class TestSchedulersProvider : SchedulersProvider {
    override fun mainUiThread(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun immediate(): Scheduler {
        return Schedulers.trampoline()
    }
}
