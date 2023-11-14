package edu.phystech.kotlin.backend.repository.user.model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object: EntityClass<String, UserEntity>(UsersTable)

    var name by UsersTable.name
    var passwordHashed by UsersTable.passwordHashed
    var registrationTime by UsersTable.registrationTime
}