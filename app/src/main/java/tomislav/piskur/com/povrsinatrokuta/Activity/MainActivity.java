package tomislav.piskur.com.povrsinatrokuta.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tomislav.piskur.com.povrsinatrokuta.R;
import tomislav.piskur.com.povrsinatrokuta.Data.FragmentAdapter;

public class MainActivity extends AppCompatActivity  implements IzracunPovrsineFragment.SendMessage{

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();




        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void sendData(String message) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
        PovijestIzracunaFragment f = (PovijestIzracunaFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(message);
    }
}
