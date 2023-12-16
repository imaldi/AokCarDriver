package com.surelabsid.mrjempootdriver.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public final class HourToMillis {
    @NotNull
    public static final HourToMillis INSTANCE;

    @NotNull
    public final String today() {
        return this.millisToDate(this.millis());
    }

    public final long millis() {
        Calendar time = Calendar.getInstance(Locale.ENGLISH);
        Intrinsics.checkNotNullExpressionValue(time, "time");
        return time.getTimeInMillis();
    }




    private final String millisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(millis);
        String var10000 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10000, "sdf.format(c.time)");
        return var10000;
    }

    @NotNull
    public final String millisToDateHour(@NotNull Long millis, @Nullable String p) {
        String pattern = "yyyy-MM-ddd HH:mm:ss";
        if(p != null)
            pattern = p;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        Intrinsics.checkNotNull(millis);
        c.setTimeInMillis(millis);
        String var4 = "Time here " + sdf.format(c.getTime());
        boolean var5 = false;
        System.out.println(var4);
        String var10000 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10000, "sdf.format(c.time)");
        return var10000;
    }

    @NotNull
    public final String millisToMonth(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(millis);
        String var10000 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10000, "sdf.format(c.time)");
        return var10000;
    }

    @NotNull
    public final String millisToHour(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(millis);
        String var10000 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10000, "sdf.format(c.time)");
        return var10000;
    }

    @NotNull
    public final String getDayOfDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(millis);
        String var10001 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10001, "sdf.format(c.time)");
        return this.getDay(var10001);
    }

    private final String getDay(String d) {
        boolean var3 = false;
        if (d == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        } else {
            String var10000 = d.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "(this as java.lang.Strin….toLowerCase(Locale.ROOT)");
            String var2 = var10000;
            switch(var2.hashCode()) {
                case 101661:
                    if (var2.equals("fri")) {
                        return "Jumat";
                    }
                    break;
                case 108300:
                    if (var2.equals("mon")) {
                        return "Senin";
                    }
                    break;
                case 113638:
                    if (var2.equals("sat")) {
                        return "Sabtu";
                    }
                    break;
                case 114252:
                    if (var2.equals("sun")) {
                        return "Minggu";
                    }
                    break;
                case 114817:
                    if (var2.equals("thu")) {
                        return "Kamis";
                    }
                    break;
                case 115204:
                    if (var2.equals("tue")) {
                        return "Selasa";
                    }
                    break;
                case 117590:
                    if (var2.equals("wed")) {
                        return "Rabu";
                    }
            }

            return "";
        }
    }

    @NotNull
    public final String millisToCustomFormat(long millis, @NotNull String format) {
        Intrinsics.checkNotNullParameter(format, "format");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(millis);
        String var10000 = sdf.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10000, "sdf.format(c.time)");
        return var10000;
    }

    // $FF: synthetic method
    public static String millisToCustomFormat$default(HourToMillis var0, long var1, String var3, int var4, Object var5) {
        if ((var4 & 2) != 0) {
            var3 = "dd/MM/yyyy HH:mm";
        }

        return var0.millisToCustomFormat(var1, var3);
    }

    @Nullable
    public final Long dateHourToMillis(@NotNull String date) {
        Intrinsics.checkNotNullParameter(date, "date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Long millis = (Long)null;

        try {
            Date mDate = sdf.parse(date);
            millis = mDate != null ? mDate.getTime() : null;
            Log.d("DATE", String.valueOf(millis));
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return millis;
    }

    @NotNull
    public final String fullDate(long milis) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        Intrinsics.checkNotNullExpressionValue(c, "c");
        c.setTimeInMillis(milis);
        String var10001 = monthFormat.format(c.getTime());
        Intrinsics.checkNotNullExpressionValue(var10001, "monthFormat.format(c.time)");
        String day = var10001;
        boolean var9 = false;
        String month = this.selectedMonth(Integer.parseInt(day) - 1);
        day = dayFormat.format(c.getTime());
        String year = yearFormat.format(c.getTime());
        return day + ' ' + month + ' ' + year;
    }

    @NotNull
    public final String selectedMonth(int month) {
        boolean var3 = false;
        List monthList = (List)(new ArrayList());
        monthList.add("Januari");
        monthList.add("Februari");
        monthList.add("Maret");
        monthList.add("April");
        monthList.add("Mei");
        monthList.add("Juni");
        monthList.add("Juli");
        monthList.add("Agustus");
        monthList.add("September");
        monthList.add("Oktober");
        monthList.add("November");
        monthList.add("Desember");
        return (String)monthList.get(month);
    }

    public HourToMillis() {
    }

    static {
        HourToMillis var0 = new HourToMillis();
        INSTANCE = var0;
    }
}
