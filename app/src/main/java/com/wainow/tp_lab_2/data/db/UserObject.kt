package com.wainow.tp_lab_2.data.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserObject(
    @PrimaryKey
    var id: Int = 0,
    var currentNumber: Int = 0,
    val availableOperations: RealmList<String> = RealmList(),
    val availableNumbers: RealmList<Int> = RealmList(),
    var result: String = ""
): RealmObject()