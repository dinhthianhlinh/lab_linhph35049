package com.example.asm_api_linhph35049;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void showToast(Context context, String messenger){
        Toast.makeText(context, messenger, Toast.LENGTH_SHORT).show();
    }
}
