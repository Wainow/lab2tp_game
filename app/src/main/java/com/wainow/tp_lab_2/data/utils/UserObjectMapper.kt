package com.wainow.tp_lab_2.data.utils

import com.wainow.tp_lab_2.data.db.UserObject
import com.wainow.tp_lab_2.domain.User
import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

object UserObjectMapper {
    fun map(user: User, realm: Realm): RealmObject =
        realm.createObject<UserObject>(user.id).apply {
            currentNumber = user.currentNumber
            result = user.result
            user.availableNumbers.forEach { availableNumbers.add(it) }
            user.availableOperations.forEach{ availableOperations.add(it.toString()) }
        }
}