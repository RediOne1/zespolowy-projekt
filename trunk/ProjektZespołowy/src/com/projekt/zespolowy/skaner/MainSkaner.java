package com.projekt.zespolowy.skaner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.projekt.zespolowy.R;

import java.io.ByteArrayOutputStream;

public class MainSkaner extends Activity implements OnClickListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int REQ_CODE_PICK_IMAGE_SCAN = 101;
    private static final int REQ_CODE_PICK_IMAGE_BASE = 102;
    private static final int IMAGE_SIZE = 250;

    private ImageView shareBtn, img_z_bazy, img_skanowane;
    private View baseBtn, scanBtn;
    private Bitmap z_bazy, skanowane;
    private EditText seed_toSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_skaner);
        shareBtn = (ImageView) findViewById(R.id.share_seed);
        seed_toSent = (EditText) findViewById(R.id.seed_toSent);
        img_z_bazy = (ImageView) findViewById(R.id.img_z_bazy);
        img_skanowane = (ImageView) findViewById(R.id.img_skanowane);
        baseBtn = (View) findViewById(R.id.base_img_layout);
        scanBtn = (View) findViewById(R.id.scan_img_layout);

        scanBtn.setOnClickListener(this);
        baseBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
    }

    /**
     * Tworzy menu kontekstowe na podstawie szablonu z pliku xml.
     * @param menu
     * @param v Obiekt który został przytrzymany, lub który wywołał menu kontekstowe.
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == scanBtn) {
            getMenuInflater().inflate(R.menu.scan_context, menu);
            menu.setHeaderTitle(R.string.wybierz_opcje);
        }
    }

    /**
     * Funkcja wywoływana po kliknieciu obiektu w menu kontekstowym
     * @param item obiekt wybrany w menu kontekstowym
     * @return zwraca prawde jesli wybrany obiekt wywołał jakąś funkcję
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        boolean result = false;
        Intent intent;
        switch (item.getItemId()) {
            case R.id.capture_img:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                result = true;
                break;
            case R.id.pick_img:
                intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_PICK_IMAGE_SCAN);
                result = true;
                break;
        }
        return result;
    }

    /**
     * Funkcja która odbiera wynik wywołanych aktywności, w naszym przypdaku wywołujemy galerie lub aparat.
     * @param requestCode dowolny kod, który przypisujemy do wywołanej aktywności żeby później wiedzieć który wynik dotyczy którego wywołania
     * @param resultCode kod zwracany razem z wynikiem wywołania aktywności, informuje nas jaki był rezultat wywołania.
     * @param data dane wynikowe wywołania
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE_SCAN:
            case REQ_CODE_PICK_IMAGE_BASE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, o);
                    final int REQUIRED_SIZE = IMAGE_SIZE;
                    int width_tmp = o.outWidth, height_tmp = o.outHeight;
                    int scale = 1;
                    while (true) {
                        if (width_tmp / 2 < REQUIRED_SIZE
                                || height_tmp / 2 < REQUIRED_SIZE)
                            break;
                        width_tmp /= 2;
                        height_tmp /= 2;
                        scale *= 2;
                    }
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    Bitmap yourSelectedImage2 = BitmapFactory.decodeFile(filePath,
                            o2);
                    if (requestCode == REQ_CODE_PICK_IMAGE_BASE) {
                        img_z_bazy.setImageBitmap(yourSelectedImage2);
                        z_bazy = yourSelectedImage2;
                    } else {
                        img_skanowane.setImageBitmap(yourSelectedImage2);
                        skanowane = yourSelectedImage2;
                    }
                }
                break;

            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    byte[] byteArray = stream.toByteArray();
                    //TODO Zapisywanie zdjęcia z tablicy bitów(byteArray) w dobrej jakości, bo teraz zapisana jest tylko miniaturka.

                    img_skanowane.setImageBitmap(bmp);
                    skanowane = bmp;

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                break;
        }
    }

    /**
     * Funkcja wywołują aktywność w celu wysłania tekstu z pola "seed do wysłania"
     */
    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, seed_toSent.getText().toString());
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Seed");
        startActivity(Intent.createChooser(sharingIntent, "Wyślij za pomocą"));
    }

    @Override
    public void onClick(View v) {
        if (v == shareBtn)
            share();
        else if (v == baseBtn) {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_PICK_IMAGE_BASE);
        } else if(v == scanBtn)
            openContextMenu(v);
    }
}
