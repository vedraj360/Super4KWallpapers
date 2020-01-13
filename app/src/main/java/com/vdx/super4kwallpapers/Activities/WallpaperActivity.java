package com.vdx.super4kwallpapers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import com.vdx.super4kwallpapers.Models.ModelLinks;
import com.vdx.super4kwallpapers.Models.Sheet1;

import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.Helper;
import com.vdx.super4kwallpapers.Utils.IResult;
import com.vdx.super4kwallpapers.Utils.Links;
import com.vdx.super4kwallpapers.Utils.Transformations.HingeTransformation;
import com.vdx.super4kwallpapers.Utils.VolleyService;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;

import com.vdx.super4kwallpapers.Adapters.ImagePageAdapter;

public class WallpaperActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    private FloatingActionMenu leftCenterMenu;
    Helper helper = new Helper();
    private static final int WRITE_REQUEST_CODE = 300;
    private static String REQUEST_TYPE_POST = "POST";
    String TAG = "wallpaper";
    private String link, links, linkplus, amoledlink;
    private String wallType;
    private IResult result, getResult;

    private int count = 0, length = 0;
    private Map<String, Integer> cMap = new HashMap<>();
    private HashMap<String, Integer> tMap = new HashMap<>();
    private ArrayList<String> trending_links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        Log.d("RESSSS", Links.Res);

        try {
            initViews();

        } catch (Exception e) {
            Log.e("ERRRRRRR", String.valueOf(e));
        }


    }

    void initViews() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        HingeTransformation hingeTransformation = new HingeTransformation();
        wallType = getIntent().getStringExtra("wall");

        String response = getIntent().getStringExtra("response");


        final int pos = getIntent().getIntExtra("pos", 0);
        initVolley();
        initTpost();

        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_action_new_light));

        // Set up the large red butt on the center right side
        // With custom butt and content sizes and margins

        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);

        final ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_action_new));

        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin);
        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
        fabIconStarParams.setMargins(redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin);

        FloatingActionButton leftCenterButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconStar, fabIconStarParams)
                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setLayoutParams(starParams)
                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
        lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin);
        lCSubBuilder.setLayoutParams(blueContentParams);

        // Set custom layout params

        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        ImageView share = new ImageView(this);
        final ImageView save = new ImageView(this);
        final ImageView setAs = new ImageView(this);
        final ImageView rate = new ImageView(this);

        share.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_share));
        save.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_white));
        setAs.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_setas));
        rate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rate));

        // Build another menu with custom options
        leftCenterMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(lCSubBuilder.setContentView(share, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(save, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(setAs, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setContentView(rate, blueContentParams).build())
                .setRadius(redActionMenuRadius)
                .attachTo(leftCenterButton)
                .build();


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Find the Super and best 4k & Amoled Wallpapers: " + "https://play.google.com/store/apps/details?id=com.vdx.super4kwallpapers");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whats App Not Found!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.RateUs(WallpaperActivity.this, "com.vdx.super4kwallpapers");

            }
        });

        leftCenterMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                fabIconStar.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconStar, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                fabIconStar.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconStar, pvhR);
                animation.start();

            }
        });

        try {
            Gson gson = new Gson();
            ModelLinks modelLinks;

            if (!Links.Res.equals("zzz")) {
                modelLinks = gson.fromJson(Links.Res, ModelLinks.class);

            } else {
                modelLinks = gson.fromJson(response, ModelLinks.class);

            }

            if (wallType.equals("trending")) {
                modelLinks = gson.fromJson(Links.TRENDING_RESPONSE, ModelLinks.class);
            }

            final List<Sheet1> linkList = modelLinks.getSheet1();

            for (int i = 0; i < linkList.size(); i++) {
                cMap.put(linkList.get(i).getId(), linkList.get(i).getCount());
                tMap.put(linkList.get(i).getAmoledlinks(), linkList.get(i).getCount());

            }

            ImagePageAdapter myPager = new ImagePageAdapter(WallpaperActivity.this, linkList, wallType);
            viewPager.setAdapter(myPager);

            viewPager.setCurrentItem(pos);
            viewPager.setPageTransformer(true, hingeTransformation);

            if (wallType.equals("4k")) {
                link = linkList.get(pos).getLinks();
            } else {
                link = linkList.get(pos).getAmoledlinks();
            }

            setAs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSetAs(link);

                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = linkList.get(pos).getId();
                    count = linkList.get(pos).getCount();
                    saveFile(link, id);

                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (leftCenterMenu.isOpen()) {
                        leftCenterMenu.close(true);
                    }
                }

                @Override
                public void onPageSelected(final int position) {


                    if (wallType.equals("4k")) {
                        links = linkList.get(position).getLinks();

                    } else {
                        links = linkList.get(position).getAmoledlinks();

                    }

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = linkList.get(position).getId();

                            count = linkList.get(position).getCount();

                            saveFile(links, id);

                        }
                    });

                    setAs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSetAs(links);

                        }
                    });
                }

                @Override
                public void onPageScrollStateChanged(int state) {


                }
            });

        } catch (Exception e) {
            Log.d("InitAdapter_Exception", String.valueOf(e));
        }


    }

    private void saveFile(String linkplus, String id) {

        if (EasyPermissions.hasPermissions(WallpaperActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //Get the URL entered
            int cc = Objects.requireNonNull(cMap.get(id));
            int cvalue = cc + 1;
            cMap.put(id, cvalue);
            Log.d("cMap", String.valueOf(cMap.get(id)));
            String updateCount = String.valueOf(cMap.get(id));
            VolleyService volleyService = new VolleyService(result, getApplicationContext(), Links.key, id, updateCount);

            volleyService.postData(REQUEST_TYPE_POST, Links.POST_URL);
            Log.d("ID", id);
            //  new DownloadFile().execute(linkplus);
            Links.BACKPRESS = 1;
            sortTlist();

            VolleyService service = new VolleyService(getResult, getApplicationContext(), trending_links, length);
            service.postTrendingData(REQUEST_TYPE_POST, Links.TRENDING_POST_URL);


        } else {
            //If permission is not present request for the same.
            EasyPermissions.requestPermissions(WallpaperActivity.this, getString(R.string.write_file), WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }


    }

    private void sortTlist() {
        Map<String, Integer> hm1 = sortByValue(tMap);

        for (Map.Entry<String, Integer> en : hm1.entrySet()) {
            System.out.println("Key = " + en.getKey() +
                    ", Value = " + en.getValue());
            if (!en.getKey().equals(""))
                trending_links.add(en.getKey());
            length = length + 1;
        }
        hm1.clear();
        tMap.clear();
        System.out.println(length);

    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    private void initTpost() {
        getResult = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON Trending post" + response);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON Trending post" + "That didn't work!");
            }
        };
    }

    private void dialogSetAs(final String links) {

        Log.d("check", links);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.setwallpaperdialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("Set As");
        Button home = dialog.findViewById(R.id.set_home);

        Button lock;
        lock = dialog.findViewById(R.id.set_lock);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WallpaperActivity.this, "Setting Wallpaper", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                WallpaperManager wpm = WallpaperManager.getInstance(WallpaperActivity.this);
                InputStream ins;

                try {
                    ins = new URL(links).openStream();
                    wpm.setStream(ins);
                    Toast.makeText(WallpaperActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        });
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockScreenWallpaper(links);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void lockScreenWallpaper(String links) {
        final WallpaperManager wpManager = WallpaperManager.getInstance(getApplicationContext());


        // Set the wallpaper
        InputStream ins;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // On Android N and above use the new API to set both the general system wallpaper and
            // the lock-screen-specific wallpaper
            try {
                ins = new URL(links).openStream();
                wpManager.setStream(ins, null, false, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);


            } catch (Exception e) {
                Log.e("", "");
            }
        } else {
            Toast.makeText(this, "Device Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    private void initVolley() {
        result = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_fab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up butt, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        new DownloadFile().execute("https://images.freeimages.com/images/large-previews/a0d/autumn-tree-1382832.jpg");
        Log.d("Granted", "Permission has been granted");


    }

    @Override
    public void onBackPressed() {
        Log.d("WALLSC", String.valueOf(Links.BACKPRESS));

        super.onBackPressed();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        Log.d("Denied", "Permission has been denied");
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(WallpaperActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                @SuppressLint("SimpleDateFormat") String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                String fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1);

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                String folder = Environment.getExternalStorageDirectory() + File.separator + "super4kwallpaper/";

                //Create super4kwallpaper folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    //  Log.d("Down", "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dial after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }

}
