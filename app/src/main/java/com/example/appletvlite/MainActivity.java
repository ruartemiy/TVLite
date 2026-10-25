package com.example.appletvlite;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
  private LinearLayout root;
  private int dp(float x) { return (int)(getResources().getDisplayMetrics().density*x+0.5f); }
  private GradientDrawable bg(int color, int radius) { GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(radius));return d; }
  private TextView text(String s,int sp,int color) { TextView t=new TextView(this);t.setText(s);t.setTextColor(color);t.setTextSize(sp);t.setGravity(Gravity.CENTER_VERTICAL);return t; }
  @Override public void onCreate(Bundle b) { super.onCreate(b); getWindow().getDecorView().setSystemUiVisibility(5894|1024|512); render(); }
  @Override protected void onResume() { super.onResume(); }
  private void render() {
    root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(44),dp(26),dp(44),dp(24));
    root.setBackground(new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{0xff151b2b,0xff080b12}));setContentView(root);
    LinearLayout top=new LinearLayout(this);top.setGravity(Gravity.CENTER_VERTICAL);root.addView(top,new LinearLayout.LayoutParams(-1,dp(58)));
    TextView title=text("TV Lite",30,Color.WHITE);title.setTypeface(null,Typeface.BOLD);top.addView(title,new LinearLayout.LayoutParams(0,-1,1));
    TextView clock=text("",20,0xffc5cfdd);top.addView(clock);java.text.SimpleDateFormat fmt=new java.text.SimpleDateFormat("HH:mm",Locale.getDefault());clock.setText(fmt.format(new Date()));
    TextView sub=text("Ваши приложения",20,0xffd9e1ec);LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,dp(58));sp.topMargin=dp(24);root.addView(sub,sp);
    ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
    GridLayout grid=new GridLayout(this);grid.setColumnCount(5);grid.setUseDefaultMargins(false);scroll.addView(grid);
    PackageManager pm=getPackageManager();LinkedHashMap<String,ResolveInfo> apps=new LinkedHashMap<>();
    for(String category:new String[]{Intent.CATEGORY_LEANBACK_LAUNCHER,Intent.CATEGORY_LAUNCHER}) {
      Intent q=new Intent(Intent.ACTION_MAIN);q.addCategory(category);
      for(ResolveInfo info:pm.queryIntentActivities(q,0)) if(!info.activityInfo.packageName.equals(getPackageName())) apps.put(info.activityInfo.packageName,info);
    }
    ArrayList<ResolveInfo> sorted=new ArrayList<>(apps.values());Collections.sort(sorted,(a,b)->String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm).toString(),b.loadLabel(pm).toString()));
    int i=0;
    for(ResolveInfo info:sorted) {
      final String pkg=info.activityInfo.packageName;
      LinearLayout tile=new LinearLayout(this);tile.setOrientation(LinearLayout.VERTICAL);tile.setGravity(Gravity.CENTER);tile.setFocusable(true);tile.setClickable(true);tile.setPadding(dp(8),dp(10),dp(8),dp(10));tile.setBackground(bg(0xff252d40,18));
      GridLayout.LayoutParams lp=new GridLayout.LayoutParams(GridLayout.spec(i/5),GridLayout.spec(i%5));lp.width=dp(154);lp.height=dp(132);lp.setMargins(dp(7),dp(7),dp(7),dp(7));grid.addView(tile,lp);
      ImageView icon=new ImageView(this);try{icon.setImageDrawable(info.loadIcon(pm));}catch(Exception ignored){}tile.addView(icon,new LinearLayout.LayoutParams(dp(55),dp(55)));
      TextView name=text(info.loadLabel(pm).toString(),14,Color.WHITE);name.setGravity(Gravity.CENTER);name.setMaxLines(2);name.setEllipsize(android.text.TextUtils.TruncateAt.END);LinearLayout.LayoutParams np=new LinearLayout.LayoutParams(-1,dp(43));np.topMargin=dp(6);tile.addView(name,np);
      tile.setOnFocusChangeListener((v,focused)->{tile.setBackground(bg(focused?0xffe8edf7:0xff252d40,18));name.setTextColor(focused?0xff111827:Color.WHITE);tile.setScaleX(focused?1.045f:1f);tile.setScaleY(focused?1.045f:1f);});
      tile.setOnClickListener(v->{try{Intent launch=pm.getLeanbackLaunchIntentForPackage(pkg);if(launch==null)launch=pm.getLaunchIntentForPackage(pkg);if(launch!=null)startActivity(launch);else Toast.makeText(this,"Не удалось открыть приложение",Toast.LENGTH_SHORT).show();}catch(Exception e){Toast.makeText(this,"Ошибка запуска",Toast.LENGTH_SHORT).show();}});
      if(i==0)tile.requestFocus();i++;
    }
    if(i==0){TextView empty=text("Приложения не найдены",20,Color.WHITE);root.addView(empty);}
    TextView foot=text("OK — открыть приложение     •     Стрелки — навигация",13,0xff8e9aae);root.addView(foot);
  }
}
