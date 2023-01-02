package com.jd.dogapp.api.dto

import com.jd.dogapp.models.Dog

class DogDTOMapper
{
    fun fromDogDTOtoDogDomain(dogDTO: DogDTO): Dog
    {
        return Dog(dogDTO.id, dogDTO.index, dogDTO.name, dogDTO.type, dogDTO.heightFemale,
            dogDTO.heightMale, dogDTO.imageUrl, dogDTO.lifeExpectancy, dogDTO.temperament,
            dogDTO.weightFemale, dogDTO.weightMale)
    }

    fun fromDogDTOListtoDogDomainList(dogDTOList: List<DogDTO>): List<Dog>
    {
        return dogDTOList.map { fromDogDTOtoDogDomain(it) }
    }
}