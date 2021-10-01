package io.tolgee.api.v2.hateoas.uploadedImage

import io.tolgee.api.v2.controllers.translation.V2TranslationsController
import io.tolgee.component.TimestampValidation
import io.tolgee.configuration.tolgee.TolgeeProperties
import io.tolgee.model.UploadedImage
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component
import java.util.*

@Component
class UploadedImageModelAssembler(
  private val timestampValidation: TimestampValidation,
  private val tolgeeProperties: TolgeeProperties
) : RepresentationModelAssemblerSupport<UploadedImage, UploadedImageModel>(
  V2TranslationsController::class.java, UploadedImageModel::class.java
) {
  override fun toModel(entity: UploadedImage): UploadedImageModel {
    var filename = entity.filenameWithExtension
    if (tolgeeProperties.authentication.securedImageRetrieval) {
      filename = filename + "?timestamp=" + timestampValidation.encryptTimeStamp(Date().time)
    }
    return UploadedImageModel(
      id = entity.id,
      requestFilename = filename,
      filename = entity.filename,
      createdAt = entity.createdAt!!
    )
  }
}