package com.jd.dogapp.api.dto

import com.jd.dogapp.models.User

class UserDTOMapper
{
    fun fromUserDTOtoUserDomain(userDTO: UserDTO): User
    {
        return User(userDTO.id, userDTO.email, userDTO.authenticationToken)
    }
}