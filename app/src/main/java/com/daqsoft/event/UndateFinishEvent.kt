package com.daqsoft.event

/**
 * @Description
 * @ClassName   RefreshHomeEvent
 * @Author      luoyi
 * @Time        2020/4/30 11:44
 */
class UndateFinishEvent {
    var success:Boolean?=false

    constructor(success: Boolean?) {
        this.success = success
    }
}