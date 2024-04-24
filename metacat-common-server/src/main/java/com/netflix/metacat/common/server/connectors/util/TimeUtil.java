/*
 *
 *  Copyright 2017 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.netflix.metacat.common.server.connectors.util;

import java.util.concurrent.TimeUnit;

/**
 * TimeUtil.
 *
 * @author zhenl
 * @since 1.0.0
 */
public final class TimeUtil {
    private TimeUtil() {
    }

    /**
     * unitFor.
     *
     * @param inputUnit   inputUnit
     * @param defaultUnit defaultUnit
     * @return TimeUnit
     */
    public static TimeUnit unitFor(final String inputUnit, final TimeUnit defaultUnit) {
        final String unit = inputUnit.trim().toLowerCase();
        if (unit.isEmpty() || "l".equals(unit)) {
            if (defaultUnit == null) {
                throw new IllegalArgumentException("Time unit is not specified");
            }
            return defaultUnit;
        } else if ("d".equals(unit) || unit.startsWith("day")) {
            return TimeUnit.DAYS;
        } else if ("h".equals(unit) || unit.startsWith("hour")) {
            return TimeUnit.HOURS;
        } else if ("m".equals(unit) || unit.startsWith("min")) {
            return TimeUnit.MINUTES;
        } else if ("s".equals(unit) || unit.startsWith("sec")) {
            return TimeUnit.SECONDS;
        } else if ("ms".equals(unit) || unit.startsWith("msec")) {
            return TimeUnit.MILLISECONDS;
        } else if ("us".equals(unit) || unit.startsWith("usec")) {
            return TimeUnit.MICROSECONDS;
        } else if ("ns".equals(unit) || unit.startsWith("nsec")) {
            return TimeUnit.NANOSECONDS;
        }
        throw new IllegalArgumentException("Invalid time unit " + unit);
    }

    /**
     * toTime.
     *
     * @param value     value
     * @param inputUnit inputUnit
     * @param outUnit   outUnit
     * @return long
     */
    public static long toTime(final String value, final TimeUnit inputUnit, final TimeUnit outUnit) {
        final String[] parsed = parseTime(value.trim());
        return outUnit.convert(Long.parseLong(parsed[0].trim().trim()), unitFor(parsed[1].trim(), inputUnit));
    }

    private static String[] parseTime(final String value) {
        final char[] chars = value.toCharArray();
        int i = 0;
        while (i < chars.length && (chars[i] == '-' || Character.isDigit(chars[i]))) {
            i++;
        }
        return new String[]{value.substring(0, i), value.substring(i)};
    }
}
