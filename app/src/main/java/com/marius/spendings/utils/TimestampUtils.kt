package com.marius.spendings.utils

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Converts a LocalDate to a Timestamp using UTC time-zone
 */
fun LocalDate.toTimestamp() = Timestamp(this.atStartOfDay(ZoneOffset.UTC).toEpochSecond(), 0)
