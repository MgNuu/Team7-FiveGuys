package org.softeer.robocar.data.repository.CarPool

import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools

interface CarPoolRepository {

    suspend fun getCarPoolList(
        startLocation: String,
        destinationLocation: String,
        countOfMen: Int,
        countOfFemale: Int,
    ): CarPools

    suspend fun requestCarPool(
        carPool: CarPool
    ): Result<Unit>

    suspend fun registerCarPool(
        request: registerCarPoolRequest
    ): Result<Unit>

}