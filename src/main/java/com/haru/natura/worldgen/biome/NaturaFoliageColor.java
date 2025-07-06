package com.haru.natura.worldgen.biome;

public class NaturaFoliageColor {
    private static int[] pixels = new int[65536];
    public static int get(double pTemperature, double pHumidity) {
        pHumidity *= pTemperature;
        int i = (int)((1.0D - pTemperature) * 255.0D);
        int j = (int)((1.0D - pHumidity) * 255.0D);
        int k = j << 8 | i;
        return k >= pixels.length ? getDefaultColor() : pixels[k];
    }

    public static int getMapleColor() {
        return 0xcc5718;
    }

    public static int getDarkenColor() {
        return 0x222222;
    }
    public static int getDefaultColor() {
        return 4764952;
    }
}
