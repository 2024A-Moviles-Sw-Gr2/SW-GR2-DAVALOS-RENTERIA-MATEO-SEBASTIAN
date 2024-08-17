package com.example.deber_02.base_datos

import android.content.Context
import com.example.deber_02.helpers.ESqliteHelperCaracteristica
import com.example.deber_02.helpers.ESqliteHelperPlaneta

class EBaseDeDatos {
    companion object {
        var tablaPlaneta: ESqliteHelperPlaneta? = null
        var tablaCaracteristica: ESqliteHelperCaracteristica? = null


    }
}
