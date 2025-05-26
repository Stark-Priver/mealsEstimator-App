
package com.joelkanyi.admeal.domain.usecase

import android.graphics.Bitmap
import com.joelkanyi.admeal.domain.repository.AddMealRepository
import javax.inject.Inject

class UploadMealImageUseCase @Inject constructor(
    private val addMealRepository: AddMealRepository
) {
    suspend operator fun invoke(
        imageBitmap: Bitmap
    ) = addMealRepository.uploadImage(
        imageBitmap
    )
}
