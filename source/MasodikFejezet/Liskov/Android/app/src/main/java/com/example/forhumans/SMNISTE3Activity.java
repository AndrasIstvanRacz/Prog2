/*
 * SMNISTE1Activity.java
 *
 * SMNIST for Humans
 *
 * Copyright (C) 2019, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * https://bhaxor.blog.hu/2019/03/10/the_semantic_mnist
 */
package com.example.forhumans;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
public class SMNISTE3Activity extends AppCompatActivity {

    private boolean licenseAccepted = false;
    public static final String MS_FILE = "millisecs";
    public static final String SV_FILE = "svmeans";

    public void save(String file, int[] array){

        try{
            java.io.FileOutputStream fos = openFileOutput(file, android.content.Context.MODE_PRIVATE);
            java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);
            dos.writeInt(array.length);
            for(int i:array){
                dos.writeInt(i);
            }
            dos.flush();
            dos.close();
        }catch(java.io.IOException e){
            e.printStackTrace();
        }

    }

    public int[] load(String file){

        int[] ret = null;

        try{
            java.io.FileInputStream fis = openFileInput(file);
            java.io.DataInputStream dis = new java.io.DataInputStream(fis);

            int size = dis.readInt();
            ret = new int[size];

            for(int i=0; i<size; ++i){
                ret[i] = dis.readInt();
            }
            dis.close();

        }catch(java.io.IOException e){
            e.printStackTrace();
            ret = null;
        }

        return ret;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smniste3);

        android.content.SharedPreferences sp = getPreferences(MODE_PRIVATE);
        licenseAccepted = sp.getBoolean("licenseAccepted", false);
        int semValueLimit = sp.getInt("semValueLimit", 3);
        int decisionTimeLimit = sp.getInt("decisionTimeLimit", 1000);

        SMNISTSurfaceView.set(semValueLimit, decisionTimeLimit);

        int[] millisecs = load(MS_FILE);

        if (millisecs != null) {

            SMNISTSurfaceView.setMillisecs(millisecs);

        }

        int[] svmeans = load(SV_FILE);
        if (svmeans != null) {

            SMNISTSurfaceView.setSvmeans(svmeans);

        }

        if (!licenseAccepted)
            new android.app.AlertDialog.Builder(this)
                    .setTitle("GNU General Public License")
                    .setMessage("The sensation time of elements of the Parallel Individuation System"
                            + "\n"
                            + "\n"
                            + "Copyright (C) 2019 Norbert Bátfai, PhD"
                            + "\n"
                            + "norbert.batfai@inf.unideb.hu"
                            + "\n"
                            + "\n"
                            + "This program is free software: you can redistribute it and/or modify"
                            + "it under the terms of the GNU General Public License as published by"
                            + "the Free Software Foundation, either version 3 of the License, or"
                            + "(at your option) any later version."
                            + "\n"
                            + "\n"
                            + "This program is distributed in the hope that it will be useful,"
                            + "but WITHOUT ANY WARRANTY; without even the implied warranty of"
                            + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
                            + "GNU General Public License for more details.")
                    .setPositiveButton("Accept", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            SMNISTE3Activity.this.licenseAccepted = true;
                        }
                    })
                    .setNegativeButton("Reject", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(android.content.DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SMNISTE3Activity.this.finish();
                        }
                    })
                    .show();

    }

    @Override
    public void onPause() {
        super.onPause();

        android.content.SharedPreferences sp = getPreferences(MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("licenseAccepted", licenseAccepted);
        editor.putInt("semValueLimit", SMNISTSurfaceView.getSem());
        editor.putInt("decisionTimeLimit", SMNISTSurfaceView.getDec());
        editor.commit();

        save(MS_FILE, SMNISTSurfaceView.getMillisecs());
        save(SV_FILE, SMNISTSurfaceView.getSvmeans());
    }


}
