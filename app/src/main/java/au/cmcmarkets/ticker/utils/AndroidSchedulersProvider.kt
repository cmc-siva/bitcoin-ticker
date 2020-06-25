package au.cmcmarkets.ticker.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sivaraj on 25/6/20.
 */
class AndroidSchedulersProvider : SchedulersProvider {
    override fun mainUiThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun immediate(): Scheduler {
        return Schedulers.trampoline()
    }
}