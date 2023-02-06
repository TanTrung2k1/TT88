package com.hammer.tt88;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar sbFCar, sbSCar, sbTCar;
    CheckBox dfirstCar, dsecondCar, dthirdCar;
    EditText edBetAmount;
    TextView tvSoDu;
    TextView tvTienCuoc;
    TextView tvXeCuoc;
    Button btnStart;
    Button btnClickBet;
    Button btnConfirmBet;
    String carWin;
    String message;

    private void getAction() {

        sbFCar = findViewById(R.id.sbFCar);
        sbSCar = findViewById(R.id.sbSCar);
        sbTCar = findViewById(R.id.sbTCar);

        tvSoDu = findViewById(R.id.tvSodu);
        tvTienCuoc = findViewById(R.id.tvTienCuoc);
        tvXeCuoc = findViewById(R.id.tvBet_Car);

        btnClickBet = findViewById(R.id.btn_click_bet);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setEnabled(false);

    }

    private int randomNumber() {
        int result = 5;
        Random random = new Random();
        //return number 0 -> result
        result = random.nextInt(result);
        return result;
    }

    private void openPopupBetAmount() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_bet);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);

        dfirstCar = dialog.findViewById(R.id.firstCar);
        dsecondCar = dialog.findViewById(R.id.secondCar);
        dthirdCar = dialog.findViewById(R.id.thirdCar);

        edBetAmount = dialog.findViewById(R.id.betAmount);

        btnConfirmBet = dialog.findViewById(R.id.btn_confirm_bet);


        dfirstCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dsecondCar.setChecked(false);
                dthirdCar.setChecked(false);

            }
        });

        dsecondCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                dfirstCar.setChecked(false);
                dthirdCar.setChecked(false);

            }
        });

        dthirdCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                dfirstCar.setChecked(false);
                dsecondCar.setChecked(false);

            }
        });


        btnConfirmBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dfirstCar.isChecked() || dsecondCar.isChecked() || dthirdCar.isChecked()) {
                    if (checkBetAmount()) {
                        if (dfirstCar.isChecked()) {
                            tvTienCuoc.setText(edBetAmount.getText().toString());
                            tvXeCuoc.setText("Green car");
                        }
                        if (dsecondCar.isChecked()) {
                            tvTienCuoc.setText(edBetAmount.getText().toString());
                            tvXeCuoc.setText("Yellow car");
                        }
                        if (dthirdCar.isChecked()) {
                            tvTienCuoc.setText(edBetAmount.getText().toString());
                            tvXeCuoc.setText("Blue car");
                        }

                        dialog.dismiss();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng chọn xe để đặt cược", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private boolean checkBetAmount() {
        if (TextUtils.isEmpty(edBetAmount.getText().toString())) {
            edBetAmount.setError("Vui lòng nhập số tiền cược");
            return false;
        }
        if (Integer.parseInt(tvSoDu.getText().toString()) < Integer.parseInt(edBetAmount.getText().toString())) {
            edBetAmount.setError("Số dư không đủ");
            return false;
        }
        return true;
    }

    private void openPopupResult(int gravity, String win, String message) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_result);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = gravity;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(Gravity.BOTTOM == gravity);

        TextView carWin = dialog.findViewById(R.id.carWin);
        carWin.setText(win);

        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        Button btnConfirm = dialog.findViewById(R.id.confirm_result);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAction();
        String money = "10000";
        String bet_money = "0";
        tvSoDu.setText(money);
        tvTienCuoc.setText(bet_money);

        btnClickBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopupBetAmount();
                btnStart.setEnabled(true);
            }
        });

        //thời gian chạy hết khoản đường đó: 30s, mỗi lần di chuyển cách 0.1s
        CountDownTimer countDownTimer = new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (sbFCar.getProgress() >= sbFCar.getMax()) {
                    this.cancel(); //countDownTimer
                    carWin = "Green car win";
                    if (dfirstCar.isChecked()) {
                        message = "Bạn đã thắng";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) + Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));
                    } else {
                        message = "Bạn đã thua";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) - Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));
                    }
                    openPopupResult(Gravity.CENTER, carWin, message);
                }

                if (sbSCar.getProgress() >= sbSCar.getMax()) {
                    this.cancel(); //countDownTimer
                    carWin = "Yellow car win";

                    if (dsecondCar.isChecked()) {
                        message = "Bạn đã thắng";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) + Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));
                    } else {
                        message = "Bạn đã thua";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) - Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));

                    }
                    openPopupResult(Gravity.CENTER, carWin, message);
                }

                if (sbTCar.getProgress() >= sbTCar.getMax()) {
                    this.cancel(); //countDownTimer
                    carWin = "Blue car win";

                    if (dthirdCar.isChecked()) {
                        message = "Bạn đã thắng";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) + Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));
                    } else {
                        message = "Bạn đã thua";
                        int money = Integer.parseInt(tvSoDu.getText().toString()) - Integer.parseInt(tvTienCuoc.getText().toString());
                        tvSoDu.setText(Integer.toString(money));
                    }
                    openPopupResult(Gravity.CENTER, carWin, message);
                }


                sbFCar.setProgress(sbFCar.getProgress() + randomNumber());
                sbSCar.setProgress(sbSCar.getProgress() + randomNumber());
                sbTCar.setProgress(sbTCar.getProgress() + randomNumber());
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(tvSoDu.getText().toString()) < Integer.parseInt(tvTienCuoc.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Số dư không đủ", Toast.LENGTH_SHORT).show();
                } else {
                    sbFCar.setProgress(0);
                    sbSCar.setProgress(0);
                    sbTCar.setProgress(0);

                    countDownTimer.start();
                }
            }
        });


    }
}