package com.andrstudy.myapplication;

import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QR_Scanner extends AppCompatActivity {

    TextView tvResult;
    SurfaceView cameraView;
    CameraSource cameraSource;
    Detector.Detections detections;
    Button btScan, btCancel;
    Boolean beep = false;
    SoundPool mPool;
    int mDdok;
    AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tv_result);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        btScan = (Button) findViewById(R.id.bt_scan);
        btCancel = (Button) findViewById(R.id.bt_canel);
        mPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        //mDdok = mPool.load(this, R.raw.ddok, 1); //직접 올린 사운드 사용시
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(QR_Scanner.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraView.refreshDrawableState();
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraSource.stop();
                //기본 화면으로 리셋하는 방법은???
                //cameraView.setBackgroundColor(Color.BLACK);
                tvResult.setText("");
                beep = false;
            }
        });

        //create barcode detector - 다른타입의 바코드도 가능
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 640)
                .build();

        //callback
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                //mPool.play(mDdok, 1, 1, 0, 0, 1); //직접 올린 효과음을 재생할때 사용
                if (barcodes.size() != 0) {
                    if(!beep) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                        beep = true;
                    }
                    tvResult.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            tvResult.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                }
            }
        });
    }
}
