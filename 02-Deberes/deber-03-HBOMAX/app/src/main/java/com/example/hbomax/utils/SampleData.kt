package com.example.hbomax.utils

import com.example.hbomax.model.ChildModel
import com.example.hbomax.model.ParentModel

object SampleData {

    private val movieModel= listOf(
        ChildModel(Images.imageUrl0),
        ChildModel(Images.imageUrl1),
        ChildModel(Images.imageUrl2),
        ChildModel(Images.imageUrl3),
        ChildModel(Images.imageUrl4),
        ChildModel(Images.imageUrl5),
        ChildModel(Images.imageUrl6),
        ChildModel(Images.imageUrl7),
        ChildModel(Images.imageUrl8),
        ChildModel(Images.imageUrl9)
    )

    val collections = listOf(
        ParentModel("Solo para tí" , movieModel),
        ParentModel("Continuar Viendo >" , movieModel.reversed()),
        ParentModel("Mi lista >" , movieModel.shuffled()),
        ParentModel("Recién Añadidos" , movieModel)

    )
}