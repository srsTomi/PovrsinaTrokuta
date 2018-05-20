package tomislav.piskur.com.povrsinatrokuta.Data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tomislav.piskur.com.povrsinatrokuta.Activity.IzracunPovrsineFragment;
import tomislav.piskur.com.povrsinatrokuta.Activity.PovijestIzracunaFragment;

/**
 * Created by srs on 15.05.2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new IzracunPovrsineFragment();
        } else if (position == 1) {
            fragment = new PovijestIzracunaFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Izračun površine";
        } else if (position == 1) {
            title = "Povijest";
        }
        return title;
    }
}