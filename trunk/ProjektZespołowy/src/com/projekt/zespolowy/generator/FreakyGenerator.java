package com.projekt.zespolowy.generator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Generator dzieli ekran na podstawie podanego ziarna dzieli ekran na prostokątne obszary, a następnie w kążdym z nich rsuje poziome lub pionowe linie.
 *
 * @author Adrian
 * @version 1.0
 */
public class FreakyGenerator extends Generator {

    private static final int MAX_ILOSC_OBSZAROW = 2000;
    /**
     * Zmienna okreslająca odstęp pomiędzy każdą z lini
     */
    private static final int GESTOSC_LINI = 2;
    private static final int KOLOR_LINII = Color.BLACK;
    /**
     * Zmienna określająca minimalna szerokość pojedynczego obszaru
     */
    private static final float MINIMALNA_SZEROKOSC_OBSZARU = 50;
    /**
     * Zmienna określająca minimalna wysokosc pojedynczego obszaru
     */
    private static final float MINIMALNA_WYSOKOSC_OBSZARU = 50;

    private static final long serialVersionUID = -1341784248038743032L;
    private Random r;
    private Paint paint;
    private List<Obszar> obszary;

    /**
     * Główna funkcja generująca obraz. W niej na podstawie podanych danych: {@link #KOLOR_LINII}, {@link #MAX_ILOSC_OBSZAROW}, dzielony jest ekran na obszary, a następnie wypełniany liniami.
     * Do dzielenia obszaru wykorzystano zaimplementowną funkcję {@link #split(Obszar o)}, która jako parametr przyjmuje obszar który chcemy podzielić.
     * Do wypełnienia obszaru wykorzystuje funkcję {@link com.projekt.zespolowy.generator.FreakyGenerator.Obszar#fill(boolean)}
     *
     * @param seed ziarno dla którego ma zostać wygenerowana bitmapa
     * @param w    szerokość rządanej bitmapy
     * @param h    wysokość rządanej bitmapy
     * @return wygenerowana bitmape
     */
    @Override
    public Bitmap generate(long seed, int w, int h) {
        init(w, h);
        paint = new Paint();
        paint.setColor(KOLOR_LINII);
        r = new Random(seed);
        obszary = new LinkedList<Obszar>();
        obszary.add(new Obszar(0, 0, w, h));
        int n = r.nextInt(MAX_ILOSC_OBSZAROW / 2);
        for (int i = 0; i < n; i++) {
            split(obszary.get(0));
        }
        for (Obszar o : obszary)
            o.fill(r.nextBoolean());
        return bitmap;
    }

    /**
     * Jeśli to możliwe dzieli podany obszar na dwa nowe i dodaje je do listy obszarów - obszary. W przeciwnym wypadku nie zmienia obszaru.
     * Algorytm sprawdza czy szerokosc i wysokosc ekranu jest większa niż 2 * {@link #MINIMALNA_SZEROKOSC_OBSZARU} i 2 * {@link #MINIMALNA_WYSOKOSC_OBSZARU},
     * następnie losuje nowe rozmiary obszaru, korzystając z zaimplementowanej funkcji {@link #randomFloat(float, float)}
     *
     * @param o Obszar który chcemy podzielic.
     */
    private void split(Obszar o) {
        Obszar o1 = null, o2 = null;
        if (o.getWidth() > o.getHeight()) {
            if (o.getWidth() > 2 * MINIMALNA_SZEROKOSC_OBSZARU) {
                float new_width = randomFloat(MINIMALNA_SZEROKOSC_OBSZARU,
                        o.getWidth() - MINIMALNA_SZEROKOSC_OBSZARU);
                o1 = new Obszar(o.startX, o.startY, o.startX + new_width,
                        o.stopY);
                o2 = new Obszar(o.startX + new_width, o.startY, o.stopX,
                        o.stopY);
            }
        } else {
            if (o.getHeight() > 2 * MINIMALNA_WYSOKOSC_OBSZARU) {
                float new_height = randomFloat(MINIMALNA_WYSOKOSC_OBSZARU,
                        o.getHeight() - MINIMALNA_WYSOKOSC_OBSZARU);
                o1 = new Obszar(o.startX, o.startY, o.stopX, o.startY
                        + new_height);
                o2 = new Obszar(o.startX, o.startY + new_height, o.stopX,
                        o.stopY);
            }
        }
        if (o1 != null && o2 != null) {
            obszary.remove(o);
            obszary.add(o1);
            obszary.add(o2);
        }
    }

    /**
     * Losowy float z przedziału (a, b)
     *
     * @param a minimalna wartość wylosowanej liczby
     * @param b maksymalna wartość wylosowanej liczby
     * @return wylosowna liczbę z przedziału (a, b)
     */
    private float randomFloat(float a, float b) {
        return r.nextFloat() * (b - a) + a;
    }

    @Override
    public String toString() {
        return "Adrian";
    }

    /**
     * Obiekt przechowujący dane o danym obszarze
     */
    private class Obszar {
        private float startX, startY, stopX, stopY;

        /**
         * Konstruktor obszaru
         *
         * @param startX Współrzędna X, lewego górnego rogu obszaru
         * @param startY Współrzędna Y, lewego górnego rogu obszaru
         * @param stopX  Współrzędna X, prawego dolnego rogu obszaru
         * @param stopY  Współrzędna Y, prawego dolnego rogu obszaru
         */
        private Obszar(float startX, float startY, float stopX, float stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }

        private float getWidth() {
            return stopX - startX;
        }

        private float getHeight() {
            return stopY - startY;
        }

        /**
         * Wypełnia dany obszar liniami poziomymi jeśli podany argument == true, lub pionowymi w przeciwnym wypadku.
         * Linie rysowane są w odstępach zadanych przez zmienną {@link #GESTOSC_LINI},
         * za pomocą funkcji {@link android.graphics.Canvas#drawLine(float, float, float, float, android.graphics.Paint)}
         *
         * @param poziomo parametr określający czy dany obszar ma być zapełniony liniami poziomymi czy pionowymi.
         */
        private void fill(boolean poziomo) {
            if (poziomo) {
                int n = (int) this.getHeight();
                for (int i = 0; i < n; i += GESTOSC_LINI)
                    canvas.drawLine(startX, startY + i, stopX, startY + i,
                            paint);
            } else {
                int n = (int) this.getWidth();
                for (int i = 0; i < n; i += GESTOSC_LINI)
                    canvas.drawLine(startX + i, startY, startX + i, stopY,
                            paint);
            }
        }
    }
}
