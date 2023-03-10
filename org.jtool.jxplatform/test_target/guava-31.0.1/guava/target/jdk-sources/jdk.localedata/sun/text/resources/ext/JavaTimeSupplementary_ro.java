/*
 * Copyright (c) 2013, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * COPYRIGHT AND PERMISSION NOTICE
 *
 * Copyright (C) 1991-2016 Unicode, Inc. All rights reserved.
 * Distributed under the Terms of Use in
 * http://www.unicode.org/copyright.html.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of the Unicode data files and any associated documentation
 * (the "Data Files") or Unicode software and any associated documentation
 * (the "Software") to deal in the Data Files or Software
 * without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, and/or sell copies of
 * the Data Files or Software, and to permit persons to whom the Data Files
 * or Software are furnished to do so, provided that
 * (a) this copyright and permission notice appear with all copies
 * of the Data Files or Software,
 * (b) this copyright and permission notice appear in associated
 * documentation, and
 * (c) there is clear notice in each modified Data File or in the Software
 * as well as in the documentation associated with the Data File(s) or
 * Software that the data or software has been modified.
 *
 * THE DATA FILES AND SOFTWARE ARE PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT OF THIRD PARTY RIGHTS.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR HOLDERS INCLUDED IN THIS
 * NOTICE BE LIABLE FOR ANY CLAIM, OR ANY SPECIAL INDIRECT OR CONSEQUENTIAL
 * DAMAGES, OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THE DATA FILES OR SOFTWARE.
 *
 * Except as contained in this notice, the name of a copyright holder
 * shall not be used in advertising or otherwise to promote the sale,
 * use or other dealings in these Data Files or Software without prior
 * written authorization of the copyright holder.
 */

//  Note: this file has been generated by a tool.

package sun.text.resources.ext;

import sun.util.resources.OpenListResourceBundle;

public class JavaTimeSupplementary_ro extends OpenListResourceBundle {
    @Override
    protected final Object[][] getContents() {
        final String[] sharedQuarterAbbreviations = {
            "trim. I",
            "trim. II",
            "trim. III",
            "trim. IV",
        };

        final String[] sharedQuarterNames = {
            "trimestrul I",
            "trimestrul al II-lea",
            "trimestrul al III-lea",
            "trimestrul al IV-lea",
        };

        final String[] sharedAmPmMarkers = {
            "a.m.",
            "p.m.",
        };

        final String[] sharedDatePatterns = {
            "EEEE, d MMMM y GGGG",
            "d MMMM y GGGG",
            "dd.MM.y GGGG",
            "dd.MM.y G",
        };

        final String[] sharedDayAbbreviations = {
            "dum.",
            "lun.",
            "mar.",
            "mie.",
            "joi",
            "vin.",
            "s\u00e2m.",
        };

        final String[] sharedDayNames = {
            "duminic\u0102",
            "luni",
            "mar\u021bi",
            "miercuri",
            "joi",
            "vineri",
            "s\u00e2mb\u0102t\u0102",
        };

        final String[] sharedDayNarrows = {
            "D",
            "L",
            "M",
            "M",
            "J",
            "V",
            "S",
        };

        final String[] sharedJavaTimeDatePatterns = {
            "EEEE, d MMMM y G",
            "d MMMM y G",
            "dd.MM.y G",
            "dd.MM.y GGGGG",
        };

        return new Object[][] {
            { "QuarterAbbreviations",
                sharedQuarterAbbreviations },
            { "QuarterNames",
                sharedQuarterNames },
            { "calendarname.buddhist",
                "calendar budist" },
            { "calendarname.gregorian",
                "calendar gregorian" },
            { "calendarname.gregory",
                "calendar gregorian" },
            { "calendarname.islamic",
                "calendar islamic" },
            { "calendarname.islamic-civil",
                "calendar islamic civil" },
            { "calendarname.japanese",
                "calendar japonez" },
            { "calendarname.roc",
                "calendarul Republicii Chineze" },
            { "field.dayperiod",
                "a.m/p.m." },
            { "field.era",
                "Er\u0102" },
            { "field.hour",
                "Or\u0102" },
            { "field.minute",
                "Minut" },
            { "field.month",
                "Lun\u0102" },
            { "field.second",
                "Secund\u0102" },
            { "field.week",
                "S\u0102pt\u0102m\u00e2n\u0102" },
            { "field.weekday",
                "Zi a s\u0102pt\u0102m\u00e2nii" },
            { "field.year",
                "An" },
            { "field.zone",
                "Fus orar" },
            { "islamic.AmPmMarkers",
                sharedAmPmMarkers },
            { "islamic.DatePatterns",
                sharedDatePatterns },
            { "islamic.DayAbbreviations",
                sharedDayAbbreviations },
            { "islamic.DayNames",
                sharedDayNames },
            { "islamic.DayNarrows",
                sharedDayNarrows },
            { "islamic.QuarterAbbreviations",
                sharedQuarterAbbreviations },
            { "islamic.QuarterNames",
                sharedQuarterNames },
            { "islamic.abbreviated.AmPmMarkers",
                sharedAmPmMarkers },
            { "islamic.narrow.AmPmMarkers",
                sharedAmPmMarkers },
            { "java.time.buddhist.DatePatterns",
                sharedJavaTimeDatePatterns },
            { "java.time.buddhist.long.Eras",
                new String[] {
                    "BC",
                    "era budist\u0102",
                }
            },
            { "java.time.buddhist.short.Eras",
                new String[] {
                    "BC",
                    "e.b.",
                }
            },
            { "java.time.islamic.DatePatterns",
                sharedJavaTimeDatePatterns },
            { "java.time.japanese.DatePatterns",
                sharedJavaTimeDatePatterns },
            { "java.time.long.Eras",
                new String[] {
                    "\u00eenainte de Hristos",
                    "dup\u0102 Hristos",
                }
            },
            { "java.time.roc.DatePatterns",
                sharedJavaTimeDatePatterns },
            { "java.time.short.Eras",
                new String[] {
                    "d.C.",
                    "\u00ee.d.C.",
                }
            },
            { "roc.AmPmMarkers",
                sharedAmPmMarkers },
            { "roc.DatePatterns",
                sharedDatePatterns },
            { "roc.DayAbbreviations",
                sharedDayAbbreviations },
            { "roc.DayNames",
                sharedDayNames },
            { "roc.DayNarrows",
                sharedDayNarrows },
            { "roc.MonthAbbreviations",
                new String[] {
                    "ian.",
                    "feb.",
                    "mar.",
                    "apr.",
                    "mai",
                    "iun.",
                    "iul.",
                    "aug.",
                    "sept.",
                    "oct.",
                    "nov.",
                    "dec.",
                    "",
                }
            },
            { "roc.MonthNames",
                new String[] {
                    "ianuarie",
                    "februarie",
                    "martie",
                    "aprilie",
                    "mai",
                    "iunie",
                    "iulie",
                    "august",
                    "septembrie",
                    "octombrie",
                    "noiembrie",
                    "decembrie",
                    "",
                }
            },
            { "roc.MonthNarrows",
                new String[] {
                    "I",
                    "F",
                    "M",
                    "A",
                    "M",
                    "I",
                    "I",
                    "A",
                    "S",
                    "O",
                    "N",
                    "D",
                    "",
                }
            },
            { "roc.QuarterAbbreviations",
                sharedQuarterAbbreviations },
            { "roc.QuarterNames",
                sharedQuarterNames },
            { "roc.abbreviated.AmPmMarkers",
                sharedAmPmMarkers },
            { "roc.narrow.AmPmMarkers",
                sharedAmPmMarkers },
        };
    }
}
