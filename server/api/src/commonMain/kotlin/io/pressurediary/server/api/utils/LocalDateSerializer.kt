package io.pressurediary.server.api.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor(
        serialName = "LocalDate",
        kind = PrimitiveKind.STRING,
    )

    override fun deserialize(
        decoder: Decoder,
    ): LocalDate = LocalDate.parse(decoder.decodeString())

    override fun serialize(
        encoder: Encoder,
        value: LocalDate,
    ) = encoder.encodeString(value.toString())
}