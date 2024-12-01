package com.example.registrodesignosvitales.todas_las_listas;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, @NonNull Lifecycle lifecycle) {
        super(fragmentActivity.getSupportFragmentManager(), lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Todas_Las_Tomas();
            case 1:
                return new Tomas_FC();
            case 2:
                return new Tomas_Spo2();
            case 3:
                return new Tomas_Temperatura();
            default:
                return new Todas_Las_Tomas(); // Default in case of invalid position
        }
    }

    @Override
    public int getItemCount() {
        return 4;  // 4 fragments
    }

    // Método para agregar fragmentos (no es necesario si los fragmentos ya están definidos)
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}
