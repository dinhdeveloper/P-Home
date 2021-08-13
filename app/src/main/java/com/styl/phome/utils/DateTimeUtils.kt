/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */

package com.styl.phome.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {
        const val CHART_DATE_FORMAT = "dd/MM\nHH:mm:ss"
        const val DATE_FORMAT = "dd/MM/yyyy"
        const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"
        const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun formatDateFrom(
            format: String = SERVER_DATE_FORMAT,
            date: String?,
            timeZone: TimeZone? = getUTCTimeZone()
        ): Date? {
            date?.let {
                val sdf = SimpleDateFormat(format, Locale.ENGLISH)
                timeZone?.let {
                    sdf.timeZone = timeZone
                }
                return try {
                    sdf.parse(it)
                } catch (e: Exception) {
                    null
                }
            }
            return null
        }

        fun formatDateAsString(
            format: String? = DATE_FORMAT,
            time: Date?,
            timeZone: TimeZone? = null
        ): String {
            time?.let {
                val sdf = SimpleDateFormat(format, Locale.ENGLISH)
                timeZone?.let {
                    sdf.timeZone = it
                }
                return sdf.format(time)
            }
            return ""
        }

        fun formatDateAsLocalTimeZone(
            time: String?,
            format: String = DATE_TIME_FORMAT
        ): String? {
            time?.let {
                val originDate = formatDateFrom(date = time)
                return formatDateAsString(format, originDate, getLocalTimeZone())
            }
            return null
        }

        fun formatDateAsUtcTimeZone(time: Date?): String? {
            return formatUctTime(time, getUTCTimeZone())
        }

        fun getUTCTimeZone(): TimeZone {
            return TimeZone.getTimeZone("UTC")
        }

        private fun formatUctTime(
            time: Date?,
            timeZone: TimeZone
        ): String? {
            time?.let {
                val sdf = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH)
                sdf.timeZone = timeZone
                return sdf.format(time)
            }
            return null
        }

        private fun getLocalTimeZone(): TimeZone {
            return Calendar.getInstance().timeZone
        }

    }
}